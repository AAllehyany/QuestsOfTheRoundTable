package group52.comp3004.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.players.Player;

/**
 * Handles set up, playing, and ending a quest state.
 * <p>QuestCard is the card itself. GameQuest handles the quest game play.</p>
 * @author Sandy
 *
 */
public class GameQuest {
	
	private final QuestCard quest;
	private boolean withTest;
	private List<Stage> stages;
	private List<Player> players;
	private int currentStage;
	private Player sponsor;
	private boolean over;

	static final private Logger logger = Logger.getLogger(GameQuest.class);
	
	/**
	 * Constructor for a new quest. GameQuest functionally starts only after a player has decided to sponsor a quest.
	 * @param quest The quest card that was drawn from the story deck.
	 * @param sponsor The player that will set up the quest. 
	 */
	public GameQuest(QuestCard quest, Player sponsor) {
		this.quest = quest;
		this.withTest = false;
		this.stages = new ArrayList<Stage>();
		this.players = new ArrayList<Player>();
		this.currentStage = 0;
		this.sponsor = sponsor;
		this.over = false;
	}
	
	/**
	 * Returns true if one of the stages of the quest is a test.
	 */
	public boolean isWithTest() {
		return withTest;
	}

	/**
	 * Get the quest property.
	 * @return
	 */
	public QuestCard getQuest() {
		return quest;
	}
	
	/**
	 * Get the ith stage of the quest
	 * @param i An index of the stage list that exists
	 */
	public Stage getStage(int i) {
		return stages.get(i);
	}
	
	/**
	 * Get a list of stages of a quest
	 */
	public List<Stage> getStages() {
		return stages;
	}
	
	/**
	 * Used for the cheat function. Finds the number of cards in each stage including the foe/test card
	 * @return an array list of the number of cards in each stage.
	 */
	public ArrayList<Integer> getStageCardNum(){
		ArrayList<Integer> cNum = new ArrayList<Integer>();
		for(int i=0;i>stages.size();i++) {
			cNum.add(stages.get(i).totalCardsPlayed());
		}
		for(int i=0;i<stages.size();i++) {
			logger.info("Stage " + (i+1) + " has " + cNum.get(i) + " cards");
		}
		return cNum;
	}
	
	/**
	 * Can another stage be added to the quest.
	 * @return True if there at least one more spot available in the quest for a new stage.
	 */
	public boolean canAddStage() {
		return this.stages.size() < quest.getStages();
	}
	
	/**
	 * Remove all stages from quest.
	 */
	public void clearAllStages() {
		withTest = false;
		this.stages.clear();
	}
	
	/**
	 * Add a new stage to the quest. Can only add one test stage to a quest
	 * @param stage The stage that is being added
	 * @return True Stage added correctly
	 *     <p>False either trying to add a second test or the stage the less then previous one
	 */
	public boolean addStage(GameState state, Stage stage) {
		
		if(stage.isTestStage() && withTest) return false;
		
		if(stage.isTestStage() && !withTest) {
			withTest = true;
			stages.add(stage);
			return true;
		}
		
		Stage highestStage = stages.stream().max( (s1, s2) -> Integer.compare(s1.getTotalPower(state), s2.getTotalPower(state))).orElse(null);
		
		if((highestStage == null || stage.getTotalPower(state) >= highestStage.getTotalPower(state)) && stages.size() < quest.getStages()) {
			stages.add(stage);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Get complete stage object of the current stage through the list of stages
	 */
	public Stage currentStage() {
		return stages.get(currentStage);
	}
	
	/**
	 * Get complete stage object of the current stage through the currentStage property
	 */
	public int getCurrentStage() {
		return currentStage;
	}
	
	/**
	 * Move on to the next stage
	 */
	public boolean advanceStage() {
		if(currentStage < (quest.getStages() - 1) && players.size()>0) {
			currentStage += 1;
			return true;
		}
		return false;
	}
	
	/**
	 * Add a new participating player to the quest
	 * @param player
	 */
	public void addPlayer(Player player) {
		if(!this.players.contains(player) && player.getQuest() == null) {
			logger.info("Player " + player.getId() + " joined quest");
			this.players.add(player);
			player.setQuest(this);
		}
	}
	
	/**
	 * Execute the logic to determine which players can move on in the quest and which players are removed
	 * from the quest. Handles both foe and test stages
	 * @param state The current conditions of the game.
	 */
	public void playStage(GameState state) {
		if(over || this.players.size() == 0) return;
		
		if(stages.get(currentStage).isTestStage()) {
			logger.info("Playing in a test stage...");
			ArrayList<Player> remaining = new ArrayList<Player>(players);
			int i=0;
			int highest;
			if(remaining.size()>1) highest = 0;
			else highest = stages.get(currentStage).getTest().getMinBid(state);
			while(remaining.size()>1) {
				Player p = remaining.get(i%remaining.size());
				if(highest<p.getOfferedBids() + p.getBidPoints(state)) {
					highest = p.getOfferedBids() + p.getBidPoints(state);
					i++;
				}
				else {
					remaining.remove(p);
				}
			}
			
			Player last = remaining.get(0);
			
			logger.info("Remaining player is " + last.getId());
			
			if(last.getOfferedBids()+last.getBidPoints(state) < highest) {
				logger.info("Everyone dropped of the test!");
				this.over = true;
				this.players.clear();
			}
			else {
				this.players.clear();
				this.players.add(last);
			}
			
			if(advanceStage())
				for(int j=0;i<players.size();j++) 
					players.get(j).addCardToHand(state.getAdventureDeck().draw());
			
			return;
		}
		
		logger.info("Playing a foe stage!");
		
		List<Player> remaining = players.stream().filter(p -> p.getBattlePoints(state) >= stages.get(currentStage).getTotalPower(state)).collect(Collectors.toList());
		ArrayList<AdventureCard> discard = new ArrayList<AdventureCard>();
		for(int i=0;i<players.size();i++) discard.addAll(players.get(i).clearTemp());
		state.getAdventureDeck().discard(discard);
		logger.info("Done playing the stage...");
		logger.info(remaining.size() + " players are now in the quest.");
		this.players = remaining;
		if(currentStage == (quest.getStages() - 1) || this.players.size() < 1)  {
			logger.info("No players or we played all stages! Quest is over.");
			this.over = true;
		}
		for(int i=0;i<players.size();i++) players.get(i).addCardToHand(state.getAdventureDeck().draw());
		advanceStage();	
	}
	
	/**
	 * Get the number of stages in the quest
	 */
	public int getNumStages() {
		return this.quest.getStages();
	}
	
	/**
	 * Gives shields to the players that complete the quest
	 */
	public void awardShields() {
		if(over) players.forEach(p -> p.addShields(quest.getStages()));
	}
	
	/**
	 * Gives shields to the players that complete the quest. King's recognition adds bonus shields so that needed to be handled here
	 * @param bonus The number of bonus shields awarded by King's recognition
	 */
	public void awardShields(int bonus) {
		if(over) players.forEach(p -> p.addShields(quest.getStages() + bonus));
	}
	
	/**
	 * Get the list of participating players
	 */
	public List<Player> getPlayers() {
		return this.players;
	}
	
	/**
	 * Returns true if the quest is finished
	 */
	public boolean isOver() {
		return this.over;
	}

	/**
	 * Returns true if the player is participating in the quest.
	 * @param player The player to test for
	 */
	public boolean isPlayer(Player player) {
		return this.players.contains(player); // || player == sponsor;
	}
	
	/**
	 * Get the player that sponsored the quest
	 */	
	public Player getSponsor() {
		return this.sponsor;
	}
	
	/**
	 * Change which player sponsored the quest
	 * @param player
	 */	
	public void setSponsor(Player player) {
		this.sponsor = player;
	}
	
	/**
	 * Sponsor gets the number of cards played into a quest the by sponsor so they can draw that number cards after.
	 * @return number of cards to draw
	 */
	public int getNumCardsPlayedBySponsor() {
		return this.getStages().stream().mapToInt(s -> s.totalCardsPlayed()).sum() + stages.size();
	}
	
	/**
	 * Deal number of cards equal to what the sponsor played to setup the quest
	 */
	public void dealCardsToSponsor() {
		for(int i = 0; i< getNumCardsPlayedBySponsor(); i++)
			sponsor.getGame().dealToPlayer(sponsor.getGame().getSponsorIndex());
	}
	
	/**
	 * Handles the a quest ending. All cards played into the quest are added to the discard pile and awards 
	 * @param state The current conditions of the game.
	 * @param bonus Represents the bonus shields given by King's recognition
	 */
	public void end(GameState state, int bonus) {
		this.over = true;
		dealCardsToSponsor();
		for(int i=0;i<state.getAllPlayers().size();i++) {
			Player p = state.getPlayerByIndex(i);
			state.getAdventureDeck().discard(p.getTemp());
			p.getTemp().clear();
			for(int j=0;j<p.getTemp().size();j++) {
				if(p.getTemp().get(j) instanceof Amour) {
					state.getAdventureDeck().discard(p.getTemp().get(j));
					p.getTemp().remove(j);
					break;
				}
			}
		}
		for(int i=0;i<this.getNumStages();i++) {
			state.getAdventureDeck().discard(this.stages.get(i).getCards());
		}
		state.getStoryDeck().discard(this.getQuest());
		awardShields(bonus);
	}
	
	/**
	 * Used for testing
	 */
	public void end() { 
		this.over = true;
		
	}
	
	/**
	 * Used for testing that involves events
	 * @param bonus Represents the bonus shields given by King's recognition
	 */
	public void end(int bonus) {
		this.over = true;
		dealCardsToSponsor();
		awardShields(bonus);
	}
}
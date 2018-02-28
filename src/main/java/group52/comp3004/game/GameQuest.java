package group52.comp3004.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.players.Player;

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
	 * @param quest
	 * @param hasTest
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
	
	public boolean isWithTest() {
		return withTest;
	}

	
	public QuestCard getQuest() {
		return quest;
	}
	
	public Stage getStage(int i) {
		return stages.get(i);
	}
	
	public List<Stage> getStages() {
		return stages;
	}
	
	public boolean canAddStage() {
		return this.stages.size() < quest.getStages();
	}
	
	
	public void clearAllStages() {
		withTest = false;
		this.stages.clear();
	}
	public boolean addStage(Stage stage) {
		
		if(stage.isTestStage() && withTest) return false;
		
		if(stage.isTestStage() && !withTest) {
			withTest = true;
			stages.add(stage);
			return true;
		}
		
		Stage highestStage = stages.stream().max( (s1, s2) -> Integer.compare(s1.getTotalPower(), s2.getTotalPower())).orElse(null);
		
		if((highestStage == null || stage.getTotalPower() >= highestStage.getTotalPower()) && stages.size() < quest.getStages()) {
			stages.add(stage);
			return true;
		}
		
		return false;
	}
	
	public Stage currentStage() {
		return stages.get(currentStage);
	}
	
	public int getCurrentStage() {
		return currentStage;
	}
	
	public void advanceStage() {
		if(currentStage < (quest.getStages() - 1)) currentStage += 1;
	}
	
	public void addPlayer(Player player) {
		if(!this.players.contains(player) && player.getQuest() == null) {
			this.players.add(player);
			player.setQuest(this);
		}
	}
	
	public void playStage() {
		if(over || this.players.size() == 0) return;
		
		if(stages.get(currentStage).isTestStage()) {
			logger.info("Playing in a test stage...");
			Player remaining = players.stream().max((p1, p2) -> {
				if(p1.getOfferedBids() > p2.getOfferedBids()) return -1;
				if(p1.getOfferedBids() == p2.getOfferedBids()) return 0;
				return 1;
			}).get();
			
			logger.info("Remaining player is " + remaining.getId());
			
			if(remaining.getOfferedBids() == 0) {
				logger.info("Everyone dropped of the test!");
				this.over = true;
				this.players.clear();
			}
			else {
				this.players.clear();
				this.players.add(remaining);
			}
			
			advanceStage();
			
			return;
		}
		
		logger.info("Playing a foe stage!");
		
		List<Player> remaining = players.stream().filter(p -> p.getBattlePoints() >= stages.get(currentStage).getTotalPower()).collect(Collectors.toList());
		this.players.forEach(p -> p.clearTemp());
		logger.info("Done playing the stage...");
		logger.info(remaining.size() + " players are now in the quest.");
		this.players = remaining;
		if(currentStage == (quest.getStages() - 1) || this.players.size() < 1)  {
			logger.info("No players or we played all stages! Quest is over.");
			this.over = true;
		}
		advanceStage();	
	}
	
	public void playStage(GameState state) {
		if(over || this.players.size() == 0) return;
		
		if(stages.get(currentStage).isTestStage()) {
			System.out.println("Playing in a test stage...");
			Player remaining = players.stream().max((p1, p2) -> {
				if(p1.getOfferedBids() > p2.getOfferedBids()) return -1;
				if(p1.getOfferedBids() == p2.getOfferedBids()) return 0;
				return 1;
			}).get();
			
			System.out.println("Remaining player is " + remaining.getId());
			
			if(remaining.getOfferedBids() == 0) {
				System.out.println("Everyone dropped of the test!");
				this.over = true;
				this.players.clear();
			}
			else {
				this.players.clear();
				this.players.add(remaining);
			}
			
			advanceStage();
			
			return;
		}
		
		System.out.println("Playing a foe stage!");
		
		List<Player> remaining = players.stream().filter(p -> p.getBattlePoints(state) >= stages.get(currentStage).getTotalPower(state)).collect(Collectors.toList());
		ArrayList<AdventureCard> discard = new ArrayList<AdventureCard>();
		state.getAdventureDeck().discardCard(discard);
		for(int i=0;i<players.size();i++) discard.addAll(players.get(i).clearTemp());
		System.out.println("Done playing the stage...");
		System.out.println(remaining.size() + " players are now in the quest.");
		this.players = remaining;
		if(currentStage == (quest.getStages() - 1) || this.players.size() < 1)  {
			System.out.println("No players or we played all stages! Quest is over.");
			this.over = true;
		}
		advanceStage();	
	}
	
	public int getNumStages() {
		return this.quest.getStages();
	}
	
	public void awardShields() {
		if(over) players.forEach(p -> p.addShields(quest.getStages()));
	}
	
	public void awardShields(int bonus) {
		if(over) players.forEach(p -> p.addShields(quest.getStages() + bonus));
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public boolean isOver() {
		return this.over;
	}

	public boolean isPlayer(Player player) {
		// TODO Auto-generated method stub
		return this.players.contains(player); // || player == sponsor;
	}
	
	public Player getSponsor() {
		return this.sponsor;
	}
	
	public void setSponsor(Player player) {
		this.sponsor = player;
	}
	
	public int getNumCardsPlayedBySponsor() {
		return this.getStages().stream().mapToInt(s -> s.totalCardsPlayed()).sum() + stages.size();
	}
	
	public void dealCardsToSponsor() {
		for(int i = 0; i< getNumCardsPlayedBySponsor(); i++)
			sponsor.getGame().dealToPlayer(sponsor.getGame().getSponsorIndex());
	}
	
	
	public void end() { 
		this.over = true;
		
	}
	
	public void end(int bonus) {
		this.over = true;
		dealCardsToSponsor();
		awardShields(bonus);
	}
}
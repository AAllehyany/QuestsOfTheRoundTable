	package group52.comp3004.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.decks.Deck;
import group52.comp3004.players.Player;
import group52.comp3004.players.Rank;

/**
 * Holds all the information about the current state of the game.
 * @author Sandy
 *
 */
public class GameState {
	
	private List<Player> players;
	private int currentTurn;
	private int currentPlayer;
	private int currentSponsor;
	private Phase phase;
	private Deck<AdventureCard> adventureDeck;
	private Deck<StoryCard> storyDeck;
	private GameQuest currentQuest;
	private GameTourney currentTourney;
	private StoryCard revealedCard;
	private Integer maxBid;
	private Integer bonusShields;
	
	static final private Logger logger = Logger.getLogger(GameState.class);
	/**
	 * @param players
	 */
	
	/**
	 * Constructor for a game state with the entered players
	 * @param players list of players in the game
	 */
	public GameState(List<Player> players) {
		this.players = players;
		this.currentTurn = 0;
		this.currentPlayer = 0;
		phase = Phase.TurnStart;
		logger.info("Model loaded (players)");
	}
	
	/**
	 * 
	 */
	public GameState() {
		this.players = new ArrayList<>();
		this.currentTurn = 0;
		this.currentPlayer = 0;
		this.currentSponsor = -1;
		phase = Phase.TurnStart;		
		adventureDeck = null;
		storyDeck = null;
		currentQuest = null;
		revealedCard = null;
		maxBid = 0;
		bonusShields = 0;
		logger.info("Model loaded (void)");
	}
	
	/**
	 * Deals out all initial card out to the players
	 */
	public void dealCardsToPlayers() {
		this.players.forEach(player -> {
			for(int i = 0; i < 12; i++) {
				player.addCardToHand(adventureDeck.draw());
			}
		});
	}

	/**
	 * Deal a story card into play
	 * @return The story card dealt
	 */
	public StoryCard dealStoryCard() {
		revealedCard = storyDeck.draw();
		return revealedCard;
	}

	/**
	 * add a player if there are less then four
	 * @param player Player to be added
	 */
	public void addPlayer(Player player) {
		if(players.size() < 4) players.add(player);
	}

	/**
	 * Moves to next turn. 
	 */
	public void nextTurn() {
		currentTurn = (currentTurn + 1) % players.size();
		currentPlayer = currentTurn;
	}
	
	/**
	 * Used for testing. Has test go to next player for quests.
	 */
	public void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.size();
		
		while(phase == Phase.SetupQuest 
				&& this.currentQuest != null && !this.currentQuest.isPlayer(this.players.get(currentPlayer))) {
			currentPlayer = (currentPlayer + 1) % players.size();
		}
	}
	
	/**
	 * Get the currentTurn property
	 */
	public int getCurrentTurn() {
		return this.currentTurn;
	}
	
	/**
	 * Get the player who's turn it currently is
	 */
	public int getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	/**
	 * Get a player in the list of all players.
	 * @param index Which player in the list
	 * @return A player object
	 */
	public Player getPlayerByIndex(int index) {
		return this.players.get(index);
	}
	
	/**
	 * deal a single card to a player
	 * @param index Which player to deal the card to.
	 */
	public void dealToPlayer(int index) {
		AdventureCard card = adventureDeck.draw();
		if(card != null)
			this.players.get(index).addCardToHand(card);
	}
	
	/**
	 * Get the index of the player that sponsored the quest
	 */
	public int getSponsorIndex()
	{
		return this.currentSponsor;
	}
	
	/**
	 * Get the number of players in the game
	 */
	public int numPlayers() {
		return this.players.size();
	}
	
	/**
	 * Add current player to the quest
	 */
	public void joinQuest() {
		if(currentQuest != null && currentSponsor != currentPlayer) currentQuest.addPlayer(getPlayerByIndex(currentPlayer));
	}
	
	/**
	 * Judges whether a player is able to sponsor a quest. Checks to make sure that a story card was dealt and then insures 
	 * <p>that the current player has enough cards to create the quest.</p>
	 * @return true if the current player is capable of creating the quest.
	 */
	public boolean canSponsorQuest() {
		if(revealedCard == null || !(revealedCard instanceof QuestCard)) return false;
		
		Player current = this.players.get(currentPlayer);
		QuestCard quest = (QuestCard) revealedCard;
		int numFoes = current.countFoes();
		int numFoesMinusOne = numFoes - 1;
		int numTests = current.hasTest() ? 1 : 0;
		int stages = quest.getStages();
		
		return numFoes >= stages || numFoesMinusOne + numTests >= stages;
	}
	
	/**
	 * Called if the story card drawn at start of new turn is a quest. 
	 * <p>Moves to SponsorQuest phase when finished.</p>
	 */
	public void setQuest() {
		if(revealedCard != null && revealedCard instanceof QuestCard)
		{
			currentSponsor = currentPlayer;
			currentQuest = new GameQuest((QuestCard) revealedCard, getPlayerByIndex(currentSponsor));
			this.players.get(currentPlayer).setQuest(currentQuest);
//			this.revealedCard = null;
			this.phase = Phase.SponsorQuest;
		}
	}
	
	/**
	 * Called if the story card drawn at start of new turn is a quest.
	 * <p>Moves to RunTourney phase when complete</p>
	 */
	public void setTourney() {
		if(revealedCard != null && revealedCard instanceof Tourneys)
		{
			currentSponsor = currentPlayer;
			currentTourney = new GameTourney((Tourneys) revealedCard);
			this.players.get(currentPlayer).setTourney(currentTourney);
			this.phase = Phase.RunTourney;
		}
	}
	
	/**
	 * Adds a stage to a quest if possible.
	 * @param foe The foe that makes up the stage.
	 * @return True if stage successfully added. False otherwise.
	 */
	public boolean setUpQuestStage(Foe foe) {
		if(currentQuest != null && currentQuest.canAddStage() && currentSponsor == currentPlayer
				&& phase == Phase.SetupQuest) {
			currentQuest.addStage(this, new Stage(foe));
			return true;
		}
		
		return false;
			
	}
	
	/**
	 * Used when a player plays a card. The temp list holds all cards played and is cleared after a story is completed.
	 * <p>Allies are moved to a separate list when temp is cleared</p>
	 * @param card The card to be added to the list.
	 * @return Card successfully added.
	 */
	public boolean playCardToTemp(AdventureCard card) {
		if(currentPlayer == currentSponsor && phase != Phase.SetupQuest) return false;
		if(!currentQuest.isPlayer(this.players.get(currentPlayer))) return false;
		
		Player p = this.players.get(currentPlayer);
		p.playToTemp(card);
		return true;
	}
	
	/**
	 * Resets game state for next story card after quest is finished.
	 */
	public void endQuest() {
		
		currentQuest.end(this, this.bonusShields);
		currentSponsor = -1;
		currentQuest = null;
		this.bonusShields=0;
		players.forEach(p -> p.removeQuest());
		
		
		// remove all allies and all cards and make people draw (call quest end)
	}
	
	/**
	 * Tests whether a player has an input number of bids and that number of bids is greater then the minimum playable.
	 * @param bids Number of cards added to bid.
	 * @return True if player is able to bid.
	 */
	public boolean canBidCards(int bids) {
		return this.players.get(currentPlayer).validBid(bids) && bids > maxBid;
	}
	
	/**
	 * Current player bids specified number of cards and the minimum number of bids to pass test is raised.
	 * @param bids
	 */
	public void bidCards(int bids) {
		if(canBidCards(bids)) {
			this.players.get(currentPlayer).bidCards(bids);
			this.maxBid = bids;
		}
	}
	
	/**
	 * Handles situation where the player decides to stop bidding.
	 */
	public void playerStopBidding() {
		this.players.get(currentPlayer).stopBidding();
	}
	
	/**
	 * Handles play of the next stage in the quest.
	 */
	public void playCurrentQuestStage() {
		if(currentQuest != null) currentQuest.playStage(this);
	}
	
	/**
	 * get the number of bids that the next player must pass to continue the test.
	 * @return
	 */
	public int getMaxBid() {
		return this.maxBid;
	}
	
	/**
	 * Get the player who sponsored the quest.
	 * @return A player object
	 */
	public Player getCurrentSponsor() {
		if(currentSponsor != -1) return this.players.get(currentSponsor);
		return null;
	}
	
	/**
	 * Resets game state back to normal when a tourney has ended.
	 */
	public void endTourney() {
		this.currentTourney.end(this);
		currentTourney = null;
	}
	
	//GETTERS and SETTERS
	public StoryCard getRevealed() { return this.revealedCard; }
	
	public GameQuest getCurrentQuest() { return this.currentQuest; }
	public GameTourney getCurrentTourney() {	return this.currentTourney;}
	public StoryCard getRevealedCard() { return this.revealedCard; }
	public void setRevealedCard(StoryCard card) { this.revealedCard = card; }

	public void setBonusShields(int b) {
		this.bonusShields= b;
	}
	public void setPhase(Phase phase) { this.phase = phase; }
	public Phase getPhase() { return this.phase; }
	public int getBonusShields() {	return this.bonusShields;}

	public List<Player> getAllPlayers() { return this.players; }
	
	public Deck<AdventureCard> getAdventureDeck() { return adventureDeck; }
	
	public List<Player> getWinners() {
		return this.players.stream().filter(player -> player.getRank() == Rank.KnightOfTheRoundTable).collect(Collectors.toList());
	}
}
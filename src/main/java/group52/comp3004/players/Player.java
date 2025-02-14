package group52.comp3004.players;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import group52.comp3004.Strategies.AbstractAI;
import group52.comp3004.Strategies.Strategy1;
import group52.comp3004.Strategies.Strategy2;
import group52.comp3004.Strategies.Strategy3;
import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.CardComparator;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.GameTourney;

/**
 * Stores all required information about an individual player 
 * @author Sandy
 *
 */
public class Player {
	
	private Integer id;
	private Integer shields;
	private Rank rank;
	private Integer battlePoints;
	private Integer requiredShields;
	private int minShields;
	private ArrayList<AdventureCard> hand;
	private ArrayList<AdventureCard> field;
	private ArrayList<AdventureCard> temp;
	private transient GameState game;
	private transient GameQuest quest;
	private transient GameTourney tourney;
	private boolean stoppedBidding;
	private int offeredBids;
	private AbstractAI strategy;
	private boolean ready;
	private ArrayList<AdventureCard> tempWeapons;
	
	static final private Logger logger = Logger.getLogger(Player.class);
	
	/**
	 * Used for human construction
	 * @param id Player id number. Used for internal testing.
	 * @param gs the current conditions of the game
	 */
	public Player(Integer id, GameState gs) {
		this.id = id;
		shields = 10;
		rank = Rank.Squire;
		battlePoints = 5;
		requiredShields = 15;
		minShields = 10;
		hand = new ArrayList<>();
		field = new ArrayList<>();
		temp = new ArrayList<>();
		quest = null;
		tourney = null;
		game = gs;
		offeredBids = 0;
		strategy = null;
		tempWeapons = new ArrayList<>();

	}
	
	/**
	 *  USed for AI player construction
	 * @param id Player id number. Used for internal testing.
	 * @param gs the current conditions of the game
	 * @param s
	 */
	public Player(Integer id, GameState gs, int s) {
		this.id = id;
		shields = 10;
		rank = Rank.Squire;
		battlePoints = 5;
		requiredShields = 15;
		minShields = 10;
		hand = new ArrayList<>();
		field = new ArrayList<>();
		temp = new ArrayList<>();
		quest = null;
		tourney = null;
		game = gs;
		offeredBids = 0;
		if(s==1) strategy = new Strategy1();
		else if(s==2) strategy = new Strategy2();
		else if(s==3) strategy = new Strategy3();
		else strategy = null;
		
	}
	
	public ArrayList<AdventureCard> getTempWeapons() {
		return this.tempWeapons;
	}
	
	public void addTempWeapon(AdventureCard card) {
		this.tempWeapons.add(card);
	}
	
	/**
	 *  Used for testing when there isn't a game state.
	 * @param id Player id number. Used for internal testing.
	 */
	public Player(Integer id) {
		this.id = id;
		shields = 10;
		rank = Rank.Squire;
		battlePoints = 5;
		requiredShields = 15;
		minShields = 10;
		hand = new ArrayList<>();
		field = new ArrayList<>();
		temp = new ArrayList<>();
		quest = null;
		tourney = null;
		offeredBids = 0;
		tempWeapons = new ArrayList<>();
	}
	
	/**
	 * Get the id property
	 */
	public Integer getId() {
		return id;
	}
	
	public boolean equals(Player other) {
		return this.id==other.getId();
	}
	
	/**
	 * Sets the player to an AI rather than a player.
	 * @param ai Which ai strategy to use. Corresponds to the number in the AI class names.
	 */
	public void setAI(int ai) {
		if(ai==1) { 
			logger.info("Player: " + this.id + " set to AI strategy 1");
			this.strategy = new Strategy1();
		}else if(ai==2) {
			logger.info("Player: " + this.id + " set to AI strategy 2");
			this.strategy = new Strategy2();
		}else if(ai==3) {
			logger.info("Player: " + this.id + " set to AI strategy 3");
		}else this.strategy = null;
	}
	
	/**
	 * Get the strategy used by an AI player.
	 */
	public AbstractAI getAI() {
		return this.strategy;
	}
	
	/**
	 * Get current number of shields owned by player.
	 * <p>Stored as total shields owned. So need to get subtract requirement for current rank to get shields to display</p>
	 * @return
	 */
	public Integer getShields() {
		logger.info("Player " + id + " has " + shields + " shields");
		return shields;
	}
	
	/**
	 * Get player's current rank.
	 * @return
	 */
	public Rank getRank() {
		return rank;
	}
	
	/**
	 * Change the game state to a new one.
	 * @param game the current conditions of the game
	 */
	public void setGame(GameState game) {
		this.game = game;
	}
	
	/**
	 * Get the current conditions of the game
	 * @return
	 */
	public GameState getGame() {
		return this.game;
	}
	
	/**
	 * Gets quest property. Used for testing
	 * @return
	 */
	public GameQuest getQuest() {
		return this.quest;
	}
	
	/**
	 * Sets quest property. Used for testing
	 * @param quest
	 */
	public void setQuest(GameQuest quest) {
		this.quest = quest;
	}
	
	/**
	 * Gets the total bids of the player(Rank bp, weapon bp, ally bp) 
	 * @param state The current conditions of the game
	 * @return total bids
	 */
	public Integer getBidPoints(GameState state) {
		logger.info("Player " + id + " offers " + (offeredBids + temp.stream().mapToInt(c -> c.getBids(state)).sum() + field.stream().mapToInt(c -> c.getBids(state)).sum()) + " bids");
		return offeredBids + temp.stream().mapToInt(c -> c.getBids(state)).sum() + field.stream().mapToInt(c -> c.getBids(state)).sum();
	}
	
	/**
	 * ?offered bids does what?
	 * @param bids Number of cards bids
	 */
	public void bidCards(int bids) {
		if(validBid(bids) && !stoppedBidding) {
			logger.info("Player " + id + " can bid " + bids + " cards. Changing offered bids for player...");
			this.offeredBids = bids;
		}
		else {
			logger.info("Player " + id + " cannot bid " + bids + " cards. Not enough cards in hand.");
		}
	}
	
	/**
	 * Ensures that the player has the number of cards bid or that the bid is not negative
	 * @param num Number of cards bid
	 */
	public boolean validBid(int num) {
		return num <= this.hand.size() && num >= 0;
	}
	
	/**
	 * ?offered bids does what?
	 * @param bids Number of cards bids
	 */
	public void bidCards(int bids, Tests test) {
		if(validBid(bids, test) && !stoppedBidding) {
			logger.info("Player " + id + " can bid " + bids + " cards. Changing offered bids for player...");
			this.offeredBids = bids;
		}
		else {
			logger.info("Player " + id + " cannot bid " + bids + " cards. Not enough cards in hand.");
		}
	}
	
	/**
	 * Ensures that the player has the number of cards bid or that the bid is not negative
	 * @param num Number of cards bid
	 */
	public boolean validBid(int num, Tests test) {
		return num <= this.hand.size() && num >= 0 && num >= test.getMinBid(game);
	}
	
	/**
	 * ?Offered bids does what?
	 * @return
	 */
	public int getOfferedBids() {
		return this.offeredBids;
	}

	/**
	 * Determine the battle points of a player taking into consideration all allies, amour, and weapons
	 * @param state the current conditions of the game
	 * @return the player's total battle points
	 */
	public Integer getBattlePoints(GameState state) {
		logger.info("Player: " + id + " has " + (battlePoints + temp.stream().mapToInt(c -> c.getBp(state)).sum() + field.stream().mapToInt(c -> c.getBp(state)).sum()) + " battle points");
		return battlePoints + temp.stream().mapToInt(c -> c.getBp(state)).sum() + field.stream().mapToInt(c -> c.getBp(state)).sum();
	}

	/**
	 * Get number of shields required to for next rank of player
	 */
	public Integer getRequiredShields() {
		return requiredShields;
	}
	
	/**
	 * Get the current floor of shield that a player can have.
	 * <p>Increases when a player ranks up</p>
	 * @return
	 */
	public int getMinShields() {
		return minShields;
	}

		/**
	 * Add shields to player. If player now has less than minimum amount sets to minimum. If player has more then requiredShields rank up player.
	 * @param shields
	 */
	public void addShields(Integer shields) {		
		logger.info("Player: " + this.id + " received " + shields + " shields");
		this.shields += shields;
		if(this.shields < minShields) this.shields = minShields;
		if(this.shields >= this.requiredShields) {
			updateRank();
		}
	}
	
	/**
	 * Get a list of cards in the player's hand
	 */
	public ArrayList<AdventureCard> getHand() {
		return hand;
	}
	
	/**
	 * Get number of cards in the player's hand.
	 * @return
	 */
	public int getHandSize() {
		return hand.size();
	}
	
	/**
	 * Determine if a player's hand has more than 12 cards and must thus discard cards
	 * @return
	 */
	public boolean isHandFull() {
		if(hand.size()<=12) return false;
		logger.info("Player has " + (hand.size()-12) + " too many cards");
		return true;
	}
	
	/**
	 * Give a player a whole new hand of cards.
	 * @param hand
	 */
	public ArrayList<AdventureCard> setHand(ArrayList<AdventureCard> cards) {
		ArrayList<AdventureCard> discards = new ArrayList<AdventureCard>();
		for(int i=0;i<hand.size();i++) discards.add(hand.get(i));
		for(int i=0;i<discards.size();i++) this.discard(discards.get(i));
		for(int i=0;i<cards.size();i++) this.addCardToHand(cards.get(i));
		return discards;
	}
	
	/**
	 * Add a card to a player's hand
	 * @param card
	 */
	public void addCardToHand(AdventureCard card) {
		logger.info("Adding " + card.getName() + " to hand");
		this.hand.add(card);
	}
	
	/**
	 * Move a card from the player's temporary area to their hand
	 */
	public void tempToHand() {
		temp.forEach(c -> {addCardToHand(c);
			logger.info(c.getName() + " removed from temp");
		});
		temp.clear();
	}
	
	/**
	 * The card is in player's hand.
	 */
	public boolean hasCardInHand(AdventureCard card) {
		return this.hand.contains(card);
	}
	
	/**
	 * Sets the player as ready to play
	 * 
	 * 
	 */
	public void setReady() {
		this.ready = true;
	}
	
	public boolean isReady() {return this.ready;}
	
	/**
	 * Determine if a player can add a card to their temp container
	 * @param card
	 * @return
	 */
	public boolean canPlayTemp(AdventureCard card) {
		if(this.hasCardInHand(card) && !this.temp.contains(card)) return true;
		logger.info("Cannot play " + card.getName());
		return false;
	}
	
	/**
	 * Move a card from the player's hand to their temp container
	 * @param card
	 */
	public void playToTemp(AdventureCard card) {//whats this for
		if(!this.hasCardInHand(card)) return;
		if(this.temp.contains(card)) return;
		logger.info(card.getName() + " added to temp");
		this.temp.add(card);
		this.discard(card);
	}
	
	/**
	 * Move a card from the player's hand to their field
	 * @param card
	 */
	public void addField(Ally card) {//why only ally
		if(!this.hand.contains(card) || this.field.contains(card)) return;
		logger.info(card.getName() + " added to field");
		this.field.add(card);
		this.discard(card);
	}
	
	/**
	 * Move a card from the player's hand to their temp container
	 * @param card
	 */
	public void addTemp(AdventureCard card) {//why all adventure cards
		logger.info(card.getName() + " added to temp");
		this.temp.add(card);
	}
	
	/**
	 * Move multiple card from the player's hand to their temp container
	 */
	public void addTemp(ArrayList<AdventureCard> cards) {
		for(int i=0;i<cards.size();i++) this.addTemp(cards.get(i));
	}
	
	/**
	 * Remove all allies from the playing field.
	 */
	public ArrayList<AdventureCard> clearAlly() {
		ArrayList<AdventureCard> allies = new ArrayList<AdventureCard>();
		for(int i=0;i<this.field.size();i++) {
			if(this.field.get(i) instanceof Ally) {
				allies.add(this.field.get(i));
			}
		}
		for(int i=0;i<allies.size();i++) 
			logger.info(allies.get(i).getName() + " removed from player " + id + "'s field");
		this.field.clear();
		return allies;
	}
	
	/**
	 * Move a card from the player's temp container to their feild container
	 * @param c the card to be moved
	 */
	public void tempToField(AdventureCard c) {
		logger.info(c.getName() + " moved from temp to field");
		field.add(c);
		return;
	}
	
	/**
	 * Remove all cards except for amours from a player's temp container at the end of a quest stage or
	 * tournament round and move all allies to the field
	 * @return
	 */
	public ArrayList<AdventureCard> clearTemp() {
		ArrayList<AdventureCard> a = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> weps = new ArrayList<AdventureCard>();
		
		for(int i=0;i<this.temp.size();i++) {
			if(this.temp.get(i) instanceof Ally) {
				this.tempToField(this.temp.get(i));
			}else if(this.temp.get(i) instanceof Amour) {
				a.add(this.temp.get(i));
			}else if(this.temp.get(i) instanceof Weapon) weps.add(this.temp.get(i));
		}
		
		this.temp = a;
		for(int i=0;i<weps.size();i++) 
			logger.info(weps.get(i).getName() + " removed from player " + id + "'s temp");
		return weps;
	}
	
	/**
	 * Remove all cards from a player's temp container at the end of a quest or tournament and move all allies
	 * to the field
	 * @return
	 */
	public ArrayList<AdventureCard> emptyTemp(){
		ArrayList<AdventureCard> cards = new ArrayList<AdventureCard>();
		for(int i=0;i<this.temp.size();i++) {
			if(this.temp.get(i) instanceof Ally) this.tempToField(this.temp.get(i));
			else cards.add(this.temp.get(i));
		}
		this.temp.clear();
		for(int i=0;i<cards.size();i++)
			logger.info(cards.get(i).getName() + " removed from player " + id + "'s temp");
		return cards;
	}

	/**
	 * Rank up the player. Used defined Rank enum for ranks.
	 */
	private void updateRank() {
		minShields = requiredShields;
		if(rank == Rank.Squire) {
			logger.info("Player: " + this.id + " moved up to rank: Knight");
			requiredShields = 22;
			rank = Rank.Knight;
			battlePoints = 10;
		}
		else if(rank == Rank.Knight) {
			logger.info("Player: " + this.id + " moved up to rank: Champion Knight");
			requiredShields = 32;
			rank = Rank.ChampionKnight;
			battlePoints = 20;
		}
		else if(rank == Rank.ChampionKnight) {
			logger.info("Player: " + this.id + " moved up to rank: Knight of the Round Table");
			rank = Rank.KnightOfTheRoundTable;
		}
	}

	/**
	 * Get the list of cards that the player has equipped.
	 */
	public ArrayList<AdventureCard> getField() {
		return this.field;
	}
	
	/**
	 * Get the list of cards that are in the player's temp container
	 * @return
	 */
	public ArrayList<AdventureCard> getTemp() {
		return this.temp;
	}
	
	/**
	 * Remove an ally from the player's field, to be used in conjunction with the Mordred special ability
	 * @param ally the ally to be removed
	 * @return
	 */
	public AdventureCard removeAlly(Ally ally) {
		this.field.remove(ally);
		logger.info("Removed " + ally.getName() + " from player " + id + "'s field");
		return ally;
	}
	
	
	/**
	 * Sort a players hand from highest battle power to lowest battle power for each type
	 * of card. 
	 * <p>Allies are set as first, then amours, then weapons, then foes, then tests.</p> 
	 * <p>Foes and allies are sorted depending on the current GameState</p>
	 * @param state the current conditions of the game
	 */
	public void sortHand(GameState state) {
		ArrayList<AdventureCard> foes = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> tests = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> weps = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> Am = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> Al = new ArrayList<AdventureCard>();
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Foe) foes.add(this.hand.get(i));
			else if(this.hand.get(i) instanceof Tests) tests.add(this.hand.get(i));
			else if(this.hand.get(i) instanceof Weapon) weps.add(this.hand.get(i));
			else if(this.hand.get(i) instanceof Amour) Am.add(this.hand.get(i));
			else Al.add(this.hand.get(i));
		}
		foes.sort(new CardComparator(state));
		weps.sort(new CardComparator());
		Am.sort(new CardComparator());
		Al.sort(new CardComparator(state));
		this.hand.clear();
		this.hand.addAll(Al);
		this.hand.addAll(Am);
		this.hand.addAll(weps);
		this.hand.addAll(foes);
		this.hand.addAll(tests);
	}

	/**
	 * Remove an adventure card from hand
	 */
	public AdventureCard discard(AdventureCard card) {
		logger.info("Removed " + card.getName() + " form player " + this.id + "'s hand");
		this.hand.remove(card);
		return card;
	}
	
	/**
	 * Gets a card from the player's hand. 
	 * 
	 * @returns  the card from hand or null if it does not exist
	 */
	public AdventureCard getCard(Integer name) {
		logger.info("Looking for card " + name + " in player " + id + "'s hand");
				
		for(AdventureCard card : hand) {
			if(card.getID() == name) {
				logger.info("Found the card in player's hand " + card.getName());
				return card;
			}
		}
		
		return null;
	}
	
	/**
	 * Tests if there is a test card in the hand.
	 */
	public boolean hasTest() {
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Tests) return true;
		}
		return false;
	}
	
	/**
	 * Determine if a player has an ally in play
	 * @param name the name of the ally to check
	 * @return
	 */
	public boolean hasAlly(String name) {
		for(int i=0;i<this.field.size();i++) {
			if(this.field.get(i).getName().equals(name)) return true;
		}
		return false;
	}
	
	/**
	 * Determine if a player has a card in their hand
	 * @param name the name of the card
	 * @return
	 */
	public boolean hasFoe(String name) {
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i).getName().equals(name)) return true;
		}
		return false;
	}
	
	/**
	 * Get the number of foe cards in the player's hand. Used to judge if a player can set up a quest.
	 */
	public int countFoes() {
		int count = 0;
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Foe) count++;
		}
		return count;
	}
	
	/**
	 * ?bidding is over for all players or just this one?
	 */
	public void stopBidding() {
		this.stoppedBidding = true;	
	}
	
	/**
	 * ?Duplicate?
	 * @return
	 */
	public boolean hasStoppedBidding() {
		return this.stoppedBidding;
	}

	/**
	 * Remove the player's shields. Can not remove more shield then the minimum. Minimum represents the zero value of each rank.
	 * @param i number of shields to be removed
	 */
	public void removeShield(int i) {
		logger.info("Player: " + this.id + " lost " + i + " shields");
		this.shields = shields-i;
		if (this.shields<this.minShields) this.shields = this.minShields;
		
	}
	
	/**
	 * Get the tourney property ?Use in player class?
	 * @return
	 */
	public GameTourney getTourney() {
		return this.tourney;
	}
	
	/**
	 * ?Use in player class?
	 * @param tourney
	 */
	public void setTourney(GameTourney tourney) {
		this.tourney = tourney;
	}

	/**
	 * Remove player from quest ?What is it used for?
	 */
	public void removeQuest() {
		this.quest = null;
	}
	
}
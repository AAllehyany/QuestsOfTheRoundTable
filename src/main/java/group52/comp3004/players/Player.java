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
import group52.comp3004.controllers.GameController;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.GameTourney;

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
	private GameState game;
	private GameQuest quest;
	private GameTourney tourney;
	private Integer bidPoints;
	private boolean stoppedBidding;
	private int offeredBids;
	private AbstractAI strategy;
	
	static final private Logger logger = Logger.getLogger(Player.class);
	
	public Player(Integer id, GameController gc, GameState gs) {
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
		bidPoints = 0;
		offeredBids = 0;
		strategy = null;
	}
	
	public Player(Integer id, GameController gc, GameState gs, int s) {
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
		bidPoints = 0;
		offeredBids = 0;
		if(s==1) strategy = new Strategy1();
		else if(s==2) strategy = new Strategy2();
		else if(s==3) strategy = new Strategy3();
		else strategy = null;
	}
	
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
		bidPoints = 0;
		offeredBids = 0;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Integer getRankBP() {
		return this.battlePoints;
	}
	
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
	
	public AbstractAI getAI() {
		return this.strategy;
	}
	
	public Integer getShields() {
		return shields;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public void setGame(GameState game) {
		this.game = game;
	}
	
	public GameState getGame() {
		return this.game;
	}
	
	public GameQuest getQuest() {
		return this.quest;
	}
	
	public void setQuest(GameQuest quest) {
		this.quest = quest;
	}
	
	public Integer getBidPoints(GameState state) {
		logger.info("Player " + id + " offers " + bidPoints + temp.stream().mapToInt(c -> c.getBids(state)).sum() + field.stream().mapToInt(c -> c.getBids(state)).sum() + " bids");
		return bidPoints + temp.stream().mapToInt(c -> c.getBids(state)).sum() + field.stream().mapToInt(c -> c.getBids(state)).sum();
	}
	
	
	public void bidCards(int bids) {
		if(validBid(bids) && !stoppedBidding) this.offeredBids = bids;
	}
	
	
	public boolean validBid(int num) {
		return num <= this.hand.size() && num >= 0;
	}
	
	public int getOfferedBids() {
		return this.offeredBids;
	}
	
	public Integer getBattlePoints(GameState state) {
		logger.info("Player: " + id + " has " + battlePoints + temp.stream().mapToInt(c -> c.getBp()).sum() + field.stream().mapToInt(c -> c.getBp(state)).sum() + " battle points");
		return battlePoints + temp.stream().mapToInt(c -> c.getBp()).sum() + field.stream().mapToInt(c -> c.getBp(state)).sum();
	}

	public Integer getRequiredShields() {
		return requiredShields;
	}
	
	public int getMinShields() {
		return minShields;
	}
	
	public void addShields(Integer shields) {
		logger.info("Player: " + this.id + " received " + shields + " shields");
		this.shields += shields;
		if(this.shields < minShields) this.shields = minShields;
		if(this.shields >= this.requiredShields) {
			updateRank();
		}
	}
	
	
	public ArrayList<AdventureCard> getHand() {
		return hand;
	}
	
	public int getHandSize() {
		return hand.size();
	}
	
	public boolean isHandFull() {
		if(hand.size()<=12) return false;
		logger.info("Player has " + (hand.size()-12) + " too many cards");
		return true;
	}
	
	public ArrayList<AdventureCard> setHand(ArrayList<AdventureCard> cards) {
		ArrayList<AdventureCard> discards = new ArrayList<AdventureCard>();
		for(int i=0;i<hand.size();i++) discards.add(hand.get(i));
		for(int i=0;i<discards.size();i++) this.discard(discards.get(i));
		for(int i=0;i<cards.size();i++) this.addCardToHand(cards.get(i));
		return discards;
	}
	
	
	public void addCardToHand(AdventureCard card) {
		logger.info("Adding " + card.getName() + " to hand");
		this.hand.add(card);
	}
	
	public void tempToHand() {
		temp.forEach(c -> addCardToHand(c));
		temp.forEach(c -> logger.info(c.getName() + " removed from temp"));
		temp.clear();
	}
	
	public boolean canPlayWeapon(AdventureCard weapon) {
		return !this.temp.contains(weapon);
	}
	
	public boolean hasCardInHand(AdventureCard card) {
		return this.hand.contains(card);
	}

	// Determine if a player has a test in their hand
	public boolean hasTest() {
		for(int i=0;i<this.getHand().size();i++) {
			if(this.getHand().get(i) instanceof Tests) return true;
		}
		return false;
	}
	
	// count the number of foes in a player's hand
	public int countFoes() {
		int count = 0;
		for(int i=0;i<this.getHand().size();i++) {
			if(this.getHand().get(i) instanceof Foe) count++;
		}
		return count;
	}
	
	public void playCardToField(Ally card) {//why only Ally
		if(!hasCardInHand(card)) return;
		this.field.add(card);
		this.discard(card);
	}
	
	public void playToTemp(AdventureCard card) {//whats this for
		if(!this.hasCardInHand(card)) return;
		if(card instanceof Weapon && !canPlayWeapon((Weapon) card)) return;
		
		this.temp.add(card);
		this.discard(card);
	}
	
	public void addField(Ally card) {
		logger.info(card.getName() + " added to field");
		this.field.add(card);
		this.discard(card);
	}
	
	public void addTemp(AdventureCard card) {
		logger.info(card.getName() + " added to temp");
		this.temp.add(card);
	}
	
	public void addTemp(ArrayList<AdventureCard> cards) {
		for(int i=0;i<cards.size();i++) this.addTemp(cards.get(i));
	}
	
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
	
	public ArrayList<AdventureCard> clearTemp() {
		ArrayList<AdventureCard> a = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> weps = new ArrayList<AdventureCard>();
		
		for(int i=0;i<this.temp.size();i++) {
			if(this.temp.get(i) instanceof Ally) {
				this.field.add(this.temp.get(i));
			}else if(this.temp.get(i) instanceof Amour) {
				a.add(this.temp.get(i));
			}else if(this.temp.get(i) instanceof Weapon) weps.add(this.temp.get(i));
		}
		
		this.temp = a;
		for(int i=0;i<weps.size();i++) 
			logger.info(weps.get(i).getName() + " removed from player " + id + "'s temp");
		return weps;
	}
	
	public ArrayList<AdventureCard> emptyTemp(){
		ArrayList<AdventureCard> cards = new ArrayList<AdventureCard>();
		for(int i=0;i<this.temp.size();i++) {
			if(this.temp.get(i) instanceof Ally) this.field.add(this.temp.get(i));
			else cards.add(this.temp.get(i));
		}
		this.temp.clear();
		for(int i=0;i<cards.size();i++)
			logger.info(cards.get(i).getName() + " removed from player " + id + "'s temp");
		return cards;
	}

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

	public ArrayList<AdventureCard> getField() {
		return this.field;
	}

	public ArrayList<AdventureCard> getTemp() {
		return this.temp;
	}
	
	//used for Mordred
	public AdventureCard removeAlly(Ally ally) {
		this.field.remove(ally);
		logger.info("Removed " + ally.getName() + " from player " + id + "'s field");
		return ally;
	}
	
	
	/* Sort a players hand from highest battle power to lowest battle power for each type
	 of card. Allies are set as first, then amours, then weapons, then foes, then tests. Foes
	 and allies are sorted depending on the current GameState*/
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

	public AdventureCard discard(AdventureCard card) {
		logger.info("Removed " + card.getName() + " form player " + this.id + "'s hand");
		this.hand.remove(card);
		return card;
	}
	
	public void stopBidding() {
		this.stoppedBidding = true;	
	}
	
	public boolean hasStoppedBidding() {
		return this.stoppedBidding;
	}

	public void removeShield(int i) {
		logger.info("Player: " + this.id + " lost " + i + " shields");
		this.shields = shields-i;
		if (this.shields<this.minShields) this.shields = this.minShields;
		
	}

	public GameTourney getTourney() {
		return this.tourney;
	}
	
	public void setTourney(GameTourney tourney) {
		this.tourney = tourney;
	}

	public void removeQuest() {
		this.quest = null;
	}
	
}
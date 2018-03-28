package group52.comp3004.players;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import group52.comp3004.Strategies.AbstractAI;
import group52.comp3004.Strategies.Strategy1;
import group52.comp3004.Strategies.Strategy2;
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
	private Integer rankBP;
	private Integer battlePoints;
	private Integer requiredShields;
	private List<Integer> weapons;
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
	private ArrayList<AdventureCard> tempWeapons;
	private AbstractAI strategy;
	
	
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
		weapons = new ArrayList<Integer>();
		hand = new ArrayList<>();
		field = new ArrayList<>();
		temp = new ArrayList<>();
		quest = null;
		tourney = null;
		game = gs;
		bidPoints = 0;
		offeredBids = 0;
		tempWeapons = new ArrayList<>();
		strategy = null;
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
		weapons = new ArrayList<Integer>();
		hand = new ArrayList<>();
		field = new ArrayList<>();
		temp = new ArrayList<>();
		quest = null;
		tourney = null;
		game = gs;
		bidPoints = 0;
		offeredBids = 0;
		tempWeapons = new ArrayList<>();
		if(s==1) strategy = new Strategy1();
		else if(s==2) strategy = new Strategy2();
		else strategy = null;
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
		weapons = new ArrayList<Integer>();
		hand = new ArrayList<>();
		field = new ArrayList<>();
		temp = new ArrayList<>();
		quest = null;
		tourney = null;
		bidPoints = 0;
		offeredBids = 0;
		tempWeapons = new ArrayList<>();
	}
	
	/**
	 * Get the id property
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Get the default amount of battle point given by the players rank
	 */
	public Integer getRankBP() {
		return this.battlePoints;
	}
	
	/**
	 * Sets the player to an AI rather than a player.
	 * @param ai Which ai strategy to use. Corresponds to the number in the AI class names.
	 */
	public void setAI(int ai) {
		if(ai==1) this.strategy = new Strategy1();
		else if(ai==2) this.strategy = new Strategy2();
		else this.strategy = null;
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
		//quest.addPlayer(this);
	}
	
	/**
	 * Gets the total bids of the player(Rank bp, weapon bp, ally bp) 
	 * @param state The current conditions of the game
	 * @return total bids
	 */
	public Integer getBidPoints(GameState state) {
		return bidPoints + temp.stream().mapToInt(c -> c.getBids(state)).sum() + field.stream().mapToInt(c -> c.getBids(state)).sum();
	}
	
	/**
	 * ?what does second part do?
	 * @return ?
	 */
	public Integer getBidPoints() {
		return bidPoints + temp.stream().mapToInt(c -> c.getBids()).sum() + field.stream().mapToInt(c -> c.getBids()).sum();
	}
	
	/**
	 * ?offered bids does what?
	 * @param bids Number of cards bids
	 */
	public void bidCards(int bids) {
		if(validBid(bids) && !stoppedBidding) this.offeredBids = bids;
	}
	
	/**
	 * Ensures that the player has the number of cards bid or that the bid is not negative
	 * @param num Number of cards bid
	 */
	public boolean validBid(int num) {
		return num <= this.hand.size() && num >= 0;
	}
	
	/**
	 * ?Offered bids does what?
	 * @return
	 */
	public int getOfferedBids() {
		return this.offeredBids;
	}
	
	/**
	 * ?what does second part do?
	 * @param state ?
	 * @return ?
	 */
	public Integer getBattlePoints(GameState state) {
		return battlePoints + temp.stream().mapToInt(c -> c.getBp()).sum() + field.stream().mapToInt(c -> c.getBp(state)).sum();
	}
	
	/**
	 * ?what does second part do?
	 * @return ?
	 */
	public Integer getBattlePoints() {
		return battlePoints + temp.stream().mapToInt(c -> c.getBp()).sum() + field.stream().mapToInt(c -> c.getBp()).sum();
	}
	
	/**
	 * Empty list of weapons that the player has equiped.
	 */
	public void clearWeapons() {
		this.weapons.clear();
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
	 * Equip a new weapon. Needs a test to ensure that weapon isn't already equiped.
	 * @param weapon ?
	 */
	public void addWeapon(Integer weapon) {
		this.weapons.add(weapon);
	}
	
	/**
	 * Add shields to player. If player now has less than minimum amount sets to minimum. If player has more then requiredShields rank up player.
	 * @param shields
	 */
	public void addShields(Integer shields) {		
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
	 * Give a player a whole new hand of cards.
	 * @param hand
	 */
	public void setHand(ArrayList<AdventureCard> hand) {
		this.hand.clear();
		for(int i=0;i<hand.size();i++) {
			this.hand.add(hand.get(i));
		}
	}
	
	/**
	 * ?
	 * @param card
	 */
	public void addCardToHand(AdventureCard card) {
		logger.info("Adding "+card.getName()+" to hand");
		/*card.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				
				if(id != game.getPlayerByIndex(game.getCurrentPlayer()).getId()) return;
				
				//change so card click behaviour changes based on the phase
				
				if(game.getPhase() == Phase.SetupQuest) {
					if(!(card instanceof Foe) && !(card instanceof Tests) && !(card instanceof Weapon)) {
						logger.info("Clicking illegal card for setting quest");
					}
					else {
						int numFoes = (int) temp.stream().filter(c -> c instanceof Foe).count();
						int numTests = (int) temp.stream().filter(c -> c instanceof Tests).count();
						
						if(card instanceof Tests && numTests > 0) {
							return;
						}
						
						if(!(card instanceof Weapon)) {
							
							if((numFoes + numTests) >= game.getCurrentQuest().getNumStages()) {
								return;
							}
						}
						
						
						// check for adding weapons.
						if( card instanceof Weapon ) {
							if(tempWeapons.stream().map(c -> c.getName()).collect(Collectors.toList()).contains(card.getName())) {
								return;
							}
							
							if(temp.size() > 0 && !(temp.get(temp.size() - 1) instanceof Tests)) {
								logger.info(card.getName()+ " clicked");
								temp.add(card);
								tempWeapons.add(card);
								hand.remove(card);
								controller.updateAll();
							}
								
						}
						else {
							tempWeapons.clear();
							logger.info(card.getName()+ " clicked");
							temp.add(card);
							hand.remove(card);
							controller.updateAll();	
						}
						
					}
				}
				else if(game.getPhase()==Phase.HandleEvent){
					logger.info(card.getName()+ " discarded");
					hand.remove(card);
					controller.updateAll();	
				}
				else if(game.getPhase() == Phase.SetUpTourney || game.getPhase() == Phase.SecondRound) {
					if(!(card instanceof Ally) && !(card instanceof Amour) && !(card instanceof Weapon)) {
						return;
					}
					
					if(card instanceof Weapon) {
						if(!canPlayWeapon((Weapon) card)) return;
						logger.info(card.getName()+ " clicked");
						temp.add(card);
						hand.remove(card);
					}
					else {
						int countAmours = (int) temp.stream().filter(c -> c instanceof Amour).count();
						if(card instanceof Amour && countAmours > 0) return;
			
						logger.info(card.getName()+ " clicked");
						temp.add(card);
						hand.remove(card);
					}
					
					controller.updateAll();
				}else if(game.getPhase() == Phase.Arms) {
					if(!(card instanceof Ally) && !(card instanceof Weapon)) {
						return;
					}else{
						logger.info(card.getName()+ " discarded");
						hand.remove(card);
						controller.updateAll();
					}
				}
				else if(game.getPhase() == Phase.PlayQuest) {
					if(!(card instanceof Ally) && !(card instanceof Amour) && !(card instanceof Weapon)) {
						return;
					}
					
					if(card instanceof Weapon) {
						if(!canPlayWeapon((Weapon) card)) return;
						logger.info(card.getName()+ " clicked");
						temp.add(card);
						hand.remove(card);
					}
					else {
						int countAmours = (int) temp.stream().filter(c -> c instanceof Amour).count();
						if(card instanceof Amour && countAmours > 0) return;
						
						logger.info(card.getName()+ " clicked");
						temp.add(card);
						hand.remove(card);
					}
					
					controller.updateAll();
					
					
				}
				else{
				
					logger.info(card.getName()+ " clicked");
					field.add(card);
					hand.remove(card);
					controller.updateAll();	
				}				
			}
		});*/
		this.hand.add(card);
	}
	
	/**
	 * ?
	 */
	public void tempToHand() {
		temp.forEach(c -> addCardToHand(c));
		temp.clear();
	}
	
	/**
	 * Tests if player already has the weapon equipped
	 * @param weapon That will be attempted to be equipped
	 */
	public boolean canPlayWeapon(Weapon weapon) {
		return !this.temp.contains(weapon);
	}
	
	/**
	 * The card is in player's hand.
	 */
	public boolean hasCardInHand(AdventureCard card) {
		return this.hand.contains(card);
	}
	
	/**
	 * ?
	 * @param card
	 */
	public void playCardToField(Ally card) {//why only Ally
		if(!hasCardInHand(card)) return;
		this.field.add(card);
		this.hand.remove(card);
	}
	
	/**
	 * ?
	 * @param card
	 */
	public void playToTemp(AdventureCard card) {//whats this for
		if(!this.hasCardInHand(card)) return;
		if(card instanceof Weapon && !canPlayWeapon((Weapon) card)) return;
		
		this.temp.add(card);
		this.hand.remove(card);
	}
	
	/**
	 * ?
	 * @param card
	 */
	public void addField(Ally card) {//why only ally
		this.field.add(card);
		this.hand.remove(card);
	}
	
	/**
	 * ?
	 * @param card
	 */
	public void addTemp(AdventureCard card) {//why all adventure cards
		this.temp.add(card);
	}
	
	/**
	 * ?
	 */
	public void addTemp(ArrayList<AdventureCard> cards) {
		for(int i=0;i<cards.size();i++) this.temp.add(cards.get(i));
	}
	
	/**
	 * Remove all allies from the playing field.
	 */
	public void clearAlly() {
		
		for(int i=0;i<this.field.size();i++) {
			if(this.field.get(i) instanceof Ally) {
				this.field.remove(this.temp.get(i));
			}
		}
	}
	
	/**
	 * ?
	 * @return
	 */
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
		
		return weps;
		
		/*this.temp.forEach(card -> {
			if(card instanceof Ally) {
				this.field.add(card);
			}else if(card instanceof Amour) {
				amour = (Amour) card;
			}
		});
		this.temp.clear();
		if(amour!=null)this.temp.add(amour);*/
		
	}

	/**
	 * Rank up the player. Used defined Rank enum for ranks.
	 */
	private void updateRank() {
		minShields = requiredShields;
		if(rank == Rank.Squire) {
			requiredShields = 22;
			rank = Rank.Knight;
			battlePoints = 10;
		}
		else if(rank == Rank.Knight) {
			requiredShields = 32;
			rank = Rank.ChampionKnight;
			battlePoints = 20;
		}
		else if(rank == Rank.ChampionKnight) {
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
	 * ?
	 * @return
	 */
	public ArrayList<AdventureCard> getTemp() {
		return this.temp;
	}
	
	/**
	 * ? duplicate?
	 * @param ally
	 * @return
	 */
	public AdventureCard removeAlly(Ally ally) {
		this.field.remove(ally);
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
	 * Get a list of any duplicated cards in hand. ?What is it used for?
	 * @return 
	 */
	public ArrayList<AdventureCard> getDuplicates(){
		ArrayList<AdventureCard> dupes = new ArrayList<AdventureCard>();
		HashSet<AdventureCard> cards = new HashSet<AdventureCard>();
		for(int i=0;i<this.hand.size();i++) {
			if(!cards.add(this.hand.get(i))) dupes.add(this.hand.get(i));
		}
		return dupes;
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
	 * Get the number of test cards in the players hand. Used to judge if a player can set up a quest.
	 */
	public int countTests() {
		return this.hand.stream().filter(c -> c instanceof Tests).collect(Collectors.toList()).size();
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
	 * Get the number of foes less than a certain battle power in a player's hand. Used to judge if a player can set up a quest.
	 * @param bp The battle power trying to pass
	 * @return
	 */
	public int countFoes(int bp) {
		int count =0;
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Foe && this.hand.get(i).getBp()<bp) count++;
		}
		return count;
	}
	
	/**
	 * Get the foes less than a certain battle power in a player's hand. ?What is it used for?
	 * @param bp The battle power trying to pass
	 * @return List of foes in hand that has a battle power greater than bp
	 */
	public ArrayList<AdventureCard> getFoes(int bp){
		ArrayList<AdventureCard> foes = new ArrayList<AdventureCard>();
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Foe && this.hand.get(i).getBp()<bp) {
				foes.add(this.hand.get(i));
			}
		}
		return foes;
	}
	
	// 
	/**
	 * get the foes of unique battle powers in a player's hand depending on the GameState. ?Duplicate?
	 * @param state the current conditions of the game 
	 * @return
	 */
	public ArrayList<AdventureCard> getUniqueFoes(GameState state){
		ArrayList<AdventureCard> uFoes = new ArrayList<AdventureCard>();
		HashSet<Integer> bps = new HashSet<Integer>();
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Foe) {
				Foe f;
				f = (Foe) this.hand.get(i);
				if(bps.add(f.getBp(state))) uFoes.add(this.hand.get(i));
			}
		}
		return uFoes;
	}
	
	// count the foes of unique battle powers in a player's hand depending on the GameState
	/**
	 * ?Duplicate?
	 * @param state the current conditions of the game
	 * @return
	 */
	public int numUniqueFoes(GameState state) {
		int numUFoes = 0;
		HashSet<Integer> bps = new HashSet<Integer>();
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Foe) {
				Foe f = (Foe) this.hand.get(i);
				if(bps.add(f.getBp(state))) numUFoes++;
			}
		}
		return numUFoes;
	}
	
	/**
	 * Discard an adventure card from hand
	 */
	public void discard(AdventureCard card) {
		this.hand.remove(card);
	}
	// Determine if a player has an amour in their hand
	public boolean hasAmour() {
		for(int i=0;i<this.temp.size();i++) {
			if (this.temp.get(i) instanceof Amour) return true;
		}
		return false;
	}
	
	/**
	 * Test for whether there is an amour in the player's hand.
	 */
	public boolean hasAmourInHand() {
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Amour) return true;
		}
		return false;
	}
	
	/**
	 * ?What is it used for?
	 * @return
	 */
	public AdventureCard getAmourInHand() {
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Amour) return this.hand.remove(i);
		}
		return null;
	}
	
	/**
	 * Test for whether there is an ally in the player's hand.
	 * @return
	 */
	public boolean hasAllyInHand() {
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Ally) return true;
		}
		return false;
	}
	
	/**
	 * ?What is this used for?
	 * @param state the current conditions of the game
	 * @return
	 */
	public AdventureCard getStrongestAllyInHand(GameState state) {
		this.sortHand(state);
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Ally) return this.hand.remove(i);
		}
		return null;
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
	 * @param i number of sheilds to be removed
	 */
	public void removeShield(int i) {
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
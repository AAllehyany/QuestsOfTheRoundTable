package group52.comp3004.cards;

import java.util.HashSet;

import org.apache.log4j.Logger;

import group52.comp3004.decks.Deck;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;

/**
 * Adds functionality for foe type adventure cards. Used for quest creation.
 * @author Sandy
 *
 */
public class Foe extends AdventureCard{

	private final int highBp;
	private HashSet<Weapon> weapons;
	private HashSet<String> quests = new HashSet<String>();
	
	final static Logger logger = Logger.getLogger(Foe.class);
	// Constructor for Foes with only one battle power
	/**
	 * Constructor for foes with a single battle power value
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 * @param bp Battle point value of the ally
	 */
	public Foe(String name, int bp) {
		super(name);
		this.bp = bp;
		this.highBp = bp;
		this.weapons = new HashSet<Weapon>();
		quests.add("Holy_Grail");
		quests.add("Queens_Honor");
	}
	
	// Construct for Foes with 2 battle powers. highBp is the higher battle power (not what 
	// needs to be added to achieve this battle power)
	/**
	 * Constructor for foes with a special battle power. 
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 * @param bp Battle point value of the foe
	 * @param highBp The battle power to use if in correct quest type
	 * @param quest The quest's that increase battle power.
	 */
	public Foe(String name, int bp, int highBp, String quest) {
		super(name);
		this.bp = bp;
		this.highBp = highBp;
		this.weapons = new HashSet<Weapon>();
		quests.add("Holy_Grail");
		quests.add("Queens_Honor");
		quests.add(quest);
	}
	// getBp used in the case that the GameState can affect a Foe's battle power

	/**
	 * Get the card's battle points. Uses the state determine if a special case needs to be considered. 
	 * @param state Current status of the game
	 */
	public int getBp(GameState state) {
		if(state.getCurrentQuest()!=null && 
				quests.contains(state.getCurrentQuest().getQuest().getName())){
			logger.info(super.getName() + " has battle power " + (this.highBp + weapons.stream().mapToInt(w -> w.getBp()).sum()));
			return this.highBp + weapons.stream().mapToInt(w -> w.getBp()).sum();
		}
		logger.info(super.getName() + " has battle power " + (this.bp + weapons.stream().mapToInt(w -> w.getBp()).sum()));
		return this.bp + weapons.stream().mapToInt(w -> w.getBp()).sum();
	}
	
	/**
	 * Get a foe's set battle points (the lower points that is shown on the card)
	 */
	public int getBp() {
		return this.bp;
	}
	
	/**
	 * Get the weapons equipped by the foe.
	 * @return the set of all weapons
	 */
	public HashSet<Weapon> getWeapons(){
		return this.weapons;
	}

	/**
	 * Is a weapon equipped by a foe
	 * @param wep Weapon searched for 
	 * @return true if weapon is in the set
	 */
    public boolean hasWeapon(Weapon wep){
        return this.weapons.contains(wep);
    }

    /**
     * Add a new weapon to set
     * @param wep weapon card
     * @return weapon added successfully
     */
    public boolean addWeapon(Weapon wep){
		return this.weapons.add(wep);
    }

    /**
     * Empty equipped weapon set
     */
    public void clearWeapons(){
        this.weapons.clear();
    }
    
    /**
     * Use Mordred's special ability to force another player to discard an ally from their field
     * @param state the current game conditions
     * @param owner the player who uses Mordred's special ability
     * @param player the player who is the target of Mordred's special ability
     * @param ally the ally to be removed from the player
     * @return whether the ally removal was successful or not
     */
    public boolean MordredSpecial(GameState state, Player owner, int player, Ally ally) {
    	if(!this.getName().equals("Mordred")) {
    		logger.info("NOT MORDRED\n");
    		return false;
    	}
    	if(player>state.numPlayers()) {
    		logger.info("INVALID PLAYER\n");
    		return false;
    	}
    	if(!state.getPlayerByIndex(player).getField().contains(ally)) {
    		logger.info("INVALID ALLY\n");
    		return false;
    	}
    	state.getAdventureDeck().discard(state.getPlayerByIndex(player).removeAlly(ally));
    	owner.discard(this);
    	state.getAdventureDeck().discard(this);
    	state.getAdventureDeck().discard(state.getPlayerByIndex(player).removeAlly(ally));
    	state.getAdventureDeck().discard(this);
    	owner.getHand().remove(this);
    	return true;
    }
}

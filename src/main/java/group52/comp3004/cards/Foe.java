package group52.comp3004.cards;

import java.util.HashSet;

import org.apache.log4j.Logger;

import group52.comp3004.decks.Deck;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;

public class Foe extends AdventureCard{

	private final int bp;
	private final int highBp;
	private HashSet<Weapon> weapons;
	private HashSet<String> quests = new HashSet<String>();
	
	final static Logger logger = Logger.getLogger(Foe.class);
	
	// Constructor for Foes with only one battle power
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
	public Foe(String name, int bp, int highBp, String quest) {
		super(name);
		this.bp = bp;
		this.highBp = highBp;
		this.weapons = new HashSet<Weapon>();
		quests.add("Holy_Grail");
		quests.add("Queens_Honor");
		quests.add(quest);
		// TODO Auto-generated constructor stub
	}
	
	public int getBp(GameState state) {
		if(state.getCurrentQuest()!=null && 
				quests.contains(state.getCurrentQuest().getQuest().getName())){
			logger.info(super.getName() + " has battle power " + this.highBp + weapons.stream().mapToInt(w -> w.getBp()).sum());
			return this.highBp + weapons.stream().mapToInt(w -> w.getBp()).sum();
		}
		logger.info(super.getName() + " has battle power " + this.bp + weapons.stream().mapToInt(w -> w.getBp()).sum());
		return this.bp + weapons.stream().mapToInt(w -> w.getBp()).sum();
	}
	
	public HashSet<Weapon> getWeapons(){
		return this.weapons;
	}

    public boolean hasWeapon(Weapon wep){
        return this.weapons.contains(wep);
    }

    public boolean addWeapon(Weapon wep){
		return this.weapons.add(wep);
    }

    public void clearWeapons(){
        this.weapons.clear();
    }
    
    // spaghetti code to implement Mordred's special ability
    public boolean MordredSpecial(GameState state, Player owner, int player, Ally ally, Deck<AdventureCard> adventureDeck) {
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
    	adventureDeck.discardCard(state.getPlayerByIndex(player).removeAlly(ally));
    	owner.discard(this);
    	adventureDeck.discardCard(this);
    	return true;
    }
}

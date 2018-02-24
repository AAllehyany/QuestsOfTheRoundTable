package group52.comp3004.cards;

import java.util.HashSet;

import group52.comp3004.ResourceManager;
import group52.comp3004.decks.Deck;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;

public class Foe extends AdventureCard{

	private final int bp;
	private final int highBp;
	private HashSet<Weapon> weapons;
	private HashSet<String> quests = new HashSet<String>();
	
	public Foe(String name, ResourceManager rm, int bp) {
		super(name);
		this.bp = bp;
		this.highBp = 0;
		this.weapons = new HashSet<Weapon>();
		quests.add("Holy_Grail");
		quests.add("Queens_Honor");
	}
	
	public Foe(String name, ResourceManager rm, int bp, int highBp, String quest) {
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
			return this.highBp + weapons.stream().mapToInt(w -> w.getBp()).sum();
		}
		return this.bp + weapons.stream().mapToInt(w -> w.getBp()).sum();
	}
	public int getBp() {
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
    		System.out.println("NOT MORDRED\n");
    		return false;
    	}
    	if(player>state.numPlayers()) {
    		System.out.println("INVALID PLAYER\n");
    		return false;
    	}
    	if(!state.getPlayerByIndex(player).getField().contains(ally)) {
    		System.out.println("INVALID ALLY\n");
    		return false;
    	}
    	adventureDeck.discard(state.getPlayerByIndex(player).removeAlly(ally));
    	adventureDeck.discard(this);
    	owner.getHand().remove(this);
    	return true;
    }
}

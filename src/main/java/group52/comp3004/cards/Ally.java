package group52.comp3004.cards;

import java.util.ArrayList;

import group52.comp3004.ResourceManager;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;

public class Ally extends AdventureCard
{
	private int bp;
	private int bids;
	private String prereq;
	private int bonus;
	private boolean merlin = false;
	
	// TODO special abilities
	
	public Ally(String name, ResourceManager rm, int bp, int bids) {
		
		super(name, rm);
		this.bp = bp;
		this.bids = bids;
		
	}
	
	public Ally(String name, ResourceManager rm, int bp, int bids, String prereq, int bonus) {
		super(name, rm);
		this.bp = bp;
		this.bids = bids;
		this.prereq = prereq;
		this.bonus = bonus;
	}
	
	// should only be used for Merlin
	public Ally(String name, ResourceManager rm) {
		super(name, rm);
		if(name.equals("Merlin")) this.merlin = true;
	}
	
	// determine if an ally's special ability is satisfied
	public boolean bonusSatisfied(GameState state) {
		if(this.prereq==null) return false;
		if(this.prereq.equals(state.getCurrentQuest().getQuest().getName())) {
			return true;
		}
		ArrayList<Player> players = new ArrayList<Player>(state.getAllPlayers());
		for(int i=0;i<players.size();i++) {
			ArrayList<AdventureCard> field = players.get(i).getField();
			for(int j=0;j<field.size();j++) {
				if(field.get(j).getName().equals(this.prereq)) {
					return true;
				}
			}
		}
		return false;
	}
	
	// spaghetti code to start Merlin's special ability
	public ArrayList<AdventureCard> StartMerlinSpecial(GameState state, int stage) {
		ArrayList<AdventureCard> cards = new ArrayList<AdventureCard>();
		if(!this.merlin) {
			System.out.println("NOT MERLIN\n");
			return null;
		}
		GameQuest q = state.getCurrentQuest();
		if(stage>=q.getNumStages()) {
			System.out.println("INVALID STAGE");
			return null;
		}
		Stage s = q.getStage(stage-1);
		if(s.isTestStage()) {
			return cards;
		}else {
			Foe f = s.getFoe();
			cards.add(f);
			cards.addAll(f.getWeapons());
			for(int i=0;i<cards.size();i++) {
				cards.get(i).setFaceUp();
			}
			return cards;
		}
	}
	
	// spaghetti code to end Merlin's special ability
	public boolean EndMerlinSpecial(ArrayList<AdventureCard> cards) {
		if(!this.merlin) return false;
		for(int i=0;i<cards.size();i++) {
			cards.get(i).setFaceDown();
		}
		return true;
	}

	public int getBp() {
		return bp;
	}
	
	public int getBids() {
		return bids;
	}
	
	public int getBonus() {
		return bonus;
	}
	

}

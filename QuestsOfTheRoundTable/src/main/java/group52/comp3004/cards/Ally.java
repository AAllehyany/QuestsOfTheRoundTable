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
	
	public boolean bonusQuestSatisfied(GameState state) {
		if(this.prereq==null) return false;
		if(this.prereq.equals(state.getCurrentQuest().getQuest().getName())) {
			return true;
		}
		return false;
	}
	
	public boolean bonusAllySatisfied(GameState state) {
		if(this.prereq==null) return false;
		for(int i=0;i<state.numPlayers();i++) {
			Player p = state.getPlayerByIndex(i);
			ArrayList<AdventureCard> field = p.getField();
			for(int j=0;j<field.size();j++) {
				if(field.get(j).getName().equals(this.prereq)) {
					return true;
				}
			}
		}
		return false;
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

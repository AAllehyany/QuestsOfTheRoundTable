

package group52.comp3004.players;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

import group52.comp3004.Hand;

public class Player {
	
	private Integer id;
	private Integer shields;
	private Rank rank;
	private Integer battlePoints;
	private Integer requiredShields;
	private HashSet<Weapon> weapons;
	private List <Foe> foes;
	private int minShields;
	//private Hand hand;
	
	public Player(Integer id) {
		this.id = id;
		shields = 10;
		rank = Rank.Squire;
		battlePoints = 5;
		requiredShields = 15;
		minShields = 10;
		foes = new Arraylist<Foe>();
		weapons = new HashSet<Weapon>();
		//hand = new Hand();
	}
	
	public Integer getId() {
		return id;
	}
	
	public Integer getShields() {
		return shields;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Integer getBattlePoints() {
		Iterator<Weapon> itr = this.weapons.iterator();
		int weaponsBP = 0;
		while(itr.hasNext()){
			weaponsBP += itr.next().getBP();
		}
		return battlePoints + weaponsBP;
	}
	
	public void addFoes(Foe addedFoe){
		return this.foes.add(addedFoe);
	}
	
	public void removeFoe(Foe removedFoe){
		return this.foes.remove(foes.indexOf(removedFoe));	
	}
	
	public void clearFoes(){
		return this.foes.clear();	
	}
	
	public void clearWeapons() {
		Iterator<Weapon> itr = this.weapon.iterator();
		while(itr.hasNext()){
			adventureDeck.discard(itr.next());
		}
		this.weapons.clear();
	}

	public Integer getRequiredShields() {
		return requiredShields;
	}

	public boolean hasWeapon(Weapon wep){
		return this.weapons.contains(wep);
	}
	
	 public boolean addWeapon(Weapon wep){
		return this.weapons.add(wep);
    }
	
	public void addShields(Integer shields) {		
		this.shields += shields;
		if(this.shields < minShields) this.shields = minShields;
		if(this.shields >= this.requiredShields) {
			updateRank();
		}
	}
	
	
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
}

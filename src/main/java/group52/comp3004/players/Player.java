package group52.comp3004.players;

import java.util.ArrayList;
import java.util.List;

import group52.comp3004.Hand;

public class Player {
	
	private Integer id;
	private Integer shields;
	private Rank rank;
	private Integer battlePoints;
	private Integer requiredShields;
	private List<Integer> weapons;
	private int minShields;
	//private Hand hand;
	
	public Player(Integer id) {
		this.id = id;
		shields = 10;
		rank = Rank.Squire;
		battlePoints = 5;
		requiredShields = 15;
		minShields = 10;
		weapons = new ArrayList<Integer>();
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
		return battlePoints + weapons.stream().mapToInt(Integer::intValue).sum();
	}
	
	public void clearWeapons() {
		this.weapons.clear();
	}

	public Integer getRequiredShields() {
		return requiredShields;
	}
	
	public void addWeapon(Integer weapon) {
		this.weapons.add(weapon);
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

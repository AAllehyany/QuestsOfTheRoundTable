package group52.comp3004.players;

import group52.comp3004.Hand;

public class Player {
	
	private Integer id;
	private Integer shields;
	private Rank rank;
	private Integer battlePoints;
	private Integer requiredShields;
	//private Hand hand;
	
	public Player(Integer id) {
		this.id = id;
		shields = 10;
		rank = Rank.Squire;
		battlePoints = 5;
		requiredShields = 15;
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
		return battlePoints;
	}

	public Integer getRequiredShields() {
		return requiredShields;
	}
	
	public void addShields(Integer shields) {		
		this.shields += shields;
		if(this.shields < 0) this.shields = 0;
		if(this.shields >= this.requiredShields) {
			updateRank();
		}
	}
	
	
	private void updateRank() {
		if(rank == Rank.Squire) {
			requiredShields = 22;
			rank = Rank.Knight;
		}
		else if(rank == Rank.Knight) {
			requiredShields = 32;
			rank = Rank.ChampionKnight;
		}
		else if(rank == Rank.ChampionKnight) {
			rank = Rank.KnightOfTheRoundTable;
		}
	}
}

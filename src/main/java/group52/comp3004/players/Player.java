package group52.comp3004.players;

public class Player {
	
	private Integer id;
	private Integer shields;
	private Rank rank;
	private Integer battlePoints;
	private Integer requiredShields;
	
	//TODO: hand cards and equipped weapons
	//TODO: Possibly the field of the current player? Or make that in the game state?
	
	public Player(Integer id) {
		this.id = id;
		shields = 10;
		rank = Rank.Squire;
		battlePoints = 5;
		requiredShields = 5;
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
		this.updateRank();
	}
	
	
	private void updateRank() {
		if(this.shields < 15) {
			this.rank = Rank.Squire;
		}
		else if(this.shields >= 15 && this.shields < 22) {
			this.rank = Rank.Knight;
		}
		else if(this.shields >= 22 && this.shields < 32) {
			this.rank = Rank.ChampionKnight;
		}
		else { this.rank = Rank.KnightOfTheRoundTable; }
	}
}

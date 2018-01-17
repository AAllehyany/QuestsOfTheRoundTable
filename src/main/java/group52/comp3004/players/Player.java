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
		shields = 0;
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
		Integer remaining = (this.shields + shields) - requiredShields;
		
		if(remaining >= 0) {
			// Upgrade Rank??
			// Set new required shields??
		}
		
		this.shields += shields;
	}
	
}

package group52.comp3004.cards;

public class Tourneys extends StoryCard{


	private final int bonusShields;
	public Tourneys(String name, int bonusShields) {
		super(name);
		this.bonusShields = bonusShields;
	}
	
	public int getShields() {
		return this.bonusShields;
	}
}

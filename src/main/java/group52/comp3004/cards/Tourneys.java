package group52.comp3004;

public class Tourneys extends StoryCard{


	private int bonusShield;
	public Tourneys(CardType name) {
		super(name);
		bonusShield =0;
		// TODO Auto-generated constructor stub
	}
	
	public void setReward(int bonusShield) {
		this.bonusShield= bonusShield;
	}

	public int getReward() {
		return this.bonusShield;
	}
}

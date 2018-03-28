package group52.comp3004.cards;

public class Tourneys extends StoryCard{


	private final int bonusShields;
	
	/**
	 * Adds functionality to Tourney type event cards.
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 * @param bonusShields Number of bonus shields that are awarded to the winner.
	 */
	public Tourneys(String name, int bonusShields) {
		super(name);
		this.bonusShields = bonusShields;
	}
	
	/**
	 * Get the number of bonus shields awarded to winner
	 * @return bonus shield property
	 */
	public int getShields() {
		return this.bonusShields;
	}
}

package group52.comp3004.cards;

import group52.comp3004.game.GameState;

public class Tests extends AdventureCard {

	private final int minimalBid;
	
	/**
	 * Adds tests functionally. Used for quest creation
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 * @param minimalBid The minimal number of bids(cards discarded) in order for a player to enter a test.
	 */
	public Tests(String name, int minimalBid) {
		super(name);
		this.minimalBid = minimalBid;
	}

	/**
	 * Gets the minimalBid property.
	 */
	public int getMinBid(GameState state) {
		if(this.getName().equals("Questing_Beast") && 
				state.getCurrentQuest().getQuest().getName().equals("Questing_Beast_Quest"))
			return this.minimalBid+1;
		return this.minimalBid;
	}
}

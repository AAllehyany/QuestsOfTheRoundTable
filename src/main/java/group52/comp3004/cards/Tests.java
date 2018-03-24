package group52.comp3004.cards;

public class Tests extends AdventureCard {

	private final int minimalBid;
	
	/**
	 * Adds tests functionally. Used for quest creation
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 * @param minimalBid ?
	 */
	public Tests(String name, int minimalBid) {
		super(name);
		this.minimalBid = minimalBid;
	}

	/**
	 * 
	 * @return
	 */
	public int getMinBid() {
		return this.minimalBid;
	}
}

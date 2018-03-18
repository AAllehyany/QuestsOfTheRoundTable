package group52.comp3004.cards;

public class Tests extends AdventureCard {

	private final int minimalBid;
	
	public Tests(String name, int minimalBid) {
		super(name);
		this.minimalBid = minimalBid;
	}

	
	public int getMinBid() {
		return this.minimalBid;
	}
}

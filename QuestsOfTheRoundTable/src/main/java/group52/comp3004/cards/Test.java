package group52.comp3004.cards;

public class Test extends AdventureCard {

	private final int minimalBid;
	
	public Test(String name, int minimalBid) {
		super(name);
		this.minimalBid = minimalBid;
		// TODO Auto-generated constructor stub
	}

	
	public int getMinBid() {
		return this.minimalBid;
	}
}

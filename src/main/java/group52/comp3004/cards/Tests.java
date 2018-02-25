package group52.comp3004.cards;

import group52.comp3004.ResourceManager;

public class Tests extends AdventureCard {

	private final int minimalBid;
	
	public Tests(String name, ResourceManager rm, int minimalBid) {
		super(name, rm);
		this.minimalBid = minimalBid;
		// TODO Auto-generated constructor stub
	}

	
	public int getMinBid() {
		return this.minimalBid;
	}
}

package group52.comp3004.cards;

import group52.comp3004.cards.AdventureCard;

public class Amour extends AdventureCard{
	public String name;
	private int bp;
	private int bids;
	
	public Amour(String name, int bp, int bids){
		super(name);
		this.bp = bp;
		this.bids = bids;
	}
	
	public int getBP() {
		return this.bp;
	}
	
	public int getBids() {
		return this.bids;
	}
}

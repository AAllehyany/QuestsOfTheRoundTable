package group52.comp3004.cards;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.ResourceManager;

public class Amour extends AdventureCard{
	public String name;
	private int bp;
	private int bids;
	
	public Amour(String name, ResourceManager rm, int bp, int bids){
		super(name, rm);
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

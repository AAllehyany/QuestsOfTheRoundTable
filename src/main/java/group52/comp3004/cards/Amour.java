package group52.comp3004.cards;

import group52.comp3004.game.GameState;

/**
 * Adds functionally for amour type adventure cards
 * @author Sandy
 *
 */
public class Amour extends AdventureCard{
	public String name;
	private int bp;
	private int bids;
	
	/**
	 * Constructor for amour cards
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 * @param bp Battle point value of the ally
	 * @param bids ?
	 */
	public Amour(String name, int bp, int bids){
		super(name);
		this.bp = bp;
		this.bids = bids;
	}
}

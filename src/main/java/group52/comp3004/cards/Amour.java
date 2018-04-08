package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.game.GameState;

/**
 * Adds functionally for amour type adventure cards
 * @author Sandy
 *
 */
public class Amour extends AdventureCard{
	private final static Logger logger = Logger.getLogger(Amour.class);
	
	/**
	 * Constructor for amour cards
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 * @param bp Battle point value of the amour
	 * @param bids The number of bids the amour provides to a test
	 */
	public Amour(String name, int bp, int bids){
		super(name);
		this.bp = bp;
		this.bids = bids;
	}
	
	public int getBp(GameState state) {
		logger.info(super.getName() + " has battle power " + this.bp);
		return this.bp;
	}
	
	public int getBids(GameState state) {
		logger.info(super.getName() + " adds " + this.bids + " bids");
		return this.bids;
	}
}

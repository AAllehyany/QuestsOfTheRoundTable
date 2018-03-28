package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.game.GameState;

public class Amour extends AdventureCard{
	public String name;
	private int bp;
	private int bids;
	private final static Logger logger = Logger.getLogger(Amour.class);
	
	public Amour(String name, int bp, int bids){
		super(name);
		this.bp = bp;
		this.bids = bids;
	}
	
	public int getBp(GameState state){
		return this.bp;
	}
	
	public int getBp() {
		logger.info(super.getName() + " has battle power " + this.bp);
		return this.bp;
	}
	
	public int getBids() {
		logger.info(super.getName() + " adds " + this.bids + " bids");
		return this.bids;
	}
}

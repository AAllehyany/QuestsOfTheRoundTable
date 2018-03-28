package group52.comp3004.cards;

import group52.comp3004.game.GameState;

/**
 * Adds functional for adventure type cards. Should not be generally used. Use one of its subclasses when building a deck instead.
 * @author team
 *
 */
public class AdventureCard extends Card{

	protected int bp;
	protected int bids;
	
	/**
	 * Basic constructor for an adventure card.
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 */
	public AdventureCard(String name) {
		super(name);
	}

	/**
	 * Get the card's battle points. Uses the state determine if a special case needs to be considered.
	 * @param state the current conditions of the game
	 * @return battle points
	 */
	public int getBp(GameState state) {
		return this.bp;
	}
	
	/**
	 * Get the card's battle points
	 * @return current battle points
	 */
	public int getBp() {
		return this.bp;
	}
	
	/**
	 * The number of cards bid during a test stage in a quest
	 * @param state current conditions of the game
	 * @return cards bid
	 */
	public int getBids(GameState state) {
		return 0;
	}
	
	/**
	 * The number of cards bid during a test stage in a quest
	 * @return cards bid
	 */
	public int getBids() {
		return 0;
	}
}
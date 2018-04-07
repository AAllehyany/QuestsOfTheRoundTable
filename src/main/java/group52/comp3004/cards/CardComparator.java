package group52.comp3004.cards;

import java.util.Comparator;

import group52.comp3004.game.GameState;

/**
 * Sorts a list of cards by battle power in a decreasing order.
 * @author Sandy
 *
 */
public class CardComparator implements Comparator<AdventureCard>{
	private GameState state;
	
	/**
	 * Default constructor
	 */
	public CardComparator() {}
	
	/**
	 * Constructor that uses a game state
	 * @param state the current conditions of the game
	 */
	public CardComparator(GameState state) {
		this.state = state;
	}
	
	/**
	 * Compare two cards to see if which one has the higher battle power
	 */
	public int compare(AdventureCard a, AdventureCard b) {
		if(this.state!=null) {
			return b.getBp(state)-a.getBp(state);
		}
		return b.getBp()-a.getBp();
	}
}

package group52.comp3004.cards;

import java.util.Comparator;

import group52.comp3004.game.GameState;

public class CardComparator implements Comparator<AdventureCard>{
	private GameState state;
	
	public CardComparator() {}
	
	public CardComparator(GameState state) {
		this.state = state;
	}
	
	
	public int compare(AdventureCard a, AdventureCard b) {
		if(this.state!=null) {
			return b.getBp(state)-a.getBp(state);
		}
		return b.getBp()-a.getBp();
	}
}

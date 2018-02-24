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
			return a.getBp(state) < b.getBp(state) ? -1 : a.getBp(state) == b.getBp(state) ? 0 : 1;
		}
		return a.getBp() < b.getBp() ? -1 : a.getBp() == b.getBp() ? 0 : 1;
	}
}

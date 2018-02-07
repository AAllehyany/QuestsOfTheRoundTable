package group52.comp3004.GUI.cards;

import group52.comp3004.cards.Card;
import javafx.scene.control.Label;

public class GameCard extends Label {

	
	private final Card card;

	/**
	 * @param card
	 */
	public GameCard(Card card) {
		super();
		this.card = card;
		
//		this.setWidth(76);
//		this.setHeight(111);
//		this.setFill(Color.BLACK);
		
		setText(card.getName());
	}
	
	public Card getCard() {
		return this.card;
	}
	
	
}

package group52.comp3004.cards;

import javafx.scene.shape.Rectangle;
public class Card {
	
	//attributes that a card has
	private String name;
	private boolean isFacedUp;
	private Rectangle cardFace;
	
	public Card(String name) {
		this.name= name;
		isFacedUp = false;
		//test
		boolean stuff = true;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		String str ="";
		if(isFacedUp) {
			str+= name;
		}else {
			str = "face down";
		}
		return str;
	}
}

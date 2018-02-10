package group52.comp3004.cards;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import group52.comp3004.ResourceManager;
import group52.comp3004.controllers.*;

public class Card extends Rectangle{
	
	//attributes that a card has
	private String name;
	private boolean isFaceUp;

	//card face variables
	protected ResourceManager resman;
	protected ImagePattern front;
	protected ImagePattern back;
	
	//DESCRIPTION: representation of a player's card
	//			   extends Rectangle to make it easier to deal with (no get/sets needed)
	//			   Only subclasses should be created
	//			   ERROR: If a card is solid black it likely means it is a Card not a subclass
	public Card(String name) {
		super(50,75);
		this.name= name;
		isFaceUp = true; //used to determine with face to show front/back
		this.setArcHeight(20);
		this.setArcWidth(10);
	
		front = null;
		back = null;
	}

	public String getName() {
		return name;
	}
	
	public String toString() {
		String str ="";
		if(isFaceUp) {
			str+= name;
		}else {
			str = "face down";
		}
		return str;
	}
}

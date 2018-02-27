package group52.comp3004.cards;

import group52.comp3004.ResourceManager;
import group52.comp3004.controllers.CardClickBehaviour;
import group52.comp3004.players.Player;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Card extends Rectangle{
	
	//attributes that a card has
	private String name;
	private boolean isFaceUp;

	//card face variables
	protected ResourceManager resman;
	protected ImagePattern front;
	protected ImagePattern back;
	
	protected CardClickBehaviour clickBehaviour;
	
	//DESCRIPTION: representation of a player's card
	//			   extends Rectangle to make it easier to deal with (no get/sets needed)
	//			   Only subclasses should be created
	//			   ERROR: If a card is solid black it likely means it is a Card not a subclass
	public Card(String name, ResourceManager rm) {
		super(71,100);
		this.name= name;
		resman = rm;
		isFaceUp = true; //used to determine which face to show front/back
		this.setArcHeight(20);
		this.setArcWidth(10);
		
		//card faces
		front = null;
		back = null;
	}
	
	public void flipCard() {
		if(isFaceUp) {
			isFaceUp = false;
			this.setFill(back);
		}
		else {
			isFaceUp = true;
			this.setFill(front);
		}
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
	
	public boolean getFlipped() {
		return this.isFaceUp;
	}
	
	public void setFaceUp() {
		this.isFaceUp = true;
		this.setFill(front);
		return;
	}
	
	public void setFaceDown() {
		this.isFaceUp = false;
		this.setFill(back);
		return;
	}
}

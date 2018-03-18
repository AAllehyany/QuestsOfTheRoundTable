package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.players.Player;

public class Card{
	
	//attributes that a card has
	private String name;
	private boolean isFaceUp;
	
	final static Logger logger = Logger.getLogger(Card.class);
	//DESCRIPTION: representation of a player's card
	//			   extends Rectangle to make it easier to deal with (no get/sets needed)
	//			   Only subclasses should be created
	//			   ERROR: If a card is solid black it likely means it is a Card not a subclass
	public Card(String name) {
		this.name= name;
		isFaceUp = true; //used to determine which face to show front/back
	}
	
	public void flipUp() {
			isFaceUp = true;
	}
	public void flipDown() {
		isFaceUp = false;
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
		return;
	}
	
	public void setFaceDown() {
		this.isFaceUp = false;
		return;
	}
	
	public boolean equals(Card c) {
		return c.getName().equals(this.getName());
	}
}

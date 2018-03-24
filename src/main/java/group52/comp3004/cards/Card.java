package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.players.Player;

/**
 * Representation of an individual card in the game.
 * <p>Error condition: If a card loads with a black face it means the name doesn't have a image file.</p>
 * @author Sandy
 *
 */
public class Card{

	private String name;
	private boolean isFaceUp;
	private int id;
	
	final static Logger logger = Logger.getLogger(Card.class);

	/**
	 * Creates a card instance. Should only be used as a superclass and never actually used in a game.
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 */
	public Card(String name) {
		this.name= name;
		isFaceUp = true; //used to determine which face to show front/back
	}
	
	/**
	 * Set the card to face up position
	 */
	public void flipUp() {
			isFaceUp = true;
	}
	
	/**
	 * Set the card to face down position
	 */
	public void flipDown() {
		isFaceUp = false;
	}
	
	/**
	 * The name of the card 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Outputs string that either prints its name if face up or face down otherwise.
	 */
	public String toString() {
		String str ="";
		if(isFaceUp) {
			str+= name;
		}else {
			str = "face down";
		}
		return str;
	}
	
	/**
	 * Find is card is of the same type as another
	 * @param c Card to be tested against
	 * @return Whetheror not the cards equal
	 */
	public boolean equals(Card c) {
		return c.getName().equals(this.getName());
	}
	
	/**
	 * Whether a card is flipped
	 * @return True: Card is faced up
	 * 		   <p>False: Card is faced down</p>
	 */
	public boolean getFlipped() {
		return this.isFaceUp;
	}
	
	/**
	 * Set a card to face up
	 */
	public void setFaceUp() {
		this.isFaceUp = true;
		return;
	}
	
	/**
	 * Set a card to face down
	 */
	public void setFaceDown() {
		this.isFaceUp = false;
		return;
	}
	
	/**
	 * Set card ID to input value
	 * @param id ID of the card
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Gets the value of the property ID.
	 * @return 
	 */
	public int getID() {
		return this.id;
	}
}

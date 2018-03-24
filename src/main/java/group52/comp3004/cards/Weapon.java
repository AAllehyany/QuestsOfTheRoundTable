package group52.comp3004.cards;

public class Weapon extends AdventureCard{
	
	/**
	 * Adds weapon functionality to adventure cards
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 * @param bp Battle point value of the weapon
	 */
	public Weapon(String name, int bp) {
		super(name);
		this.bp = bp;
	}
}

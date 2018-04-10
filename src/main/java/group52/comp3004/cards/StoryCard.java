package group52.comp3004.cards;

public class StoryCard extends Card{
	
	/**
	 * Adds functional for story type cards. Should not be generally used. Use one of its subclasses when building a deck instead.
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 */
	public StoryCard(String name) {
		super(name);
		this.type = "story";
	}
}

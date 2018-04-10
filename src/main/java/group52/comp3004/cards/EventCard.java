package group52.comp3004.cards;

import group52.comp3004.game.GameState;

/**
 * Adds functionality for event type story cards
 * @author Sandy
 *
 */
public class EventCard extends StoryCard{

	private EventBehaviour eventBehaviour;
	
	/**
	 * Constructor for an event card
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 * @param eventBehaviour holds the functionality that an event card executes
	 */
	public EventCard(String name, EventBehaviour eventBehaviour) {
		super(name);
		this.eventBehaviour = eventBehaviour;
		this.type = "event";
	}
	
	/**
	 * execute the card's event behaviour
	 * @param gs The current status of the game
	 */
	public void run(GameState gs) {
		if(eventBehaviour != null) eventBehaviour.handle(gs);
	}
}

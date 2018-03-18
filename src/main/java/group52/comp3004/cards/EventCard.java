package group52.comp3004.cards;

import group52.comp3004.game.GameState;

public class EventCard extends StoryCard{

	private EventBehaviour eventBehaviour;
	
	public EventCard(String name, EventBehaviour eventBehaviour) {
		super(name);
		this.eventBehaviour = eventBehaviour;
	}
	
	public void run(GameState gs) {
		if(eventBehaviour != null) eventBehaviour.handle(gs);
	}
}

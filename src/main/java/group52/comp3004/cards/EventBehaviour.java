package group52.comp3004.cards;

import group52.comp3004.game.GameState;

@FunctionalInterface
/**
 * Interface for behaviours for event cards.
 * @author Sandy
 *
 */
public interface EventBehaviour {
		
	void handle(GameState gamestate);
}

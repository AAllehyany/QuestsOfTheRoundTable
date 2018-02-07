package group52.comp3004.cards;

import group52.comp3004.game.GameState;

@FunctionalInterface
public interface EventBehaviour {

	void handle(GameState gamestate);
}

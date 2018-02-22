package group52.comp3004.cards;

import group52.comp3004.game.GameState;

public class Recognition implements EventBehaviour{
	@Override
	public void handle(GameState gamestate) {
		gamestate.setBonusShields(2);
		
	}
}

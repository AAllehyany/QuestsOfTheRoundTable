package group52.comp3004.cards;

import group52.comp3004.game.GameState;

public class Plague implements EventBehaviour{
	@Override
	public void handle(GameState gamestate) {
//		gamestate.getPlayerByIndex(gamestate.getCurrentPlayer()).removeShield(2);
		System.out.println("		->Plague");
	}
}

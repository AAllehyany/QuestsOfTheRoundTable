package group52.comp3004.cards;

import group52.comp3004.game.GameState;

public class Camelot implements EventBehaviour{

	@Override
	public void handle(GameState gamestate) {
		for(int i =0;i< gamestate.numPlayers();i++) {
			gamestate.getPlayerByIndex(i).clearAlly();
		}
		
	}

}

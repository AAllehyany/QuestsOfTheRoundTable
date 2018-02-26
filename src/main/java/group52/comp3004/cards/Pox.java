package group52.comp3004.cards;

import group52.comp3004.game.GameState;

public class Pox implements EventBehaviour{
	@Override
	public void handle(GameState gamestate) {System.out.println("		->Pox");
		for(int i=0;i<gamestate.numPlayers();i++) {
			if(i != gamestate.getCurrentPlayer()) {
				gamestate.getPlayerByIndex(i).removeShield(1);
			}
		}
		
	}
}

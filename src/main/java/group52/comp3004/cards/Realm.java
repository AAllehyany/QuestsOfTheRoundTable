package group52.comp3004.cards;

import group52.comp3004.game.GameState;

public class Realm implements EventBehaviour{

	@Override
	public void handle(GameState gamestate) {System.out.println("		->Realm");
		for(int j=0;j<2;j++) {
			for(int i=0;i<gamestate.numPlayers();i++) {
				gamestate.dealToPlayer(i);
			}
		}

		
	}

}

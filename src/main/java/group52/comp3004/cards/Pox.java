package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.game.GameState;

public class Pox implements EventBehaviour{
	final static Logger logger = Logger.getLogger(Pox.class);
	@Override
	public void handle(GameState gamestate) {
		logger.info("		->Pox");
		for(int i=0;i<gamestate.numPlayers();i++) {
			if(i != gamestate.getCurrentPlayer()) {
				gamestate.getPlayerByIndex(i).removeShield(1);
			}
		}
		
	}
}

package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.game.GameState;

/**
 * Implements Court Called to Camelot event
 * @author Sandy
 *
 */
public class Camelot implements EventBehaviour{

	final static Logger logger = Logger.getLogger(Camelot.class);
	@Override
	public void handle(GameState gamestate) {
		logger.info("		->Camelot");
		for(int i =0;i< gamestate.numPlayers();i++) {
			gamestate.getPlayerByIndex(i).clearAlly();
		}
		
	}

}

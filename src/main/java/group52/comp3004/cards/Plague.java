package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.game.GameState;

/**
 * Implements Plague event
 * @author Sandy
 *
 */
public class Plague implements EventBehaviour{
	final static Logger logger = Logger.getLogger(Plague.class);
	@Override
	public void handle(GameState gamestate) {
		gamestate.getPlayerByIndex(gamestate.getCurrentPlayer()).removeShield(2);
		logger.info("		->Plague");
	}
}

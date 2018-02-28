package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.game.GameState;

public class Recognition implements EventBehaviour{
	final static Logger logger = Logger.getLogger(Recognition.class);
	@Override
	public void handle(GameState gamestate) {
		gamestate.setBonusShields(2);
		logger.info("		->Recognition");
	}
}

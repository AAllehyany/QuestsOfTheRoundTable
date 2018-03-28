package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.game.GameState;

/**
 * Implements Prosperity through out the Realm event
 * @author Sandy
 *
 */
public class Realm implements EventBehaviour{

	final static Logger logger = Logger.getLogger(Realm.class);
	@Override
	public void handle(GameState gamestate) {
		logger.info("		->Realm");
		for(int j=0;j<2;j++) {
			for(int i=0;i<gamestate.numPlayers();i++) {
				gamestate.dealToPlayer(i);
			}
		}

		
	}

}

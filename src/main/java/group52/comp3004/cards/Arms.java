package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;
import group52.comp3004.players.Rank;

public class Arms implements EventBehaviour{

	private Rank highestRank;
	
	final static Logger logger = Logger.getLogger(Arms.class);
	@Override
	public void handle(GameState gamestate) {
		
		for(int i=0;i<gamestate.numPlayers();i++) {
			if(gamestate.getPlayerByIndex(i).getRank()== Rank.ChampionKnight){
				highestRank = Rank.ChampionKnight;
			}
		}
		if(highestRank==null) {
			for(int i=0;i<gamestate.numPlayers();i++) {
				if(gamestate.getPlayerByIndex(i).getRank()== Rank.Knight){
					highestRank = Rank.Knight;
				}
			}
		}
		if(highestRank==null) {
			for(int i=0;i<gamestate.numPlayers();i++) {
				if(gamestate.getPlayerByIndex(i).getRank()== Rank.Squire){
					highestRank = Rank.Squire;
				}
			}

		}
		for(int i=0;i<gamestate.numPlayers();i++) {
			if(gamestate.getPlayerByIndex(i).getRank()== highestRank) {
				logger.info(gamestate.getPlayerByIndex(i).getId() +"discard two cards");
			}else {
				gamestate.nextPlayer();
			}

		}
		
		logger.info("		->Arms");
	}

}

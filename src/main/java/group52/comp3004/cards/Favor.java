package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.game.GameState;
import group52.comp3004.players.Rank;

/**
 * Implements Queen's Favour event
 * @author Sandy
 *
 */
public class Favor implements EventBehaviour{
	private Rank lowestRank;
	
	final static Logger logger = Logger.getLogger(Favor.class);
	@Override
	public void handle(GameState gamestate) {
		logger.info("		->Favor");
		for(int i=0;i<gamestate.numPlayers();i++) {
			if(gamestate.getPlayerByIndex(i).getRank()== Rank.Squire){
				lowestRank = Rank.Squire;
			}
		}
		if(lowestRank==null) {
			for(int i=0;i<gamestate.numPlayers();i++) {
				if(gamestate.getPlayerByIndex(i).getRank()== Rank.Knight){
					lowestRank = Rank.Knight;
				}
			}
		}
		if(lowestRank==null) {
			for(int i=0;i<gamestate.numPlayers();i++) {
				if(gamestate.getPlayerByIndex(i).getRank()== Rank.ChampionKnight){
					lowestRank = Rank.ChampionKnight;
				}
			}

		}
		for(int i=0;i<gamestate.numPlayers();i++) {
			if(gamestate.getPlayerByIndex(i).getRank()== lowestRank) {
				for(int j=0;j<2; j++) {
				gamestate.dealToPlayer(i);
				}
			}
		}
	}

}

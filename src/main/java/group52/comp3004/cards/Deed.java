package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.game.GameState;
import group52.comp3004.players.Rank;

public class Deed implements EventBehaviour{
	private Rank lowestRank;
	private int  lowestShield;
	
	final static Logger logger = Logger.getLogger(Deed.class);
	@Override
	public void handle(GameState gamestate) {
		logger.info("		->Deed");
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
				lowestShield = gamestate.getPlayerByIndex(0).getShields();
				for(int j=1;j<gamestate.numPlayers();j++) {
					if(gamestate.getPlayerByIndex(i).getShields()<lowestShield ) {
						lowestShield = gamestate.getPlayerByIndex(i).getShields();
					}
				}
				for(int j=1;j<gamestate.numPlayers();j++) {
					if(gamestate.getPlayerByIndex(i).getShields()==lowestShield ) {
						gamestate.getPlayerByIndex(i).addShields(3);;
					}
				}
			}
		}
		
	}

}

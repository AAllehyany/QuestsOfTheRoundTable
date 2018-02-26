package group52.comp3004.cards;

import java.util.List;

import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;
import group52.comp3004.players.Rank;

public class Favor implements EventBehaviour{
	private Rank lowestRank;
	@Override
	public void handle(GameState gamestate) {
		System.out.println("		->Favor");
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

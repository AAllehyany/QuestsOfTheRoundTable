package group52.comp3004.cards;

import java.util.List;

import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;
import group52.comp3004.players.Rank;

public class Deed implements EventBehaviour{
	private Rank lowestRank;
	private int  lowestShield;
	@Override
	public void handle(GameState gamestate) {
		System.out.println("		->Deed");
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
				gamestate.getPlayerByIndex(i).addShields(2);
			}
		}
		lowestShield = gamestate.getPlayerByIndex(0).getShields();
		for(int i=1;i<gamestate.numPlayers();i++) {
			if(gamestate.getPlayerByIndex(i).getShields()<lowestShield ) {
				lowestShield = gamestate.getPlayerByIndex(i).getShields();
			}
		}
		for(int i=1;i<gamestate.numPlayers();i++) {
			if(gamestate.getPlayerByIndex(i).getShields()==lowestShield ) {
				gamestate.getPlayerByIndex(i).addShields(2);;
			}
		}
	}

}

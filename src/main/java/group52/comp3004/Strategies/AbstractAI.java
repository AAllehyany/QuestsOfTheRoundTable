package group52.comp3004.Strategies;

import java.util.ArrayList;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;

public abstract class AbstractAI{
	public abstract boolean doIParticipateInTournament(GameState state);
	public abstract boolean doISponsorQuest(GameState state, Player p);
	public abstract boolean doIParticipateInQuest(GameState state, Player p);
	public abstract int nextBid(GameState state, Player p);
	public abstract ArrayList<AdventureCard> discardAfterWinningTest(GameState state, Player p);
	
	// Determine if another player can evolve before a quest or tournament starts
	public boolean otherEvolve(GameState state) {
		ArrayList<Player> players = new ArrayList<Player>(state.getAllPlayers());
		for(int i=0;i<players.size();i++) {
			Player p = players.get(i);
			StoryCard curCard = state.getRevealed();
			if(curCard instanceof QuestCard) {
				QuestCard q = (QuestCard) curCard;
				if(p.getShields()+q.getStages()>=p.getRequiredShields()) return true;
			}else {
				Tourneys t = (Tourneys) curCard;
				if(p.getShields()+t.getShields()>=p.getRequiredShields()) return true;
			}
		}
		return false;
	}
}

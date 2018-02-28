package group52.comp3004.Strategies;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public abstract class AbstractAI{
	public abstract boolean doIParticipateInTournament(GameState state);
	
	static final private Logger logger = Logger.getLogger(AbstractAI.class);
	public boolean doISponsorQuest(GameState state, Player p) {
		if(state.getCurrentQuest()==null) {
			logger.info("NO QUEST");
			return false;
		}
		if(otherEvolve(state, p)) return false;
		int test = 0;
		if(p.hasTest()) test = 1;
		if(p.numUniqueFoes(state) +test<state.getCurrentQuest().getNumStages()) return false;
		return true;
	}
	
	public abstract boolean doIParticipateInQuest(GameState state, Player p);
	public abstract int nextBid(GameState state, Player p);
	public abstract ArrayList<AdventureCard> discardAfterWinningTest(GameState state, Player p);
	public abstract ArrayList<AdventureCard> playTourney(GameState state, Player p);
	public abstract ArrayList<Stage> createQuest(GameState state, Player p);
	public abstract ArrayList<AdventureCard> playStage(GameState state, Player p);
	
	// Determine if any player can evolve before a quest or tournament starts
	public boolean anyEvolve(GameState state) {
		ArrayList<Player> players = new ArrayList<Player>(state.getAllPlayers());
		for(int i=0;i<players.size();i++) {
			Player otherP = players.get(i);
			StoryCard curCard = state.getRevealed();
			if(curCard instanceof QuestCard) {
				QuestCard q = (QuestCard) curCard;
				if(otherP.getShields()+q.getStages()>=otherP.getRequiredShields()) return true;
			}else {
				Tourneys t = (Tourneys) curCard;
				if(otherP.getShields()+t.getShields()+players.size()>=
						otherP.getRequiredShields()) return true;
			}
		}
		return false;
	}
	
	// Determine if another player can evolve before a quest or tournament starts
	public boolean otherEvolve(GameState state, Player p) {
		ArrayList<Player> players = new ArrayList<Player>(state.getAllPlayers());
		for(int i=0;i<players.size();i++) {
			if(!players.get(i).equals(p)) {
				Player otherP = players.get(i);
				StoryCard curCard = state.getRevealed();
				if(curCard instanceof QuestCard) {
					QuestCard q = (QuestCard) curCard;
					if(otherP.getShields()+q.getStages()>=otherP.getRequiredShields()) return true;
				}else {
					Tourneys t = (Tourneys) curCard;
					if(otherP.getShields()+t.getShields()+players.size()>=
							otherP.getRequiredShields()) return true;
				}
			}
		}
		return false;
	}
	
	public Tests getTest(Player p) {
		for (int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Tests) return (Tests) p.getHand().get(i);
		}
		return null;
	}
	
	public Foe getStrongestFoe(GameState state, Player p) {
		p.sortHand(state);
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Foe) return (Foe) p.getHand().remove(i);
		}
		return null;
	}
	
	public boolean containsAmour(ArrayList<AdventureCard> cards) {
		for(int i=0;i<cards.size();i++) {
			if(cards.get(i) instanceof Amour) return true;
		}
		return false;
	}
}

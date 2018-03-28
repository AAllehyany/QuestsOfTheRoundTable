package group52.comp3004.Strategies;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public abstract class AbstractAI{
	public abstract boolean doIParticipateInTournament(GameState state, Player p);
	
	static final private Logger logger = Logger.getLogger(AbstractAI.class);
	public boolean doISponsorQuest(GameState state, Player p) {
		if(state.getCurrentQuest()==null) {
			logger.info("NO QUEST");
			return false;
		}
		if(otherEvolve(state, p)) {
			logger.info("Player: " + p.getId() + " will not sponsor the quest, because another player might rank up");
			return false;
		}
		int test = 0;
		if(this.hasTest(p)) test = 1;
		if(this.numUniqueFoes(state, p) +test<state.getCurrentQuest().getNumStages()) {
			logger.info("Player: " + p.getId() + " will not sponsor the quest, because it does not have enough cards");
			return false;
		}
		logger.info("Player: " + p.getId() + " will sponsor the quest");
		return true;
	}
	
	public abstract boolean doIParticipateInQuest(GameState state, Player p);
	public abstract int nextBid(GameState state, Player p);
	public abstract ArrayList<AdventureCard> discardAfterWinningTest(GameState state, Player p);
	public abstract ArrayList<AdventureCard> playTourney(GameState state, Player p);
	public abstract ArrayList<Stage> createQuest(GameState state, Player p);
	public abstract ArrayList<AdventureCard> playStage(GameState state, Player p);

	// Get any duplicates from a players hand
	protected ArrayList<AdventureCard> getDuplicates(Player p){
		ArrayList<AdventureCard> dupes = new ArrayList<AdventureCard>();
		HashSet<AdventureCard> cards = new HashSet<AdventureCard>();
		for(int i=0;i<p.getHand().size();i++) {
			if(!cards.add(p.getHand().get(i))) dupes.add(p.getHand().get(i));
		}
		return dupes;
	}
	
	// Determine if a player has a test in their hand
	protected boolean hasTest(Player p) {
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Tests) return true;
		}
		return false;
	}
	
	// count the number of tests in a player's hand
	protected int countTests(Player p) {
		return p.getHand().stream().filter(c -> c instanceof Tests).collect(Collectors.toList()).size();
	}
	
	// count the number of foes in a player's hand
	protected int countFoes(Player p) {
		int count = 0;
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Foe) count++;
		}
		return count;
	}
	
	// count the number of foes less than a certain battle power in a player's hand
	protected int countFoes(Player p, int bp) {
		int count =0;
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Foe && p.getHand().get(i).getBp()<bp) count++;
		}
		return count;
	}
	
	// get the foes less than a certain battle power in a player's hand
	protected ArrayList<AdventureCard> getFoes(Player p, int bp){
		ArrayList<AdventureCard> foes = new ArrayList<AdventureCard>();
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Foe && p.getHand().get(i).getBp()<bp) {
				foes.add(p.getHand().get(i));
			}
		}
		return foes;
	}
	
	// get the foes of unique battle powers in a player's hand depending on the GameState
	protected ArrayList<AdventureCard> getUniqueFoes(GameState state, Player p){
		ArrayList<AdventureCard> uFoes = new ArrayList<AdventureCard>();
		HashSet<Integer> bps = new HashSet<Integer>();
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Foe) {
				Foe f;
				f = (Foe) p.getHand().get(i);
				if(bps.add(f.getBp(state))) uFoes.add(p.getHand().get(i));
			}
		}
		return uFoes;
	}
	
	// count the foes of unique battle powers in a player's hand depending on the GameState
	protected int numUniqueFoes(GameState state, Player p) {
		int numUFoes = 0;
		HashSet<Integer> bps = new HashSet<Integer>();
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Foe) {
				Foe f = (Foe) p.getHand().get(i);
				if(bps.add(f.getBp(state))) numUFoes++;
			}
		}
		return numUFoes;
	}
	
	// Determine if a player has an amour in their hand
	protected boolean hasAmour(Player p) {
		for(int i=0;i<p.getTemp().size();i++) {
			if (p.getTemp().get(i) instanceof Amour) return true;
		}
		return false;
	}
	
	protected boolean hasAmourInHand(Player p) {
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Amour) return true;
		}
		return false;
	}
	
	protected AdventureCard getAmourInHand(Player p) {
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Amour) {
				return p.discard(p.getHand().get(i));
			}
		}
		return null;
	}
	
	protected boolean hasAllyInHand(Player p) {
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Ally) return true;
		}
		return false;
	}
	
	protected AdventureCard getStrongestAllyInHand(GameState state, Player p) {
		p.sortHand(state);
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Ally) {
				return p.discard(p.getHand().get(i));
			}
		}
		return null;
	}

	
	// Determine if any player can evolve before a quest or tournament starts
	protected boolean anyEvolve(GameState state) {
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
	protected boolean otherEvolve(GameState state, Player p) {
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
	
	protected Tests getTest(Player p) {
		for (int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Tests) return (Tests) p.getHand().get(i);
		}
		return null;
	}
	
	protected Foe getStrongestFoe(GameState state, Player p) {
		p.sortHand(state);
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Foe) return (Foe) p.discard(p.getHand().get(i));
		}
		return null;
	}
	
	protected boolean containsAmour(ArrayList<AdventureCard> cards) {
		for(int i=0;i<cards.size();i++) {
			if(cards.get(i) instanceof Amour) return true;
		}
		return false;
	}
	
	protected Foe getWeakestFoe(GameState state, Player p) {
		p.sortHand(state);
		for(int i=p.getHand().size()-1;i>=0;i--) {
			if(p.getHand().get(i) instanceof Foe) return (Foe) p.discard(p.getHand().get(i));
		}
		return null;
	}
	
	protected Foe makeFoe(GameState state, Player p, int bp) {
		Foe f = getStrongestFoe(state, p);
		ArrayDeque<AdventureCard> weps = new ArrayDeque<AdventureCard>();
		p.sortHand(state);
		for(int i=0;i<p.getHandSize();i++) {
			if(p.getHand().get(i) instanceof Weapon && !weps.contains(p.getHand().get(i)))
				weps.addLast(p.getHand().get(i));
		}
		int curbp = f.getBp(state);
		while(curbp<bp && !weps.isEmpty()){
			Weapon w = (Weapon) weps.removeFirst();
			f.addWeapon(w);
			p.discard(w);
			curbp += w.getBp();
		}
		return f;
	}
	
	protected int countWAA(GameState state, Player p, int stages) {
		int WAA = 0;
		if(this.hasAmourInHand(p) && !this.hasAmour(p)) WAA++;
		HashMap<AdventureCard, Integer> weps = new HashMap<AdventureCard, Integer>();
		for(int i=0;i<p.getHandSize();i++) {
			if(p.getHand().get(i) instanceof Weapon) {
				if(!weps.containsKey(p.getHand().get(i))) weps.put(p.getHand().get(i), 1);
				else weps.replace(p.getHand().get(i), weps.get(p.getHand().get(i))+1);
			}else if(p.getHand().get(i) instanceof Ally) WAA++;
		}
		ArrayList<Integer> wepNums = new ArrayList<Integer>(weps.values());
		for(int i=0;i<wepNums.size();i++) {
			WAA += Math.min(stages, wepNums.get(i));
		}
		return WAA;
	}
	
	protected AdventureCard getWeakestWeapon(GameState state, Player p, ArrayList<AdventureCard> weapons) {
		p.sortHand(state);
		for(int i=p.getHand().size()-1;i>=0;i--) {
			if(p.getHand().get(i) instanceof Weapon && !weapons.contains(p.getHand().get(i))) {
				return p.discard(p.getHand().get(i));
			}
		}
		return null;
	}
}

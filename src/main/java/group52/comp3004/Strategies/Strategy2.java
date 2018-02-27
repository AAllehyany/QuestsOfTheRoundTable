package group52.comp3004.Strategies;

import java.util.ArrayList;
import java.util.HashMap;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.CardComparator;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public class Strategy2 extends AbstractAI{
	private boolean havebid = false;
	
	public Strategy2() {}
	
	public boolean doIParticipateInTournament(GameState state) {
		return true;
	}
	
	public boolean doISponsorQuest(GameState state, Player p) {
		if(otherEvolve(state)) return false;
		int test = 0;
		if(p.hasTest()) test = 1;
		if(p.numUniqueFoes(state) +test<state.getCurrentQuest().getNumStages()) return false;
		return true;
	}
	
	public boolean doIParticipateInQuest(GameState state, Player p) {
		if(p.countFoes(25)<2) return false;
		p.sortHand(state);
		int stages = state.getCurrentQuest().getNumStages();
		int AAW = 0;
		if (p.hasAmourInHand()) AAW++;
		HashMap<AdventureCard, Integer> weak = new HashMap<AdventureCard, Integer>();
		for(int i=0;i<p.getHand().size();i++) {
			AdventureCard c = p.getHand().get(i);
			if(c.getBp()>=10) AAW++;
			else {
				if(weak.containsKey(c)) weak.replace(c, weak.get(c)+1);
				else weak.put(c, 1);
			}
		}
		ArrayList<AdventureCard> weakCards = new ArrayList<AdventureCard>(weak.keySet());
		int rem = stages-AAW;
		if(rem>0) {
			int weaksum = weakCards.stream().mapToInt(c->Math.min(rem, weak.get(c))*c.getBp()).sum();
			if(weaksum<rem*(rem+1)/2*10) return false;
		}
		this.resetBid();
		return true;
	}
	
	public int nextBid(GameState state, Player p) {
		if(havebid) return p.countFoes(25) + p.getDuplicates().size();
		else {
			havebid = true;
			return p.countFoes(25);
		}
	}
	
	public ArrayList<AdventureCard> discardAfterWinningTest(GameState state, Player p){
		ArrayList<AdventureCard> discards = new ArrayList<AdventureCard>();
		discards.addAll(p.getFoes(25));
		if(havebid) {
			havebid = false;
			discards.addAll(p.getDuplicates());
			return discards;
		}
		else {
			havebid = false;
			return discards;
		}
	}
	
	public void resetBid() {
		this.havebid = false;
	}
	
	public ArrayList<Stage> createQuest(GameState state, Player p){
		ArrayList<Stage> stages = new ArrayList<Stage>();
		
		p.sortHand(state);
		GameQuest q = state.getCurrentQuest();
		for(int i=0;i<q.getNumStages();i++) {
			if(i<q.getNumStages()-2) {
				Stage stage = new Stage(getWeakestFoe(state, p));
				stages.add(stage);
			}else if(i==q.getNumStages()-2) {
				if(p.hasTest()) {
						Stage stage = new Stage(getTest(p));
						stages.add(stage);
				}else {
					Stage stage = new Stage(getWeakestFoe(state, p));
					stages.add(stage);
				}
			}else {
				Foe f = getStrongestFoe(state, p);
				int j=0;
				while(f.getBp(state)<40 && j<p.getHand().size()) {
					if(p.getHand().get(j) instanceof Weapon) {
						if(f.addWeapon((Weapon) p.getHand().get(j))) {
							p.getHand().remove(j);
							j--;
						}
					}
					j++;
				}
				Stage stage = new Stage(f);
				stages.add(stage);
			}
		}
		
		return stages;
	}
	
	private Foe getWeakestFoe(GameState state, Player p) {
		p.sortHand(state);
		for(int i=p.getHand().size()-1;i>=0;i--) {
			if(p.getHand().get(i) instanceof Foe) {
				return (Foe) p.getHand().remove(i);
			}
		}
		return null;
	}
	
	public ArrayList<AdventureCard> playStage(GameState state, Player p){
		ArrayList<AdventureCard> stageCards = new ArrayList<AdventureCard>();
		if(state.getCurrentQuest().getCurrentStage() == 1) return stageCards;
		else if(state.getCurrentQuest().currentStage().isTestStage()) {
			return discardAfterWinningTest(state, p);
		}else {
			int stageBP = 0;
			int pos = 0;
			p.sortHand(state);
			if(!p.hasAmour()) {
				for(int i=0;i<p.getHand().size();i++) {
					if(p.getHand().get(i) instanceof Amour) {
						stageCards.add(p.getHand().get(i));
						stageBP += p.getHand().get(i).getBp();
						return stageCards;
					}
				}
			}
			while(stageBP<10 && pos<p.getHand().size()) {
				if(p.getHand().get(pos) instanceof Ally) {
					stageCards.add(p.getHand().get(pos));
					stageBP += p.getHand().get(pos).getBp();
				}
			}
			pos = 0;
			while(stageBP<10 && pos<p.getHand().size()) {
				if(p.getHand().get(pos) instanceof Weapon) {
					stageCards.add(p.getHand().get(pos));
					stageBP += p.getHand().get(pos).getBp();
				}
			}
			return stageCards;
		}
	}
	
	public ArrayList<AdventureCard> playTourney(GameState state, Player p) {
		ArrayList<AdventureCard> tourneyCards = new ArrayList<AdventureCard>();
		int totalBP = 0;
		p.getHand().sort(new CardComparator(state));
		int i=0;
		while(totalBP<50 && i<p.getHand().size()) {
			if(!(p.getHand().get(i) instanceof Tests || p.getHand().get(i) instanceof Foe)) {
				if(!(tourneyCards.contains(p.getHand().get(i)))) {
					if(p.getHand().get(i) instanceof Ally) totalBP += p.getHand().get(i).getBp(state);
					else totalBP += p.getHand().get(i).getBp();
					tourneyCards.add(p.getHand().get(i));
				}
			}
			i++;
		}
		return tourneyCards;
	}
}

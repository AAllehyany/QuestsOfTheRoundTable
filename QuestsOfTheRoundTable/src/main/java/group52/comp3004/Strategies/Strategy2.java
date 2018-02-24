package group52.comp3004.Strategies;

import java.util.ArrayList;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Test;
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
		int bp = p.getBattlePoints();
		if(bp <((stages*(stages+1)/2-1))*10) return false;
		this.resetBid();
		return true;
	}
	
	public int nextBid(GameState state, Player p) {
		if(havebid) {
			havebid = true;
			return p.countFoes() + p.getDuplicates().size();
		}
		else return p.countFoes(25);
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
			int pos = p.getHand().size()-1;
			if(i<q.getNumStages()-3) {
				while(pos>=0) {
					if(p.getHand().get(pos) instanceof Foe) {
						Stage stage = new Stage((Foe) p.getHand().get(pos));
						stages.add(stage);
						break;
					}
					pos--;
				}
			}else if(i==q.getNumStages()-2) {
				if(p.hasTest()) {
					for(int j=p.getHand().size()-1;j>=0;j--) {
						if(p.getHand().get(j) instanceof Test) {
							Stage stage = new Stage((Test) p.getHand().get(j));
							stages.add(stage);
							break;
						}
					}
				}else {
					while(pos>=0) {
						if(p.getHand().get(pos) instanceof Foe) {
							Stage stage = new Stage((Foe) p.getHand().get(pos));
							stages.add(stage);
							break;
						}
						pos--;
					}
				}
			}else {
				for(int j=0;j<p.getHand().size();j++) {
					if(p.getHand().get(i) instanceof Foe) {
						int stageBP = p.getHand().get(j).getBp();
						Foe f = (Foe) p.getHand().get(j);
						int k = p.getHand().size()-1;
						while(stageBP<40 && k>=0) {
							if(p.getHand().get(k) instanceof Weapon) {
								f.addWeapon((Weapon) p.getHand().get(k));
								stageBP += p.getHand().get(k).getBp();
							}
							k--;
						}
						Stage stage = new Stage(f);
						stages.add(stage);
						break;
					}
				}
			}
		}
		
		return stages;
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
		p.sortHand(state);
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Ally || p.getHand().get(i) instanceof Amour ||
					p.getHand().get(i) instanceof Weapon) {
				tourneyCards.add(p.getHand().get(i));
				totalBP += p.getHand().get(i).getBp();
				if(totalBP > 50) break;
			}
		}
		return tourneyCards;
	}
}

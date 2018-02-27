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
	private int prevbp = 0;
	
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
		int AA = 0;
		if (p.hasAmourInHand()) AA++;
		HashMap<AdventureCard, Integer> weps = new HashMap<AdventureCard, Integer>();
		ArrayList<AdventureCard> weakAllies = new ArrayList<AdventureCard>();
		for(int i=0;i<p.getHand().size();i++) {
			AdventureCard c = p.getHand().get(i);
			if(c.getBp()>=10 && c instanceof Ally) AA++;
			else if(c instanceof Ally) weakAllies.add(c);
			else if(c instanceof Weapon){
				if(weps.containsKey(c)) weps.replace(c, weps.get(c)+1);
				else weps.put(c, 1);
			}
		}
		int rem = state.getCurrentQuest().getNumStages()-AA;
		ArrayList<AdventureCard> ws = new ArrayList<AdventureCard>(weps.keySet());
		ws.sort(new CardComparator());
		int constbp = 0;
		int runningbp = 0;
		if(!(weakAllies.isEmpty())) constbp = weakAllies.get(0).getBp();
		int curbp = constbp;
		int pos = ws.size()-1;
		for(int i=0;i<rem;i++) {
			while(curbp<runningbp+10 && pos>=0) {
				System.out.println(pos);
				curbp += ws.get(pos).getBp();
				weps.replace(ws.get(pos), weps.get(ws.get(pos))-1);
				if(weps.get(ws.get(pos))==0) {
					weps.remove(ws.remove(pos));
//					pos++;
				}
				pos--;
			}
			if(curbp<runningbp+10) return false;
			runningbp = curbp;
			curbp = constbp;
			pos = ws.size()-1;
		}
		this.resetBid();
		this.prevbp = 0;
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
		int stagebp = p.getField().stream().mapToInt(c->c.getBp(state)).sum();
		if(p.hasAmour()) stagebp += p.getTemp().get(0).getBp();
		if(state.getCurrentQuest().getCurrentStage()>0){
			p.sortHand(state);
			if(!p.hasAmour() && p.hasAmourInHand()) {
				stageCards.add(p.getAmourInHand());
				stagebp+=10;
			}else {
				if(p.hasAllyInHand()) {
					stageCards.add(p.getStrongestAllyInHand(state));
					stagebp += stageCards.get(0).getBp();
				}
				if(stagebp<prevbp+10) {
					HashMap<AdventureCard, Integer> wepNum = new HashMap<AdventureCard, Integer>();
					for(int i=0;i<p.getHand().size();i++) {
						if(p.getHand().get(i) instanceof Weapon) {
							if(wepNum.containsKey(p.getHand().get(i))) {
								wepNum.replace(p.getHand().get(i), wepNum.get(p.getHand().get(i))+1);
							}else wepNum.put(p.getHand().get(i), 1);
						}
					}
					ArrayList<AdventureCard> weps = new ArrayList<AdventureCard>(wepNum.keySet());
					int pos = weps.size()-1;
					while(stagebp<prevbp+10 && pos>=0) {
						stageCards.add(weps.get(pos));
						stagebp += weps.get(pos).getBp();
						p.getHand().remove(weps.get(pos));
						pos--;
					}
				}
			}
		}
		prevbp = stagebp;
		return stageCards;
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

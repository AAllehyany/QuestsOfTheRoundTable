package group52.comp3004.Strategies;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
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
	private final static Logger logger = Logger.getLogger(Strategy2.class);
	
	public Strategy2() {}
	
	public boolean doIParticipateInTournament(GameState state, Player p) {
		logger.info("Player " + p.getId() + " is participating in the tournament");
		return true;
	}
	
	public boolean doIParticipateInQuest(GameState state, Player p) {
		if(this.countFoes(p, 25)<2) {
			logger.info("Player " + p.getId() + " does not have enough foes to discard for tests, so wil not participate in the quest");
			return false;
		}
		p.sortHand(state);
		int AA = 0;
		if (this.hasAmourInHand(p)) AA++;
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
				curbp += ws.get(pos).getBp();
				weps.replace(ws.get(pos), weps.get(ws.get(pos))-1);
				if(weps.get(ws.get(pos))==0) {
					weps.remove(ws.remove(pos));
				}
				pos--;
			}
			if(curbp<runningbp+10) {
				logger.info("Player " + p.getId() + " does not have enough cards to play, so will not participate in the quest");
				return false;
			}
			runningbp = curbp;
			curbp = constbp;
			pos = ws.size()-1;
		}
		this.resetBid();
		this.prevbp = p.getBattlePoints(state);
		logger.info("Player " + p.getId() + " will partcipate in the quest");
		return true;
	}
	
	public int nextBid(GameState state, Player p) {
		if(havebid) {
			logger.info("Player " + p.getId() + " bids " + (this.countFoes(p, 25)+this.getDuplicates(p).size()) + " cards as its second bid");
			return this.countFoes(p, 25) + this.getDuplicates(p).size();
		}
		else {
			logger.info("Player " + p.getId() + " bids " + this.countFoes(p, 25) + " cards for its first bid");
			havebid = true;
			return this.countFoes(p, 25);
		}
	}
	
	public ArrayList<AdventureCard> discardAfterWinningTest(GameState state, Player p){
		ArrayList<AdventureCard> discards = new ArrayList<AdventureCard>();
		discards.addAll(this.getFoes(p, 25));
		if(havebid) {
			discards.addAll(this.getDuplicates(p));
		}
		havebid = false;
		for(int i=0;i<discards.size();i++) {
			p.discard(discards.get(i));
		}
		return discards;
	}
	
	/**
	 * Reset the AI's have bid value to false so they can regain their original behavior for a new quest
	 */
	private void resetBid() {
		this.havebid = false;
	}
	
	public ArrayList<Stage> createQuest(GameState state, Player p){
		ArrayList<Stage> stages = new ArrayList<Stage>();
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
				Foe f = makeFoe(state, p, 40);
				Stage stage = new Stage(f);
				stages.add(stage);
			}
		}
		for(int i=0;i<stages.size();i++) {
			if(stages.get(i).isTestStage())
				logger.info("Stage " + i+1 + ": " + stages.get(i).getTest().getName());
			else {
				logger.info("Stage " + i+1 + ": " + stages.get(i).getFoe().getName());
				if(!stages.get(i).getFoe().getWeapons().isEmpty())
					logger.info("with weapons: " + stages.get(i).getFoe().getWeapons().stream().map(w -> w.getName() + " "));
			}
		}
		return stages;
	}
	
	public ArrayList<AdventureCard> playStage(GameState state, Player p){
		if(state.getCurrentQuest().getCurrentStage()==0) prevbp = p.getBattlePoints(state);
		ArrayList<AdventureCard> stageCards = new ArrayList<AdventureCard>();
		int stagebp = p.getBattlePoints(state);
		p.sortHand(state);
		if(!this.hasAmour(p) && this.hasAmourInHand(p) && !containsAmour(stageCards)) {
			stageCards.add(this.getAmourInHand(p));
			stagebp+=10;
		}else {
			if(this.hasAllyInHand(p)) {
				AdventureCard a = this.getStrongestAllyInHand(state, p);
				stageCards.add(a);
				stagebp += a.getBp();
			}
			while(stagebp<prevbp+10){
				ArrayList<AdventureCard> weapons = new ArrayList<AdventureCard>();
				for(int i=0;i<p.getHand().size();i++) {
					if(!(weapons.contains(p.getHand().get(i)) || stageCards.contains(p.getHand().get(i))) &&
							p.getHand().get(i) instanceof Weapon)
							weapons.add(p.getHand().get(i));
				}
				if(weapons.isEmpty()) break;
				else {
					Weapon wep = (Weapon) this.getWeakestWeapon(state, p, stageCards);
					stagebp += wep.getBp(state);
					stageCards.add(wep);
				}
			}
		}
		prevbp = stagebp;
		return stageCards;
	}
	
	public ArrayList<AdventureCard> playTourney(GameState state, Player p) {
		ArrayList<AdventureCard> tourneyCards = new ArrayList<AdventureCard>();
		int totalBP = p.getBattlePoints(state);
		p.getHand().sort(new CardComparator(state));
		int i=0;
		while(totalBP<50 && i<p.getHand().size()) {
			if(!(p.getHand().get(i) instanceof Tests || p.getHand().get(i) instanceof Foe)) {
				if(!(tourneyCards.contains(p.getHand().get(i)))) {
					totalBP += p.getHand().get(i).getBp(state);
					tourneyCards.add(p.getHand().get(i));
				}
			}
			i++;
		}
		for(i=0;i<tourneyCards.size();i++) p.discard(tourneyCards.get(i));
		return tourneyCards;
	}
}

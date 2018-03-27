package group52.comp3004.Strategies;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.CardComparator;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameState;
import group52.comp3004.game.GameTourney;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

/**
 * ?Needs an explaination of stategy goals? ?Does implemented functions from AbstractAI change behaviour much for different strats? 
 * @author Sandy
 *
 */
public class Strategy1 extends AbstractAI{
	
	static final private Logger logger = Logger.getLogger(Strategy1.class);
	public boolean doIParticipateInTournament(GameState state, Player p) {
		if(anyEvolve(state)) return true;
		return false;
	}
	
	public boolean doIParticipateInQuest(GameState state, Player p) {
		if(p.countFoes(20)<2) return false;
		int sum = countWAA(state, p, state.getCurrentQuest().getNumStages());
		if(sum<state.getCurrentQuest().getNumStages()*2) return false;
		return true;
	}
	
	public int nextBid(GameState state, Player p) {
		return p.countFoes(20);
	}
	
	public ArrayList<AdventureCard> discardAfterWinningTest(GameState state, Player p){
		ArrayList<AdventureCard> dcards = p.getFoes(20);
		for(int i=0;i<dcards.size();i++) p.getHand().remove(dcards.get(i));
		return dcards;
	}

	
	public boolean tourneyEvolve(GameState state) {
		GameTourney t = state.getCurrentTourney();
		ArrayList<Player> players = new ArrayList<Player>(t.getPlayers());
		for(int i=0;i<players.size();i++) {
			if(players.get(i).getShields()+t.getTourney().getShields()+players.size()>=
					players.get(i).getRequiredShields()) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<AdventureCard> playTourney(GameState state, Player p){
		ArrayList<AdventureCard> cards = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> dupes = p.getDuplicates();
		if(tourneyEvolve(state)) {
			for(int i=0;i<p.getHand().size();i++) {
				if(!(p.getHand().get(i) instanceof Tests || p.getHand().get(i) instanceof Foe) &&
						!cards.contains(p.getHand().get(i))) {
					cards.add(p.getHand().remove(i));
					i--;
				}
			}
		}else {
			for(int i=0;i<dupes.size();i++) {
				if(dupes.get(i) instanceof Weapon && !cards.contains(dupes.get(i))) {
					cards.add(dupes.get(i));
					p.getHand().remove(dupes.get(i));
				}
			}
		}
		return cards;
	}
	
	public ArrayList<Stage> createQuest(GameState state, Player p){
		p.sortHand(state);
		ArrayList<Stage> quest = new ArrayList<Stage>();
		ArrayList<AdventureCard> wepdupes = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> dupes = p.getDuplicates();
		for(int i=0;i<dupes.size();i++) {
			if(dupes.get(i) instanceof Weapon) wepdupes.add(dupes.get(i));
		}
		wepdupes.sort(new CardComparator());
		for(int i=0;i<state.getCurrentQuest().getNumStages();i++) {
			if(i==0) {
				Foe f = makeFoe(state, p, 50);
				Stage stage = new Stage(f);
				quest.add(0, stage);
			}else if(i==1) {
				if(p.hasTest()) {
					Stage stage = new Stage(getTest(p));
					quest.add(0, stage);
				}else {
					Foe f = this.getStrongestFoe(state, p);
					giveWeapon(state, p, f, wepdupes, quest.get(0).getTotalPower(state));
					Stage stage = new Stage(f);
					quest.add(0, stage);
				}
			}else {
				Foe f = this.getStrongestFoe(state, p);
				if(quest.get(0).isTestStage()) {
					giveWeapon(state, p, f, wepdupes, quest.get(1).getTotalPower(state));
				}else {
					giveWeapon(state, p, f, wepdupes, quest.get(0).getTotalPower(state));
				}
				Stage stage = new Stage(f);
				quest.add(0, stage);
			}
		}
		ArrayList<Stage> stages = new ArrayList<Stage>();
		for(int i=quest.size()-1;i>=0;i--) {
			stages.add(quest.get(i));
		}
		return quest;
	}
	
	private void giveWeapon(GameState state, Player p, Foe f, ArrayList<AdventureCard> weps, int last) {
		for(int i=0;i<weps.size();i++) {
			if(f.getBp(state)+weps.get(i).getBp()<last) {
				f.addWeapon((Weapon) weps.get(i));
				p.getHand().remove(weps.remove(i));
				return;
			}
		}
	}
	
	public ArrayList<AdventureCard> playStage(GameState state, Player p){
		p.sortHand(state);
		ArrayList<AdventureCard> stageCards = new ArrayList<AdventureCard>();
		if(state.getCurrentQuest().getCurrentStage()+1==state.getCurrentQuest().getNumStages()) {
			for(int i=0;i<p.getHand().size();i++) {
				if(!(p.getHand().get(i) instanceof Tests || p.getHand().get(i) instanceof Foe) &&
						!stageCards.contains(p.getHand().get(i))) {
					if(p.getHand().get(i) instanceof Amour) {
						if(!containsAmour(stageCards) && !p.hasAmour()) stageCards.add(p.getHand().get(i));
					}else {
						stageCards.add(p.getHand().get(i));
					}
				}
			}
			for(int i=0;i<stageCards.size();i++) p.getHand().remove(stageCards.get(i));
		}else {
			while(stageCards.size()<2) {
				if(!p.hasAmour() && p.hasAmourInHand() && 
						!containsAmour(stageCards)) stageCards.add(p.getAmourInHand());
				else if(p.hasAllyInHand()) stageCards.add(p.getStrongestAllyInHand(state));
				else stageCards.add(getWeakestWeapon(state,p,stageCards));
			}
		}
		return stageCards;
	}
	
	private AdventureCard getWeakestWeapon(GameState state, Player p, ArrayList<AdventureCard> weapons) {
		p.sortHand(state);
		for(int i=p.getHand().size()-1;i>=0;i--) {
			if(p.getHand().get(i) instanceof Weapon && !weapons.contains(p.getHand().get(i))) {
				return p.getHand().remove(i);
			}
		}
		return null;
	}
}

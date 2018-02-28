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
import group52.comp3004.game.GameState;
import group52.comp3004.game.GameTourney;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public class Strategy1 extends AbstractAI{
	public boolean doIParticipateInTournament(GameState state) {
		if(anyEvolve(state)) return true;
		return false;
	}
	
	public boolean doIParticipateInQuest(GameState state, Player p) {
		p.sortHand(state);
		if(p.countFoes(20)<2) return false;
		int sum = 0;
		HashMap<Weapon, Integer> wepNum = new HashMap<Weapon, Integer>();
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Ally) {
				sum++;
			}
			if(p.getHand().get(i) instanceof Weapon) {
				Weapon w = (Weapon) p.getHand().get(i);
				if(wepNum.containsKey(w)) wepNum.replace(w, wepNum.get(w)+1);
				else wepNum.put((Weapon) p.getHand().get(i), 1);
			}
		}
		if(p.hasAmourInHand()) sum++;
		int AA = sum;
		ArrayList<Integer> weps = new ArrayList<Integer>(wepNum.values());
		for(int i=0;i<weps.size();i++) {
			sum += Math.min(weps.get(i), state.getCurrentQuest().getNumStages()-AA/2);
		}
		if(sum<state.getCurrentQuest().getNumStages()*2) return false;
		return true;
	}
	
	public int nextBid(GameState state, Player p) {
		return p.countFoes(20);
	}
	
	public ArrayList<AdventureCard> discardAfterWinningTest(GameState state, Player p){
		return p.getFoes(20);
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
		ArrayList<Weapon> wepdupes = new ArrayList<Weapon>();
		ArrayList<AdventureCard> dupes = p.getDuplicates();
		for(int i=0;i<dupes.size();i++) {
			if(dupes.get(i) instanceof Weapon) wepdupes.add((Weapon) dupes.get(i));
		}
		wepdupes.sort(new CardComparator());
		for(int i=0;i<state.getCurrentQuest().getNumStages();i++) {
			if(i==0) {
				Foe f = this.getStrongestFoe(state, p);
				int j = 0;
				while(f.getBp(state) < 50 && j<p.getHand().size()) {
					if(p.getHand().get(i) instanceof Weapon) {
						f.addWeapon((Weapon) p.getHand().remove(j));
					}
					j++;
				}
				Stage stage = new Stage(f);
				quest.add(stage);
			}else if(i==1) {
				if(p.hasTest()) {
					Stage stage = new Stage(getTest(p));
					quest.add(0, stage);
				}else {
					Foe f = this.getStrongestFoe(state, p);
					Weapon wep = null;
					if(!(wepdupes.isEmpty())) {
						wep = wepdupes.remove(0);
						f.addWeapon(wep);
					}
					while(quest.get(0).getFoe().getBp(state)<=f.getBp(state)) {
						f.clearWeapons();
						wep = wepdupes.remove(0);
						f.addWeapon(wep);
					}
					if(wep!=null) p.getHand().remove(wep);
					Stage stage = new Stage(f);
					quest.add(0, stage);
				}
			}else {
				Foe f = this.getStrongestFoe(state, p);
				Weapon wep = null;
				if(!(wepdupes.isEmpty())) {
					wep = wepdupes.remove(0);
					f.addWeapon(wep);
				}
				if(quest.get(0).isTestStage()) {
					while(quest.get(1).getFoe().getBp(state)<=f.getBp(state)) {
						f.clearWeapons();
						wep = wepdupes.remove(0);
						f.addWeapon(wep);
					}
				}else {
					while(quest.get(0).getFoe().getBp(state)<=f.getBp(state)) {
						f.clearWeapons();
						wep = wepdupes.remove(0);
						f.addWeapon(wep);
					}
				}
				if(wep!=null) p.getHand().remove(wep);
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

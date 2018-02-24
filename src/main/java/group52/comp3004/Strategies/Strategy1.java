package group52.comp3004.Strategies;

import java.util.ArrayList;
import java.util.HashMap;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.CardComparator;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Test;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public class Strategy1 extends AbstractAI{
	public boolean doIParticipateInTournament(GameState state) {
		if(otherEvolve(state)) return true;
		return false;
	}
	
	public boolean doISponsorQuest(GameState state, Player p) {
		if(otherEvolve(state)) return false;
		int test = 0;
		if(p.hasTest()) test = 1;
		if(p.numUniqueFoes(state)+test<state.getCurrentQuest().getNumStages()) return false;
		return true;
	}
	
	public boolean doIParticipateInQuest(GameState state, Player p) {
		p.sortHand(state);
		if(p.countFoes(20)<2) return false;
		int AA = 0;
		HashMap<Weapon, Integer> weps = new HashMap<Weapon, Integer>();
		for(int i=0;i<p.getHand().size();i++) {
			if(p.getHand().get(i) instanceof Ally || p.getHand().get(i) instanceof Amour) {
				AA++;
			}
			if(p.getHand().get(i) instanceof Weapon) {
				Weapon w = (Weapon) p.getHand().get(i);
				if(weps.containsKey(w)) weps.replace(w, weps.get(w)+1);
				else weps.put((Weapon) p.getHand().get(i), 1);
			}
		}
		int cards = AA;
		ArrayList<Integer> w = new ArrayList<Integer>(weps.values());
		for(int i=0;i<w.size();i++) {
			cards += Math.min(w.get(i), state.getCurrentQuest().getNumStages()-AA/2);
		}
		if(AA+cards<state.getCurrentQuest().getNumStages()*2) return false;
		return true;
	}
	
	public int nextBid(GameState state, Player p) {
		return p.countFoes(20);
	}
	
	public ArrayList<AdventureCard> discardAfterWinningTest(GameState state, Player p){
		return p.getFoes(20);
	}

	
	public boolean tourneyEvolve(GameState state) {
		GameQuest q = state.getCurrentQuest();
		ArrayList<Player> players = new ArrayList<Player>(q.getPlayers());
		for(int i=0;i<players.size();i++) {
			if(players.get(i).getShields()+q.getQuest().getStages()>=players.get(i).getRequiredShields()) {
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
				if(!(p.getHand().get(i) instanceof Test)) cards.add(p.getHand().get(i));
			}
		}else {
			for(int i=0;i<dupes.size();i++) {
				if(dupes.get(i) instanceof Weapon) cards.add(dupes.get(i));
			}
		}
		return cards;
	}
	
	public ArrayList<Stage> createQuest(GameState state, Player p){
		p.sortHand(state);
		ArrayList<Stage> quest = new ArrayList<Stage>();
		ArrayList<Weapon> wepdupes = new ArrayList<Weapon>();
		wepdupes.sort(new CardComparator());
		int weppos = 0;
		int foepos = 0;
		int wepdupespos = 0;
		for(int i=0;i<state.getCurrentQuest().getNumStages();i++) {
			if(i==0) {
				for(int j=0;j<p.getHand().size();j++) {
					if(p.getHand().get(i) instanceof Foe) {
						Foe f = (Foe) p.getHand().get(i);
						while(f.getBp(state)<50 && weppos<p.getHand().size()) {
							if(p.getHand().get(i) instanceof Weapon) {
								f.addWeapon((Weapon) p.getHand().get(i));
							}
							weppos++;
						}
						Stage s = new Stage(f);
						quest.add(s);
						foepos = j;
						break;
					}
				}
				for(int j=0;j<p.getDuplicates().size();j++) {
					if(p.getDuplicates().get(j) instanceof Weapon) {
						wepdupes.add((Weapon) p.getDuplicates().get(j));
					}
				}
			}else if(i==1) {
				if(p.hasTest()) {
					for(int j=p.getHand().size()-1;j>=0;j--) {
						if(p.getHand().get(j) instanceof Test) {
							Stage s = new Stage((Test) p.getHand().get(j));
							quest.add(0, s);
							break;
						}
					}
				}else {
					while(foepos<p.getHand().size()) {
						if(p.getHand().get(foepos) instanceof Foe) {
							Foe f = (Foe) p.getHand().get(foepos);
							if(wepdupespos<wepdupes.size()) {
								f.addWeapon(wepdupes.get(wepdupespos));
								wepdupespos++;
							}
							Stage s = new Stage(f);
							quest.add(0,s);
							break;
						}
					}
				}
			}else {
				while(foepos<p.getHand().size()) {
					if(p.getHand().get(foepos) instanceof Foe) {
						Foe f = (Foe) p.getHand().get(foepos);
						if(wepdupespos<wepdupes.size()) {
							f.addWeapon(wepdupes.get(wepdupespos));
							wepdupespos++;
						}
						Stage s = new Stage(f);
						quest.add(0,s);
						break;
					}
				}
			}
		}
		return quest;
	}
	
	public ArrayList<AdventureCard> playStage(GameState state, Player p){
		p.sortHand(state);
		ArrayList<AdventureCard> stageCards = new ArrayList<AdventureCard>();
		if(state.getCurrentQuest().getCurrentStage()+1==state.getCurrentQuest().getNumStages()) {
			for(int i=0;i<p.getHand().size();i++) {
				if(!(p.getHand().get(i) instanceof Test)) {
					stageCards.add(p.getHand().get(i));
				}
			}
		}else {
			while(stageCards.size()<2) {
				for(int i=0;i<p.getHand().size();i++) {
					if(!(p.getHand().get(i) instanceof Test)) {
						stageCards.add(p.getHand().get(i));
						break;
					}
				}
			}
		}
		return stageCards;
	}
}

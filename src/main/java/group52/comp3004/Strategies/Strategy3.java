package group52.comp3004.Strategies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public class Strategy3 extends AbstractAI{
	
	public boolean doIParticipateInTournament(GameState state, Player p) {
		int WAA = countWAA(state, p, 1);
		ArrayList<Player> players = new ArrayList<Player>(state.getAllPlayers());
		for(int i=0;i<players.size();i++) {
			if(players.get(i).getHandSize()/2>WAA) return false;
		}
		return true;
	}
	
	public boolean doIParticipateInQuest(GameState state, Player p) {
		ArrayList<Integer> cNum = state.getCurrentQuest().getStageCardNum();
		int totalCards = 0;
		int WAA = countWAA(state, p, state.getCurrentQuest().getNumStages());
		int multiCardStage = 0;
		boolean hasTest = false;
		for(int i=0;i<cNum.size();i++) {
			totalCards += cNum.get(i);
			if(cNum.get(i)>1) multiCardStage++;
		}
		if(multiCardStage<state.getCurrentQuest().getNumStages()-1) hasTest = true;
		if(state.getCurrentQuest().getNumStages()==2 && multiCardStage<2) hasTest = true;
		if(hasTest) {
			if(WAA>totalCards-1 && p.countFoes(25)>2) {
				return true;
			}
		}else {
			if(WAA>totalCards) return true;
		}
		return false;
	}
	
	public int nextBid(GameState state, Player p) {
		return p.countFoes(25);
	}
	
	public ArrayList<AdventureCard> discardAfterWinningTest(GameState state, Player p){
		ArrayList<AdventureCard> discards = p.getFoes(25);
		for(int i=0;i<discards.size();i++) p.getHand().remove(discards.get(i));
		return discards;
	}
	
	public ArrayList<AdventureCard> playTourney(GameState state, Player p){
		HashSet<AdventureCard> tCards = new HashSet<AdventureCard>();
		int max = 0;
		for(int i=0;i<state.getAllPlayers().size();i++) {
			if(state.getAllPlayers().get(i)!=p)
				max = Math.max(max, state.getAllPlayers().get(i).getHand().size()/2);
		}
		int pos = 0;
		while(tCards.size()<=max && pos<p.getHandSize()) {
			if(p.getHand().get(pos) instanceof Ally || p.getHand().get(pos) instanceof Weapon)
				tCards.add(p.getHand().get(pos));
			else if(p.getHand().get(pos) instanceof Amour && !p.hasAmour())
				tCards.add(p.getHand().get(pos));
		}
		ArrayList<AdventureCard> cards = new ArrayList<AdventureCard>(tCards);
		for(int i=0;i<cards.size();i++) p.getHand().remove(cards.get(i));
		return cards;
	}
	
	public ArrayList<Stage> createQuest(GameState state, Player p){
		ArrayList<Stage> stages = new ArrayList<Stage>();
		int max = 0;
		for(int i=0;i<state.getAllPlayers().size();i++) {
			if(state.getAllPlayers().get(i)!=p) 
				max = Math.max(state.getAllPlayers().get(i).getHandSize(), max);
		}
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
				Foe f = makeFoe(state, p, max*10);
				Stage stage = new Stage(f);
				stages.add(stage);
			}
		}
		return stages;
	}
	
	public ArrayList<AdventureCard> playStage(GameState state, Player p){
		HashSet<AdventureCard> sCards = new HashSet<AdventureCard>();
		Stage current = state.getCurrentQuest().getStage(state.getCurrentQuest().getCurrentStage());
		int stage = current.getFoe().getWeapons().size()+1;
		int pos = 0;
		while(sCards.size()<=stage && pos<p.getHandSize()) {
			if(p.getHand().get(pos) instanceof Ally || p.getHand().get(pos) instanceof Weapon)
				sCards.add(p.getHand().get(pos));
			else if(p.getHand().get(pos) instanceof Amour && !p.hasAmour())
				sCards.add(p.getHand().get(pos));
		}
		ArrayList<AdventureCard> cards = new ArrayList<AdventureCard>(sCards);
		for(int i=0;i<cards.size();i++) p.getHand().remove(cards.get(i));
		return cards;
	}
}

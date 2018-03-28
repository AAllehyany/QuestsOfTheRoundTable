package group52.comp3004.Strategies;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
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
		if(anyEvolve(state)) {
			logger.info("Player: " + p.getId() + " will not participate in the tournament");
			return true;
		}
		logger.info("Player: " + p.getId() + " will participate in the tournament");
		return false;
	}
	
	public boolean doIParticipateInQuest(GameState state, Player p) {
		if(this.countFoes(p, 20)<2) {
			logger.info("Player: " + p.getId() + " does not have enough foes to discard, so will not participate in the quest");
			return false;
		}
		int sum = countWAA(state, p, state.getCurrentQuest().getNumStages());
		if(sum<state.getCurrentQuest().getNumStages()*2) {
			logger.info("Player: " + p.getId() + "does not have enough cards to play, so will not participate in the quest");
			return false;
		}
		logger.info("Player: " + p.getId() + " will participate in the quest");
		return true;
	}
	
	public int nextBid(GameState state, Player p) {
		logger.info("Player: " + p.getId() + " bids " + this.countFoes(p, 20) + " cards");
		return this.countFoes(p, 20);
	}
	
	public ArrayList<AdventureCard> discardAfterWinningTest(GameState state, Player p){
		ArrayList<AdventureCard> dcards = this.getFoes(p, 20);
		for(int i=0;i<dcards.size();i++) p.discard(dcards.get(i));
		return dcards;
	}

	
	private boolean tourneyEvolve(GameState state) {
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
		ArrayList<AdventureCard> dupes = this.getDuplicates(p);
		if(tourneyEvolve(state)) {
			for(int i=0;i<p.getHand().size();i++) {
				if(!(p.getHand().get(i) instanceof Tests || p.getHand().get(i) instanceof Foe) &&
						!cards.contains(p.getHand().get(i))) {
					cards.add(p.getHand().get(i));
				}
			}
		}else {
			for(int i=0;i<dupes.size();i++) {
				if(dupes.get(i) instanceof Weapon && !cards.contains(dupes.get(i))) {
					cards.add(dupes.get(i));
				}
			}
		}
		for(int i=0;i<cards.size();i++) p.discard(cards.get(i));
		return cards;
	}
	
	public ArrayList<Stage> createQuest(GameState state, Player p){
		p.sortHand(state);
		ArrayList<Stage> quest = new ArrayList<Stage>();
		ArrayList<AdventureCard> wepdupes = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> dupes = this.getDuplicates(p);
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
		logger.info("Player " + p.getId() + " created quest with stages: ");
		for(int i=0;i<quest.size();i++) {
			if(quest.get(i).isTestStage())
				logger.info("Stage " + i+1 + ": " + quest.get(i).getTest().getName());
			else {
				logger.info("Stage " + i+1 + ": " + quest.get(i).getFoe().getName());
				if(!quest.get(i).getFoe().getWeapons().isEmpty())
					logger.info("with weapons: " + quest.get(i).getFoe().getWeapons().stream().map(w -> w.getName() + " "));
			}
		}
		return quest;
	}
	
	private void giveWeapon(GameState state, Player p, Foe f, ArrayList<AdventureCard> weps, int last) {
		for(int i=0;i<weps.size();i++) {
			if(f.getBp(state)+weps.get(i).getBp()<last) {
				f.addWeapon((Weapon) weps.get(i));
				p.discard(weps.remove(i));
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
						if(!this.containsAmour(stageCards) && !this.hasAmour(p)) stageCards.add(p.discard(p.getHand().get(i)));
					}else {
						stageCards.add(p.discard(p.getHand().get(i)));
					}
				}
			}
			for(int i=0;i<stageCards.size();i++) p.getHand().remove(stageCards.get(i));
		}else {
			while(stageCards.size()<2) {
				if(!this.hasAmour(p) && this.hasAmourInHand(p) && 
						!containsAmour(stageCards)) stageCards.add(this.getAmourInHand(p));
				else if(this.hasAllyInHand(p)) stageCards.add(this.getStrongestAllyInHand(state, p));
				else stageCards.add(getWeakestWeapon(state,p,stageCards));
			}
		}
		return stageCards;
	}
}

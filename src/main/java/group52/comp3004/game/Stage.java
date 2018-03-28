package group52.comp3004.game;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Tests;

public class Stage {
	
	private final Foe foe;
	private final Tests test;
	
	private final boolean testStage;
	
	private static final Logger logger = Logger.getLogger(Stage.class);
	
	/**
	 * @param foe
	 */
	public Stage(Foe foe) {
		super();
		this.foe = foe;
		this.test = null;
		testStage = false;
	}
	
	public Stage(Tests test) {
		super();
		this.foe = null;
		this.test = test;
		testStage = true;
	}
	
	public ArrayList<AdventureCard> getCards(){
		ArrayList<AdventureCard> cards = new ArrayList<AdventureCard>();
		if(this.testStage) {
			cards.add(this.test);
		}else {
			cards.addAll(this.foe.getWeapons());
			cards.add(this.foe);
		}
		return cards;
	}
	
	public int getTotalPower(GameState state) {
		if(!testStage) logger.info("Battle power of the stage is " + foe.getBp(state));
		return testStage ? 0 : foe.getBp(state);
	}
	
	public boolean isTestStage() {
		return testStage;
	}
	
	public int totalCardsPlayed() {
		return (testStage) ? 1 : 1 + this.foe.getWeapons().size();
	}

	public Foe getFoe() {
		return foe;
	}
	
	public Tests getTest() {
		return test;
	}
}

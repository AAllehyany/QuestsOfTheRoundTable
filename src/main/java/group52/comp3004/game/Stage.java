package group52.comp3004.game;

import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Tests;

public class Stage {
	
	private final Foe foe;
	private final Tests test;
	
	private final boolean testStage;
	
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
	
	public int getTotalPower() {
		return testStage ? 0 : foe.getBp();
	}
	
	public int getTotalPower(GameState state) {
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

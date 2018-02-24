package group52.comp3004.game;

import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Test;

public class Stage {
	
	private final Foe foe;
	private final Test test;
	
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
	
	public Stage(Test test) {
		super();
		this.foe = null;
		this.test = test;
		testStage = true;
	}
	
	public int getTotalPower() {
		return foe.getBp();
	}
	
	public boolean isTestStage() {
		return testStage;
	}
	
	public int totalCardsPlayed() {
		return 1 + this.foe.getWeapons().size();
	}

	public Foe getFoe() {
		return foe;
	}
	
}

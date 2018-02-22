package group52.comp3004.game;

import group52.comp3004.cards.Foe;

public class Stage {
	
	private final Foe foe;
	
	private final boolean testStage;
	
	/**
	 * @param foe
	 */
	public Stage(Foe foe) {
		super();
		this.foe = foe;
		testStage = false;
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

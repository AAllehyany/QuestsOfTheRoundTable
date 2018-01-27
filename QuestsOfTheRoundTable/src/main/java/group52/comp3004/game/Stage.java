package group52.comp3004.game;

import group52.comp3004.cards.Foe;

public class Stage {
	
	private final Foe foe;

	/**
	 * @param foe
	 */
	public Stage(Foe foe) {
		super();
		this.foe = foe;
	}
	
	public int getTotalPower() {
		return foe.getBp();
	}
}

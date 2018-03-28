package group52.comp3004.game;

import java.util.ArrayList;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Tests;

/**
 * Holds all information about an individual stage of a quest.
 * @author Sandy
 *
 */
public class Stage {
	
	private final Foe foe;
	private final Tests test;
	
	private final boolean testStage;
	
	/**
	 * Constructor for a standard stage. Should be used only for foe stages.
	 * <p>If stage is a test use other constructor</p>
	 * @param foe The stage foe.
	 */
	public Stage(Foe foe) {
		super();
		this.foe = foe;
		this.test = null;
		testStage = false;
	}
	
	/**
	 * Constructor for a standard stage. Should be used only for test stages.
	 * <p>If stage is a foe use other constructor</p>
	 * @param test The stage test.
	 */
	public Stage(Tests test) {
		super();
		this.foe = null;
		this.test = test;
		testStage = true;
	}
	
	/**
	 * Get a list of all cards used for the stage. 
	 */
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
	
	/**
	 * Gets the total battle power provided by a stage.
	 * <p>Used only for testing</p>
	 * @return the stage's battle power
	 */
	public int getTotalPower() {
		return testStage ? 0 : foe.getBp();
	}
	
	/**
	 * Gets the total battle power provided by a stage.
	 * @param state The current conditions of the game
	 * @return the stage's battle power
	 */
	public int getTotalPower(GameState state) {
		return testStage ? 0 : foe.getBp(state);
	}
	
	/**
	 * Is the stage a test.
	 * @return True is a test. False if a foe stage.
	 */
	public boolean isTestStage() {
		return testStage;
	}
	
	/**
	 * ?
	 * @return
	 */
	public int totalCardsPlayed() {
		return (testStage) ? 1 : 1 + this.foe.getWeapons().size();
	}

	/**
	 * Get the foe card used in the stage.
	 * @return Foe object
	 */
	public Foe getFoe() {
		return foe;
	}
	
	/**
	 * Get the tests card used in the stage.
	 * @return
	 */
	public Tests getTest() {
		return test;
	}
}

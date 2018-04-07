package group52.comp3004.game;

import java.util.ArrayList;

import org.apache.log4j.Logger;

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
	
	private static final Logger logger = Logger.getLogger(Stage.class);
	
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
	 * @param state The current conditions of the game
	 * @return the stage's battle power
	 */
	public int getTotalPower(GameState state) {
		if(!testStage) logger.info("Battle power of the stage is " + foe.getBp(state));
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
	 * Determine the total number of cards in the stage to implement the cheat function and to help
	 * with determining the number of cards to deal to the sponsor at the end of the quest
	 * @return the number of cards in the stage
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

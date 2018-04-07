package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.game.GameQuest;

public class CardFactory {
	
	static final private Logger logger = Logger.getLogger(CardFactory.class);
	
	/**
	 * Create a weapon card
	 * @param name the name of the weapon card
	 * @param bp the battle power of the weapon card
	 * @return the created weapon card
	 */
	public static Weapon createWeapon(String name, int bp) {
		logger.info("Weapon: " + name + "created");
		return new Weapon(name, bp);
	}
	
	/**
	 * Create an ally that does not have a bonus state card
	 * @param name the name of the ally card
	 * @param bp the battle power of the ally card
	 * @param bids the bid points of the ally card
	 * @return the created ally card
	 */
	public static Ally createAlly(String name, int bp, int bids) {
		logger.info("Ally: " + name + "created");
		return new Ally(name, bp, bids);
	}
	
	/**
	 * Create an ally that has a bonus state card
	 * @param name the name of the ally card
	 * @param bp the battle power of the ally card
	 * @param bids the bid points of the ally card
	 * @param bonus the conditions required to satisfy the bonus card
	 * @param bonusbp the resultant bonus state battle power card
	 * @param bonusbids the resultant bonus state bid points card
	 * @return the created ally card
	 */
	public static Ally createAlly(String name, int bp, int bids, String bonus, int bonusbp, int bonusbids) {
		logger.info("Ally: " + name + "created");
		return new Ally(name, bp, bids, bonus, bonusbp, bonusbids);
	}
	
	/**
	 * Create an ally that has 0 battle power, 0 bid points, and no bonus state card
	 * @param name the name of the ally card
	 * @return the created ally card
	 */
	public static Ally createAlly(String name) {
		logger.info("Ally: " + name + "created");
		return new Ally(name);
	}
	
	/**
	 * Create an amour card
	 * @param name the name of the amour card
	 * @param bp the battle power of the amour card
	 * @param bids the bid points of the amour card
	 * @return the created amour card
	 */
	public static Amour createAmour(String name, int bp, int bids) {
		logger.info("Amour: " + name + "created");
		return new Amour(name, bp, bids);
	}
	
	/**
	 * Create a foe with no bonus state card
	 * @param name the name of the foe card
	 * @param bp the battle power of the foe card
	 * @return the created foe card
	 */
	public static Foe createFoe(String name, int bp) {
		logger.info("Foe: " + name + "created");
		return new Foe(name, bp);
	}
	
	/**
	 * Create a foe with a bonus state card
	 * @param name the name of the foe card
	 * @param bp the battle power of the foe card
	 * @param higherbp the resultant battle power of the bonus state card
	 * @param quest the quest required to achieve the bonus state card
	 * @return the created foe card
	 */
	public static Foe createFoe(String name, int bp, int higherbp, String quest) {
		logger.info("Foe: " + name + "created");
		return new Foe(name, bp, higherbp, quest);
	}
	
	/**
	 * Create a test card
	 * @param name the name of the test card
	 * @param min the minimum bids for the test card
	 * @return the created test card
	 */
	public static Tests createTests(String name, int min) {
		logger.info("Test: " + name + "created");
		return new Tests(name, min);
	}
	
	/**
	 * Create a quest card
	 * @param name the name of the quest card
	 * @param stages the number of stages in the quest card
	 * @return the created quest card
	 */
	public static QuestCard createQuest(String name, int stages) {
		logger.info("Quest: " + name + "created");
		return new QuestCard(name, stages);
	}
	
	/**
	 * Create an event card
	 * @param name the name of the event card
	 * @param behavior the effect of the event card
	 * @return the created event card
	 */
	public static EventCard createEvent(String name, EventBehaviour behavior) {
		logger.info("Event: " + name + "created");
		return new EventCard(name, behavior);
	}
	
	/**
	 * Create a tournament card
	 * @param name the name of the tournament card
	 * @param bonus the bonus shields rewarded by the tournament card
	 * @return the created tournament card
	 */
	public static Tourneys createTourney(String name, int bonus) {
		logger.info("Tourney: " + name + "created");
		return new Tourneys(name, bonus);
	}
}

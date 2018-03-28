package group52.comp3004.cards;

import org.apache.log4j.Logger;

import group52.comp3004.game.GameQuest;

public class CardFactory {
	
	static final private Logger logger = Logger.getLogger(CardFactory.class);
	
	public static Weapon createWeapon(String name, int bp) {
		logger.info("Weapon: " + name + "created");
		return new Weapon(name, bp);
	}
	
	public static Ally createAlly(String name, int bp, int bids) {
		logger.info("Ally: " + name + "created");
		return new Ally(name, bp, bids);
	}
	
	public static Ally createAlly(String name, int bp, int bids, String bonus, int bonusbp, int bonusbids) {
		logger.info("Ally: " + name + "created");
		return new Ally(name, bp, bids, bonus, bonusbp, bonusbids);
	}
	
	public static Ally createAlly(String name) {
		logger.info("Ally: " + name + "created");
		return new Ally(name);
	}
	
	public static Amour createAmour(String name, int bp, int bids) {
		logger.info("Amour: " + name + "created");
		return new Amour(name, bp, bids);
	}
	
	public static Foe createFoe(String name, int bp) {
		logger.info("Foe: " + name + "created");
		return new Foe(name, bp);
	}
	
	public static Foe createFoe(String name, int bp, int higherbp, String quest) {
		logger.info("Foe: " + name + "created");
		return new Foe(name, bp, higherbp, quest);
	}
	
	public static Tests createTests(String name, int min) {
		logger.info("Test: " + name + "created");
		return new Tests(name, min);
	}
	
	public static QuestCard createQuest(String name, int stages) {
		logger.info("Quest: " + name + "created");
		return new QuestCard(name, stages);
	}
	
	public static EventCard createEvent(String name, EventBehaviour behavior) {
		logger.info("Event: " + name + "created");
		return new EventCard(name, behavior);
	}
	
	public static Tourneys createTourney(String name, int bonus) {
		logger.info("Tourney: " + name + "created");
		return new Tourneys(name, bonus);
	}
}

package group52.comp3004.cards;

public class CardFactory {
	public static Weapon createWeapon(String name, int bp) {
		return new Weapon(name, bp);
	}
	
	public static Ally createAlly(String name, int bp, int bids) {
		return new Ally(name, bp, bids);
	}
	
	public static Ally createAlly(String name, int bp, int bids, String bonus, int bonusbp, int bonusbids) {
		return new Ally(name, bp, bids, bonus, bonusbp, bonusbids);
	}
	
	public static Ally createAlly(String name) {
		return new Ally(name);
	}
	
	public static Amour createAmour(String name, int bp, int bids) {
		return new Amour(name, bp, bids);
	}
	
	public static Foe createFoe(String name, int bp) {
		return new Foe(name, bp);
	}
	
	public static Foe createFoe(String name, int bp, int higherbp, String quest) {
		return new Foe(name, bp, higherbp, quest);
	}
	
	public static Tests createTests(String name, int min) {
		return new Tests(name, min);
	}
	
	public static QuestCard createQuest(String name, int stages) {
		return new QuestCard(name, stages);
	}
	
	public static EventCard createEvent(String name, EventBehaviour behavior) {
		return new EventCard(name, behavior);
	}
	
	public static Tourneys createTourney(String name, int bonus) {
		return new Tourneys(name, bonus);
	}
}

package group52.comp3004.cards;


public enum CardType {
	//ADVENTURE DECK:
	
	//Weapons
	Excalibur("Excalibur"),
	Lance("Lance"),Battleax("Battle-ax"),Sword("Sword"),Horse("Horse"),Dagger("Dagger"),
	//Foes
	Dragon("Dragon"),Giant("Giant"),Mordred("Mordred"),GreenKnight("Green Knight"),BlackKnight("Black Knight"),
	EvilKnight("Evil Knight"),SaxonKnight("Saxon Knight"),RobberKnight("Robber Knight"),Saxons("Saxons"),
	Boar("Boar"),Thieves("Thieves"),
	//Tests
	Valor("Test of Valor"),Temptation("Test of Temptation"),MorganLeFey("Test of Morgan Le Fey"),
	QuestingBeast("Test of The Questing Beast"),
	//Allies
	Galahad("Sir Galahad"), Lancelot("Sir Lancelot"),Arthur("King Arthur"),Tristan("Sir Tristan"),
	Pellinore("Sir Prllinore"),Gawain("Sir Gawain"),Percival("Sir Percival"),Guinevere("Queen Guinevere"),
	Iseult("Queen Iseult"), Merlin("Merlin"),
	//Amours
	Amours("Amours"),
	
	//STORY DECK:
	
	//Quests
	SearchHolyGrail("Search for the Holy Grail"),TestGreenKnight("Test of the Green Knight"),
	SearchQuestingBeast("Search for the Questing Beast"),QueensHonor("Defend the Queen's Honor"),
	FairMaiden("Rescue the Fair Maiden"),EnchantedForest("Journey Through the Enchanted Forest"),
	AuthorsEnemies("Vanquish King Arthur's Enemies"),SlayDragon("Slay the Dragon"), BoarHunt("BoarHunt"),
	SaxonRider("Repel the Saxon Raiders"),
	//Tournament
	Camelot("Tournament at Camelot"),Orkney("Tournament at Orkney"),Tintagel("Tournament at Tintagel"),
	York("Tournament at York"),
	//Events
	Recognition("King's Recognition"),Favor("Queen's favor"),CourtCall("Court Called to Camelot"),
	Pox("Pox"),Plague("Plague"),Deed("Chivalrous Deed"),Realm("Prosperity Throughout the Realm"),
	Arms("King's Call to Arms"),
	
	//RANK DECK:
	Squire("Squire"),Knight("Knight"),ChampionKnight("ChampionKnight");
	
	private final String cardText;
	
	private CardType(String cardText) {
		this.cardText= cardText;
	}
	public String printCT() {
		return cardText;
	}
}

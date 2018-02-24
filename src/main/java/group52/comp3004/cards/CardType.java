package group52.comp3004.cards;


public enum CardType {
	//ADVENTURE DECK:
	
	//Weapons
	Excalibur("Excalibur"),
	Lance("Lance"),Battleax("Battle-ax"),Sword("Sword"),Horse("Horse"),Dagger("Dagger"),
	//Foes
	Dragon("Dragon"),Giant("Giant"),Mordred("Mordred"),GreenKnight("Green_Knight"),BlackKnight("Black_Knight"),
	EvilKnight("Evil_Knight"),SaxonKnight("Saxon_Knight"),RobberKnight("Robber_Knight"),Saxons("Saxons"),
	Boar("Boar"),Thieves("Thieves"),
	//Tests
	Valor("Test_of_Valor"),Temptation("Test_of_Temptation"),MorganLeFey("Test_of_Morgan_Le_Fey"),
	QuestingBeast("Test of The Questing Beast"),
	//Allies
	Galahad("Sir_Galahad"), Lancelot("Sir_Lancelot"),Arthur("King_Arthur"),Tristan("Sir_Tristan"),
	Pellinore("Sir_Prllinore"),Gawain("Sir_Gawain"),Percival("Sir_Percival"),Guinevere("Queen_Guinevere"),
	Iseult("Queen_Iseult"), Merlin("Merlin"),
	//Amours
	Amours("Amour"),
	
	//STORY DECK:
	
	//Quests
	SearchHolyGrail("Search_for_the_Holy Grail"),TestGreenKnight("Test_of_the_Green Knight"),
	SearchQuestingBeast("Search_for_the_Questing_Beast"),QueensHonor("Defend_the_Queen's_Honor"),
	FairMaiden("Rescue_the_Fair_Maiden"),EnchantedForest("Journey_Through_the_Enchanted_Forest"),
	AuthorsEnemies("Vanquish_King_Arthur's_Enemies"),SlayDragon("Slay_the_Dragon"), BoarHunt("BoarHunt"),
	SaxonRider("Repel_the_Saxon_Raiders"),
	//Tournament
	Camelot("Tournament_at_Camelot"),Orkney("Tournament_at_Orkney"),Tintagel("Tournament_at_Tintagel"),
	York("Tournament_at_York"),
	//Events
	Recognition("King's_Recognition"),Favor("Queen's_favor"),CourtCall("Court_Called_to_Camelot"),
	Pox("Pox"),Plague("Plague"),Deed("Chivalrous_Deed"),Realm("Prosperity_Throughout_the_Realm"),
	Arms("King's_Call_to_Arms"),
	
	//RANK DECK:
	Squire("Squire"),Knight("Knight"),ChampionKnight("Champion_Knight");
	
	private final String cardText;
	
	private CardType(String cardText) {
		this.cardText= cardText;
	}
	public String printCT() {
		return cardText;
	}
}

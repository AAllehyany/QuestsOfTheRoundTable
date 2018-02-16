package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public class GameQuestTest {
	
	private QuestCard journey;
	private GameQuest quest;

	
	@Test
	public void testHasCorrectNumberOfStages() {
		journey = new QuestCard("LOL Quest", 3);
		quest = new GameQuest(journey, new Player(1));
		assertEquals(3, quest.getNumStages());
	}
	
	@Test
	public void testAdvancesStageCorrectly() {
		journey = new QuestCard("LOL Quest", 3);
		quest = new GameQuest(journey, new Player(1));
		assertEquals(0, quest.getCurrentStage());
		
		quest.advanceStage();
		assertEquals(1, quest.getCurrentStage());
		
		quest.advanceStage();
		assertEquals(2, quest.getCurrentStage());
		
		quest.advanceStage();
		assertEquals(2, quest.getCurrentStage());
	}
	
	@Test
	public void testFoePowerIncreasesInEachStage() {
		journey = new QuestCard("LOL Quest", 3);
		quest = new GameQuest(journey, new Player(1));
		
		Foe theGoodGuy = new Foe("I AM GOOD GUY", 10, 20);
		Foe theBadBoy = new Foe("I AM BAD", 2, 1999);
		Foe extremelyNiceFoe = new Foe("EXTREMELY NICE FOE", 103, 200);
		
		Stage stage1 = new Stage(theGoodGuy);
		Stage stage2 = new Stage(theBadBoy);
		Stage stage3 = new Stage(extremelyNiceFoe);
		
		assert(quest.addStage(stage1));
		assertFalse(quest.addStage(stage2));
		assert(quest.addStage(stage3));
		
		assert(quest.addStage(stage3));
		assertFalse(quest.addStage(stage3));
	}
	
	
	@Test 
	public void testPlaysStage() {
		journey = new QuestCard("LOL Quest", 3);
		quest = new GameQuest(journey, new Player(1));
		
		Foe theGoodGuy = new Foe("I AM GOOD GUY", 2, 20);
		Foe theBadBoy = new Foe("I AM BAD", 2, 1999);
		Foe extremelyNiceFoe = new Foe("EXTREMELY NICE FOE", 6, 200);
		
		Stage stage1 = new Stage(theGoodGuy);
		Stage stage2 = new Stage(theBadBoy);
		Stage stage3 = new Stage(extremelyNiceFoe);
		
		quest.addStage(stage1);
		quest.addStage(stage2);
		quest.addStage(stage3);
		
		Player p1 = new Player(13);
		Player p2 = new Player(22);
		Player p3 = new Player(31);
		
		
		quest.addPlayer(p1);
		quest.addPlayer(p2);
		quest.addPlayer(p3);
		
		
		assertEquals(3, quest.getPlayers().size());
		assertEquals(0, quest.getCurrentStage());
		assertFalse(quest.isOver());
		
		quest.playStage();
		assertFalse(quest.isOver());
		assertEquals(1, quest.getCurrentStage());
		assertEquals(3, quest.getPlayers().size());
		
		quest.playStage();
		assertFalse(quest.isOver());
		assertEquals(2, quest.getCurrentStage());
		assertEquals(3, quest.getPlayers().size());
		
		quest.playStage();
		assert(quest.isOver());
		assertEquals(2, quest.getCurrentStage());
		assertEquals(0, quest.getPlayers().size());
		
		
	}
	
	@Test
	public void testAwardsShields() {
		journey = new QuestCard("LOL Quest", 3);
		quest = new GameQuest(journey, new Player(1));
		
		Foe theGoodGuy = new Foe("I AM GOOD GUY", 2, 20);
		Foe theBadBoy = new Foe("I AM BAD", 2, 1999);
		Foe extremelyNiceFoe = new Foe("EXTREMELY NICE FOE", 6, 200);
		
		Stage stage1 = new Stage(theGoodGuy);
		Stage stage2 = new Stage(theBadBoy);
		Stage stage3 = new Stage(extremelyNiceFoe);
		
		quest.addStage(stage1);
		quest.addStage(stage2);
		quest.addStage(stage3);
		
		Player p1 = new Player(13);
		Player p2 = new Player(22);
		Player p3 = new Player(31);
		
		
		quest.addPlayer(p1);
		quest.addPlayer(p2);
		quest.addPlayer(p3);
		
		quest.awardShields();
		
		assertEquals(10, (int) p1.getShields());
		assertEquals(10, (int) p2.getShields());
		assertEquals(10, (int) p3.getShields());
		
		quest.end();
		
		quest.awardShields();
		assertEquals(13, (int) p1.getShields());
		assertEquals(13, (int) p2.getShields());
		assertEquals(13, (int) p3.getShields());
	}
	
	public void testGetsCorrectNumCardsPlayedBySponsor() {
		GameState g = new GameState();
		journey = new QuestCard("LOL Quest", 3);
		Player sponsor = new Player(1);
		g.addPlayer(sponsor);
		quest = new GameQuest(journey, sponsor);
		
		Foe theGoodGuy = new Foe("I AM GOOD GUY", 2, 20);
		Foe theBadBoy = new Foe("I AM BAD", 2, 1999);
		Foe extremelyNiceFoe = new Foe("EXTREMELY NICE FOE", 6, 200);
		
		Stage stage1 = new Stage(theGoodGuy);
		Stage stage2 = new Stage(theBadBoy);
		Stage stage3 = new Stage(extremelyNiceFoe);
		
		quest.addStage(stage1);
		quest.addStage(stage2);
		quest.addStage(stage3);
		
		assertEquals(3, quest.getNumCardsPlayedBySponsor());
		
		
		
		theGoodGuy.addWeapon(new Weapon("goodweapon", 28));
		assertEquals(4, quest.getNumCardsPlayedBySponsor());
		
		quest.dealCardsToSponsor();
		
		assertEquals(4, sponsor.getHand().size());
		
	}
}

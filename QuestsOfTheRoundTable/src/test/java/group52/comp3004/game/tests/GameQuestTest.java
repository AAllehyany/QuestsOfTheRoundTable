package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.game.GameQuest;
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
		
		
	}
}

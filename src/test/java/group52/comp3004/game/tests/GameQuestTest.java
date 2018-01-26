package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.Stage;

public class GameQuestTest {
	
	private QuestCard journey;
	private GameQuest quest;

	
	@Test
	public void testHasCorrectNumberOfStages() {
		journey = new QuestCard("LOL Quest", 3);
		quest = new GameQuest(journey);
		assertEquals(3, quest.getNumStages());
	}
	
	@Test
	public void testAdvancesStageCorrectly() {
		journey = new QuestCard("LOL Quest", 3);
		quest = new GameQuest(journey);
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
		quest = new GameQuest(journey);
		
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
}

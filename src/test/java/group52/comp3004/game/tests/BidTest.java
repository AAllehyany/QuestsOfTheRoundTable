package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Tests;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public class BidTest {
	private QuestCard journey;
	private GameQuest quest;
	private GameState state;
	
	@Test
	public void testBid() {
		state = new GameState();
		journey = new QuestCard("Boar_Hunt", 2);
		quest = new GameQuest(journey, new Player(1));
		
		Foe aBoarOhNo = new Foe("Boar", 5, 15, "Boar_Hunt");
		Tests evilKanevil = new Tests("Morgan_le_Fey", 3); 
		
		Stage stage1 = new Stage(aBoarOhNo);
		Stage stage2 = new Stage(evilKanevil);
		
		quest.addStage(state, stage1);
		quest.addStage(state, stage2);
		assertEquals(2, quest.getStages().size());
		assert(quest.isWithTest());
		
		Player p1 = new Player(0);
		Player p2 = new Player(1);
		Player p3 = new Player(2);

		state.addPlayer(p1);
		state.addPlayer(p2);
		state.addPlayer(p3);
		
		quest.addPlayer(p1);
		quest.addPlayer(p2);
		quest.addPlayer(p3);
		
		assertEquals(3, quest.getPlayers().size());
		assertEquals(0, quest.getCurrentStage());
		assertFalse(quest.isOver());
		for(int i = 0; i < 12; i++) {
			p1.addCardToHand(state.getAdventureDeck().draw());
			p2.addCardToHand(state.getAdventureDeck().draw());
			p3.addCardToHand(state.getAdventureDeck().draw());
		}
		System.out.println("Player 0 card num: "+ p1.getHandSize());
		
		p1.bidCards(2);
		p2.bidCards(3);
		p3.bidCards(4);
		quest.playStage(state);
		assertFalse(quest.isOver());
		assertEquals(1, quest.getCurrentStage());
		assertEquals(3, quest.getPlayers().size());
		
		quest.playStage(state);
		assert(quest.isOver());
		assertEquals(1, quest.getCurrentStage());
		assertEquals(1, quest.getPlayers().size());
	}
}

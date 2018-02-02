package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;

public class GameStateTest {

	
	@Test
	public void testChangesTurnProperly() {
		GameState gs = new GameState(Arrays.asList(new Player(13), new Player(22), new Player(31), new Player(40)));
		
		assertEquals(0, gs.getCurrentTurn());
		
		gs.nextTurn();
		assertEquals(1, gs.getCurrentTurn());
		
		gs.nextTurn();
		assertEquals(2, gs.getCurrentTurn());
		
		gs.nextTurn();
		assertEquals(3, gs.getCurrentTurn());
		
		gs.nextTurn();
		assertEquals(0, gs.getCurrentTurn());
	}
	
	@Test
	public void testChangesPlayerProperly() {
		GameState gs = new GameState(Arrays.asList(new Player(13), new Player(22), new Player(31), new Player(40)));
		
		assertEquals(0, gs.getCurrentTurn());
		assertEquals(0, gs.getCurrentPlayer());
		
		gs.nextTurn();
		assertEquals(1, gs.getCurrentPlayer());
		
		gs.nextTurn();
		assertEquals(2, gs.getCurrentPlayer());
		
		gs.nextTurn();
		assertEquals(3, gs.getCurrentPlayer());
		
		gs.nextPlayer();
		assertEquals(0, gs.getCurrentPlayer());
		
		gs.nextPlayer();
		assertEquals(1, gs.getCurrentPlayer());
		
		gs.nextPlayer();
		assertEquals(2, gs.getCurrentPlayer());
		
		gs.nextPlayer();
		assertEquals(3, gs.getCurrentPlayer());
	}
	
	@Test
	public void testDeals12CardsToAllPlayers() {
		GameState gs = new GameState();
		Player p1 = new Player(123);
		Player p2 = new Player(1234);
		Player p3 = new Player(1247);
		Player p4 = new Player(1468);
		
		gs.addPlayer(p1);
		gs.addPlayer(p2);
		gs.addPlayer(p3);
		gs.addPlayer(p4);
		
		assertEquals(0, p1.getHand().size());
		assertEquals(0, p2.getHand().size());
		assertEquals(0, p3.getHand().size());
		assertEquals(0, p4.getHand().size());
		
		gs.dealCardsToPlayers();
		
		assertEquals(12, p1.getHand().size());
		assertEquals(12, p2.getHand().size());
		assertEquals(12, p3.getHand().size());
		assertEquals(12, p4.getHand().size());
	}
	
	
}

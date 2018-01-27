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
}

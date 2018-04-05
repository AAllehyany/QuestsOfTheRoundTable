package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.Amour;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;

public class AmourTest {
	@Test
	public void testGetStats() {
		GameState state = new GameState();
		Player p1 = new Player(0);
		state.addPlayer(p1);
		Amour a = new Amour("Amour", 10, 1);
		p1.addCardToHand(a);
		p1.addTemp(a);
		assertEquals(10, (int) a.getBp(state));
		assertEquals(1, (int) a.getBids(state));
	}
	
	@Test
	public void testPlayerStats() {
		GameState state = new GameState();
		Player p1 = new Player(0);
		state.addPlayer(p1);
		Amour a = new Amour("Amour", 10, 1);
		p1.addCardToHand(a);
		p1.addTemp(a);
		assertEquals(15, (int) p1.getBattlePoints(state));
		assertEquals(1, (int) p1.getBidPoints(state));
	}

}

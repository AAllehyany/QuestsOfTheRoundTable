package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.Amour;

public class AmourTest {

	@Test
	public void testGetStats() {
		Amour a = new Amour("Amour", 10, 1);
		assertEquals(10, (int) a.getBP());
		assertEquals(1, (int) a.getBids());
	}

}

package group52.comp3004.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import group52.comp3004.GameStateDemo;

public class GameStateTest {
	
	@Test
	public void testAddsPlayer() {
		GameStateDemo game = new GameStateDemo();
		
		assertEquals(new ArrayList<String>(), game.getPlayers());
		
		game.addPlayer("xXEdgyXx");
		
		assertEquals(Arrays.asList("xXEdgyXx"), game.getPlayers());
	}
}

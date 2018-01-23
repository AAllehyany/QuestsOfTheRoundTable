package group52.comp3004.players.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import group52.comp3004.players.Player;
import group52.comp3004.players.Rank;

public class PlayerTest {

	@Test
	public void testGetsCorrectRank() {
		Player p = new Player(5);
		
		assertEquals(Rank.Squire, p.getRank());
		
		p.addShields(1);
		
		assertEquals(Rank.Squire, p.getRank());
		
		p.addShields(5);
		assertEquals(Rank.Knight, p.getRank());
		
		p.addShields(20);
		assertEquals(Rank.KnightOfTheRoundTable, p.getRank());
		
		
		Player p2 = new Player(2);
		
		p2.addShields(13);
		assertEquals(Rank.ChampionKnight, p2.getRank());
	}
}

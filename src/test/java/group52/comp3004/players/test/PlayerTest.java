package group52.comp3004.players.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import group52.comp3004.players.Player;
import group52.comp3004.players.Rank;

public class PlayerTest {

	@Test
	public void testShieldsNeverGoBelowZero() {
		Player p = new Player(5);
		
		p.addShields(-19);
		
		assertEquals(0, (int) p.getShields());
	}
	
	@Test
	public void testRanksUpCorrectly() {
		Player p = new Player(109);
		
		assertEquals(Rank.Squire, p.getRank());
		
		p.addShields(2);
		assertEquals(Rank.Squire, p.getRank());
		
		p.addShields(6);
		assertEquals(Rank.Knight, p.getRank());
		
		
		p.addShields(10);
		assertEquals(Rank.ChampionKnight, p.getRank());
		
		p.addShields(4);
		assertEquals(Rank.KnightOfTheRoundTable, p.getRank());
	}
	
	@Test
	public void testRankDoesNotGoDownAfterLosingShields() {
		Player p = new Player(100000);
		
		assertEquals(Rank.Squire, p.getRank());
		
		p.addShields(10);
		assertEquals(Rank.Knight, p.getRank());
		
		p.addShields(-6);
		assertEquals(Rank.Knight, p.getRank());
		
		p.addShields(8);
		assertEquals(Rank.ChampionKnight, p.getRank());
		
		p.addShields(-2);
		assertEquals(Rank.ChampionKnight, p.getRank());
	}
	
	@Test
	public void testAddsWeaponPowerToBattlePoints() {
		Player p = new Player(1337);
		
		assertEquals(5, (int) p.getBattlePoints());
		
		p.addWeapon(12);
		
		assertEquals(17, (int) p.getBattlePoints());
	}
}

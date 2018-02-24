package group52.comp3004.players.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import group52.comp3004.ResourceManager;
import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Weapon;
import group52.comp3004.players.Player;
import group52.comp3004.players.Rank;

public class PlayerTest {
	private ResourceManager resman = new ResourceManager();
	
	@Test
	public void testShieldsDoNotGoBelowRank() {
		Player p = new Player(5);
		
		p.addShields(-19);
		
		assertEquals(10, (int) p.getShields());
		
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
		
		assertEquals(10, (int)  p.getShields());
		assertEquals(Rank.Squire, p.getRank());
		
		p.addShields(10);
		assertEquals(20, (int)  p.getShields());
		assertEquals(Rank.Knight, p.getRank());
		
		p.addShields(-6);
		assertEquals(15, (int)  p.getShields());
		assertEquals(Rank.Knight, p.getRank());
		
		p.addShields(8);
		assertEquals(23, (int)  p.getShields());
		assertEquals(Rank.ChampionKnight, p.getRank());
		
		p.addShields(-2);
		assertEquals(22, (int)  p.getShields());
		assertEquals(Rank.ChampionKnight, p.getRank());
	}
	
	@Test
	public void testGetsBPCorrectly() {
		Player p = new Player(1337);
		
		assertEquals(5, (int) p.getBattlePoints());
		
		p.addField(new Ally("Hello", resman, 15, 0));
		
		assertEquals(20, (int) p.getBattlePoints());
		
		p.addTemp(new Ally("Hello", resman, 15, 0));
		p.addTemp(new Weapon("Horse", resman, 10));
		
		assertEquals(45, (int) p.getBattlePoints());
	}
	
	@Test
	public void testDoesNotPlayCardNotInHand() {
		Player p = new Player(1337);
		
		Ally c = new Ally("hey", resman, 16, 0);
		
		p.playCardToField(c);
		
		assertEquals(new ArrayList<AdventureCard>(), p.getField());
		
		p.addCardToHand(c);
		p.playCardToField(c);
		
		assertEquals(Arrays.asList(c), p.getField());
		
		
	}
}

package group52.comp3004.players.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;
import group52.comp3004.players.Rank;

public class PlayerTest {
	private GameState state;
	
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
		state = new GameState();
		Player p = new Player(1337);
		
		assertEquals(5, (int) p.getBattlePoints(state));
		
		p.addField(new Ally("King_Arthur", 15, 0));
		
		assertEquals(20, (int) p.getBattlePoints(state));
		
		p.addTemp(new Ally("King_Arthur", 15, 0));
		p.addTemp(new Weapon("Horse", 10));
		
		assertEquals(45, (int) p.getBattlePoints(state));
	}
	
	@Test
	public void testDoesNotPlayCardNotInHand() {
		Player p = new Player(1337);
		
		Ally c = new Ally("King_Arthur", 16, 0);
		
		p.addField(c);
		
		assertEquals(new ArrayList<AdventureCard>(), p.getField());
		
		p.addCardToHand(c);
		p.addField(c);
		
		assertEquals(Arrays.asList(c), p.getField());
		
		
	}
	
	@Test
	public void testDoesNotBidMoreThanInHand() {
		Player p = new Player(1337);
		
		Ally c = new Ally("King_Arthur", 16, 0);
		ArrayList<AdventureCard> bids = new ArrayList();
		p.addCardToHand(c);
		p.addCardToHand(c);
		
		p.bidCards(10);
		
		assertEquals(0, (int) p.getOfferedBids());
		
		p.bidCards(-1);
		
		assertEquals(0, (int) p.getOfferedBids());
		
		p.bidCards(1);
		
		
		assertEquals(1, (int) p.getOfferedBids());
		
		p.bidCards(2);
		assertEquals(2, (int) p.getOfferedBids());
	}
}

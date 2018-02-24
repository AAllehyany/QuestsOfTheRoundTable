package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Foe;
import group52.comp3004.decks.Deck;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;

public class FoeTest {
	
	@Test
	public void testMordred() {
		GameState state = new GameState();
		Player p1 = new Player(1);
		Player p2 = new Player(2);
		Player p3 = new Player(3);
		state.addPlayer(p1);
		state.addPlayer(p2);
		state.addPlayer(p3);
		
		Deck<AdventureCard> aDeck = new Deck<AdventureCard>();
		
		Foe mordred = new Foe("Mordred", 30);
		Ally merlin = new Ally("Merlin");
		Ally arthur = new Ally("King_Arthur", 10, 2);
		p1.addField(merlin);
		p1.addField(arthur);
		p2.addCardToHand(mordred);
		p3.addCardToHand(mordred);
		
		boolean result = mordred.MordredSpecial(state, p2, 0, arthur, aDeck);
		
		assertEquals(true, (boolean) result);
		assertEquals(false, (boolean) p1.getField().contains(arthur));
		assertEquals(true, (boolean) p1.getField().contains(merlin));
		assertEquals(false, (boolean) p2.getHand().contains(mordred));
		assertEquals(true, (boolean) p3.getHand().contains(mordred));
	}

}

package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Card;
import group52.comp3004.cards.Weapon;
import group52.comp3004.decks.Deck;
import group52.comp3004.decks.DeckFactory;

public class DeckTest {
	AdventureCard excalibur = new Weapon("Excalibur", 30);
	AdventureCard horse = new Weapon("Horse", 10);
	AdventureCard sword = new Weapon("Sword", 10);
	AdventureCard dagger = new Weapon("Dagger", 5);
	AdventureCard dagger2 = new Weapon("Dagger", 5);
	AdventureCard lance = new Weapon("Lance", 20);
	Weapon battleax = new Weapon("Battle_Ax", 15);

	@Test
	public void testEquals() {
		assert(dagger.equals(dagger2));
		assertFalse(dagger.equals(lance));
	}
	
	@Test
	public void testCreateDeck() {
		Deck<Card> aDeck = new Deck<Card>();
		aDeck.addCard(excalibur);
		aDeck.addCard(horse);
		aDeck.addCard(sword);
		aDeck.addCard(dagger);
		aDeck.addCard(lance);
		aDeck.addCard(battleax);
		assertEquals(6, (int) aDeck.getSize());
	}
	
	@Test
	public void testDrawCard() {
		Deck<Card> aDeck = new Deck<Card>();
		aDeck.addCard(excalibur);
		aDeck.addCard(horse);
		aDeck.addCard(sword);
		aDeck.addCard(dagger);
		aDeck.addCard(lance);
		aDeck.addCard(battleax);
		Card fdraw = aDeck.draw();
		assertEquals(5, (int) aDeck.getSize());
		assertEquals(false, (boolean) (fdraw==null));
	}
	
	@Test
	public void testDiscardCard() {
		Deck<Card> aDeck = new Deck<Card>();
		aDeck.addCard(excalibur);
		aDeck.addCard(horse);
		aDeck.addCard(sword);
		aDeck.addCard(dagger);
		aDeck.addCard(lance);
		aDeck.addCard(battleax);
		Card discarded = aDeck.discard(excalibur);
		assertEquals("Excalibur", (String) discarded.getName());
	}
	
	@Test
	public void testDiscardArrayListCard() {
		Deck<AdventureCard> aDeck = DeckFactory.createAdventureDeck();
		ArrayList<AdventureCard> cards = new ArrayList<AdventureCard>();
		cards.add(excalibur);
		cards.add(sword);
		cards.add(lance);
		aDeck.discard(cards);
		assertEquals(3,cards.size());
	}
	
	public void testDrawEmptyDeck() {
		Deck<Card> aDeck = new Deck<Card>();
		aDeck.discard(excalibur);
		aDeck.discard(horse);
		aDeck.discard(sword);
		aDeck.draw();
		assertEquals(2, (int) aDeck.getSize());
	}

}

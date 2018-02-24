package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.Card;
import group52.comp3004.cards.Weapon;
import group52.comp3004.decks.Deck;

public class DeckTest {
	
	Card excalibur = new Weapon("Excalibur", 30);
	Card horse = new Weapon("Horse", 10);
	Card sword = new Weapon("Sword", 10);
	Card dagger = new Weapon("Dagger", 5);
	Card lance = new Weapon("Lance", 20);
	Card battleax = new Weapon("Battle-ax", 15);

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
		Card fdraw = aDeck.drawCard();
		assertEquals(5, (int) aDeck.getSize());
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
	
	public void testDrawEmptyDeck() {
		Deck<Card> aDeck = new Deck<Card>();
		aDeck.discard(excalibur);
		aDeck.discard(horse);
		aDeck.discard(sword);
		aDeck.drawCard();
		assertEquals(2, (int) aDeck.getSize());
	}

}

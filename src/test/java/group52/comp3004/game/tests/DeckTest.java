package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import group52.comp3004.ResourceManager;
import group52.comp3004.cards.Card;
import group52.comp3004.cards.Weapon;
import group52.comp3004.decks.Deck;

public class DeckTest {
	
	public ResourceManager resman = new ResourceManager();
	
	Card excalibur = new Weapon("Excalibur", resman, 30);
	Card horse = new Weapon("Horse", resman, 10);
	Card sword = new Weapon("Sword", resman, 10);
	Card dagger = new Weapon("Dagger", resman, 5);
	Card lance = new Weapon("Lance", resman, 20);
	Card battleax = new Weapon("Battle-ax", resman, 15);

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
		Card discarded = aDeck.discardCard(excalibur);
		assertEquals("Excalibur", (String) discarded.getName());
	}
	
	public void testDrawEmptyDeck() {
		Deck<Card> aDeck = new Deck<Card>();
		aDeck.discardCard(excalibur);
		aDeck.discardCard(horse);
		aDeck.discardCard(sword);
		aDeck.drawCard();
		assertEquals(2, (int) aDeck.getSize());
	}

}

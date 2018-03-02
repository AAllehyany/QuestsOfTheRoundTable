package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import group52.comp3004.ResourceManager;
import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Card;
import group52.comp3004.cards.Weapon;
import group52.comp3004.decks.Deck;
import javafx.embed.swing.JFXPanel;

public class DeckTest {
	
	JFXPanel jfxPanel = new JFXPanel();
	public ResourceManager resman = new ResourceManager();
	
	AdventureCard excalibur = new Weapon("Excalibur", resman, 30);
	AdventureCard horse = new Weapon("Horse", resman, 10);
	AdventureCard sword = new Weapon("Sword", resman, 10);
	AdventureCard dagger = new Weapon("Dagger", resman, 5);
	AdventureCard dagger2 = new Weapon("Dagger", resman, 5);
	AdventureCard lance = new Weapon("Lance", resman, 20);
	Weapon battleax = new Weapon("Battle_Ax", resman, 15);

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
	
	@Test
	public void testDiscardArrayListCard() {
		Deck<AdventureCard> aDeck = new Deck<AdventureCard>(Deck.createAdventureDeck(resman));
		ArrayList<AdventureCard> cards = new ArrayList<AdventureCard>();
		cards.add(excalibur);
		cards.add(sword);
		cards.add(lance);
		aDeck.discardCard(cards);
		assertEquals(3,cards.size());
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

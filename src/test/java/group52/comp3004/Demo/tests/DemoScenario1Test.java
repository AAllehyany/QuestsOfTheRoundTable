package group52.comp3004.Demo.tests;

import java.util.Random;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.CardFactory;
import group52.comp3004.cards.Deed;
import group52.comp3004.cards.EventCard;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Realm;
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public class DemoScenario1Test {
	
	static Random rand = new Random();
	
	static GameState state = new GameState();
	
	static QuestCard bh = CardFactory.createQuest("Boar_Hunt", 2);
	static QuestCard sd = CardFactory.createQuest("Slay_the_Dragon", 3);
	
	static EventCard prosperity = CardFactory.createEvent("Prosperity", new Realm());
	static EventCard deed = CardFactory.createEvent("Deed", new Deed());
	
	static Tourneys camelot = CardFactory.createTourney("Camelot", 3);
	
	static Foe saxons = CardFactory.createFoe("Saxons", 10, 20, "Repel_Saxon_Raiders");
	static Foe boar = CardFactory.createFoe("Boar", 5, 15, "Boar_Hunt");
	static Foe thief = CardFactory.createFoe("Thieves", 5);
	static Foe dragon = CardFactory.createFoe("Dragon", 50, 70, "Slay_the_Dragon");
	static Foe giant = CardFactory.createFoe("Giant", 40);
	
	static Weapon sword = CardFactory.createWeapon("Sword", 10);
	static Weapon dagger = CardFactory.createWeapon("Dagger", 5);
	static Weapon horse = CardFactory.createWeapon("Horse", 10);
	static Weapon axe = CardFactory.createWeapon("Battle_Ax", 15);
	static Weapon excalibur = CardFactory.createWeapon("Excalibur", 30);
	static Weapon lance = CardFactory.createWeapon("Lance", 20);
	
	static Tests valor = CardFactory.createTests("Valor", 3);
	
	static Amour amour = CardFactory.createAmour("Amour", 10, 1);
	
	static Player p1 = new Player(1,state);
	static Player p2 = new Player(2, state);
	static Player p3 = new Player(3, state);
	static Player p4 = new Player(4, state);
	
	public static void scenario1() {
		
		state.addPlayer(p1);
		state.addPlayer(p2);
		state.addPlayer(p3);
		state.addPlayer(p4);
		
		p1.addCardToHand(saxons);
		state.getAdventureDeck().remove(saxons);
		p1.addCardToHand(boar);
		state.getAdventureDeck().remove(boar);
		p1.addCardToHand(sword);
		state.getAdventureDeck().remove(sword);
		p1.addCardToHand(dagger);
		state.getAdventureDeck().remove(dagger);
		while(p1.getHandSize()<12) p1.addCardToHand(state.getAdventureDeck().draw());
		
		p2.addCardToHand(valor);
		state.getAdventureDeck().remove(valor);
		p2.addCardToHand(sword);
		state.getAdventureDeck().remove(sword);
		p2.addCardToHand(giant);
		state.getAdventureDeck().remove(giant);
		p2.addCardToHand(dragon);
		state.getAdventureDeck().remove(dragon);
		while(p2.getHandSize()<12) p2.addCardToHand(state.getAdventureDeck().draw());
		
		p3.addCardToHand(horse);
		state.getAdventureDeck().remove(horse);
		p3.addCardToHand(excalibur);
		state.getAdventureDeck().remove(excalibur);
		while(p3.getHandSize()<12) p3.addCardToHand(state.getAdventureDeck().draw());
		
		p4.addCardToHand(axe);
		state.getAdventureDeck().remove(axe);
		p4.addCardToHand(lance);
		state.getAdventureDeck().remove(lance);
		p4.addCardToHand(thief);
		while(p4.getHandSize()<12) p4.addCardToHand(state.getAdventureDeck().draw());
		
		state.setRevealedCard(bh);
		state.getStoryDeck().remove(bh);
		state.setQuest();
		state.getCurrentQuest().setSponsor(p1);
		
		Stage s1 = new Stage((Foe) p1.discard(saxons));
		Foe b = (Foe) p1.discard(boar);
		b.addWeapon((Weapon) p1.discard(sword));
		b.addWeapon((Weapon) p1.discard(dagger));
		Stage s2 = new Stage(b);
		
		state.getCurrentQuest().addStage(state, s1);
		state.getCurrentQuest().addStage(state, s2);
		
		state.getCurrentQuest().addPlayer(p2);
		
		state.getCurrentQuest().addPlayer(p3);
		
		state.getCurrentQuest().addPlayer(p4);
		
		for(int i=0;i<state.getCurrentQuest().getPlayers().size();i++)
			state.getCurrentQuest().getPlayers().get(i).addCardToHand(state.getAdventureDeck().draw());
		
		while(p2.getHandSize()-12>0) {
			AdventureCard card = p2.getHand().get(rand.nextInt(p2.getHandSize()));
			if(!card.equals(valor) || !card.equals(sword)) state.getAdventureDeck().discard(p2.discard(card));
		}
		
		p3.playToTemp(horse);
		
		p4.playToTemp(axe);
		
		state.getCurrentQuest().playStage(state);
		
		p3.playToTemp(excalibur);
		
		p4.playToTemp(lance);
		
		state.getCurrentQuest().playStage(state);
		
		state.getCurrentQuest().end(state, 0);
		
		while(p1.getHandSize()-12>0) {
			AdventureCard card = p1.getHand().get(rand.nextInt(p1.getHandSize()));
			state.getAdventureDeck().discard(p1.discard(card));
		}
		
		state.nextTurn();
		
		state.setRevealedCard(deed);
		state.getStoryDeck().remove(deed);
		
		state.getStoryDeck().discard(deed);
		
		state.nextTurn();
		
		state.setRevealedCard(prosperity);
		state.getStoryDeck().remove(prosperity);
		
		state.getAdventureDeck().discard(p2.discard(sword));
		p3.playToTemp(amour);
		state.getAdventureDeck().discard(p4.discard(thief));
		
		while(p1.getHandSize()>12) {
			AdventureCard card = p1.getHand().get(rand.nextInt(p1.getHandSize()));
			state.getAdventureDeck().discard(p1.discard(card));
		}
		while(p2.getHandSize()>12) {
			AdventureCard card = p2.getHand().get(rand.nextInt(p2.getHandSize()));
			if(!card.equals(valor)) state.getAdventureDeck().discard(card);
		}
		while(p3.getHandSize()>12) {
			AdventureCard card = p3.getHand().get(rand.nextInt(p3.getHandSize()));
			state.getAdventureDeck().discard(p3.discard(card));
		}
		while(p4.getHandSize()>12) {
			AdventureCard card = p4.getHand().get(rand.nextInt(p4.getHandSize()));
			state.getAdventureDeck().discard(p4.discard(card));
		}
		
		state.getStoryDeck().discard(prosperity);
		
		state.nextTurn();
		
		state.setRevealedCard(camelot);
		state.setTourney();
		
		state.getCurrentTourney().addPlayer(p1);
		state.getCurrentTourney().addPlayer(p2);
		state.getCurrentTourney().addPlayer(p4);
		
		// each player plays some cards
		
		state.nextTurn();
		
		state.setRevealedCard(sd);
		state.getStoryDeck().remove(sd);
		
		state.nextPlayer();

		state.setQuest();
		
		Stage sd1 = new Stage((Tests) p2.discard(valor));
		Stage sd2 = new Stage((Foe) p2.discard(giant));
		Stage sd3 = new Stage((Foe) p2.discard(dragon));
		
		state.getCurrentQuest().addStage(state, sd1);
		state.getCurrentQuest().addStage(state, sd2);
		state.getCurrentQuest().addStage(state, sd3);
		
		state.getCurrentQuest().playStage(state);
		
		// players bid cards (p3 bids twice to win)
		
		state.getCurrentQuest().playStage(state);
	}
}

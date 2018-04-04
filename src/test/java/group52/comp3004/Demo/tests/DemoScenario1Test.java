package group52.comp3004.Demo.tests;

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
	static GameState state = new GameState();
	
	static QuestCard bh = CardFactory.createQuest("Boar_Hunt", 2);
	static QuestCard sd = CardFactory.createQuest("Slay_the_Dragon", 3);
	
	static EventCard prosperity = CardFactory.createEvent("Prosperity", new Realm());
	static EventCard deed = CardFactory.createEvent("Deed", new Deed());
	
	static Tourneys camelot = CardFactory.createTourney("Camelot", 3);
	
	static Foe saxons = CardFactory.createFoe("Saxons", 10, 20, "Repel_Saxon_Raiders");
	static Foe boar = CardFactory.createFoe("Boar", 5, 15, "Boar_Hunt");
	
	static Weapon sword = CardFactory.createWeapon("Sword", 10);
	static Weapon dagger = CardFactory.createWeapon("Dagger", 5);
	static Weapon horse = CardFactory.createWeapon("Horse", 10);
	static 	Weapon axe = CardFactory.createWeapon("Battle_Ax", 15);
	static Weapon excalibur = CardFactory.createWeapon("Excalibur", 30);
	static Weapon lance = CardFactory.createWeapon("Lance", 20);
	
	static Tests valor = CardFactory.createTests("Valor", 3);
	
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
		for(int i=0;i<8;i++) p1.addCardToHand(state.getAdventureDeck().draw());
		
		p2.addCardToHand(valor);
		state.getAdventureDeck().remove(valor);
		for(int i=0;i<11;i++) p2.addCardToHand(state.getAdventureDeck().draw());
		
		p3.addCardToHand(horse);
		state.getAdventureDeck().remove(horse);
		p3.addCardToHand(excalibur);
		state.getAdventureDeck().remove(excalibur);
		for(int i=0;i<10;i++) p3.addCardToHand(state.getAdventureDeck().draw());
		
		p4.addCardToHand(axe);
		state.getAdventureDeck().remove(axe);
		p4.addCardToHand(lance);
		state.getAdventureDeck().remove(lance);
		for(int i=0;i<10;i++) p4.addCardToHand(state.getAdventureDeck().draw());
		
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
		
		p3.playToTemp(horse);
		
		p4.playToTemp(axe);
		
		state.getCurrentQuest().playStage(state);
		
		p3.playToTemp(excalibur);
		
		p4.playToTemp(lance);
	}
}

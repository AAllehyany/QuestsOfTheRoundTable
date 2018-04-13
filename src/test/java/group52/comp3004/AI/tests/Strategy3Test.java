package group52.comp3004.AI.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import group52.comp3004.Strategies.AbstractAI;
import group52.comp3004.Strategies.Strategy3;
import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public class Strategy3Test {
	QuestCard gk = new QuestCard("Green_Knight_Quest", 3);
	QuestCard qh = new QuestCard("Queens_Honor", 4);
	QuestCard bh = new QuestCard("Boar_Hunt", 2);
	QuestCard hg = new QuestCard("Holy_Grail", 5);
	Tourneys camelot = new Tourneys("Camelot", 3);
	Tourneys york = new Tourneys("York", 0);
	
	Foe gknight = new Foe("Green_Knight", 25, 40, "Green_Knight_Quest");
	Foe mordred = new Foe("Mordred", 30);
	Foe giant = new Foe("Giant", 40);
	Foe bknight = new Foe("Black_Knight", 25, 35, "Rescue_Maiden");
	Foe boar = new Foe("Boar", 5, 15, "Boar_Hunt");
	Foe saxons = new Foe("Saxons", 10, 20, "Saxon_Raiders");
	Foe dragon = new Foe("Dragon", 50, 70, "Slay_the_Dragon");
	Weapon excalibur = new Weapon("Excalibur", 30);
	Weapon dagger = new Weapon("Dagger", 5);
	Weapon horse = new Weapon("Horse", 10);
	Weapon sword = new Weapon("Sword", 15);
	Ally kp = new Ally("King_Pellinore", 10, 0, "Questing_Beast_Search", 0, 4);
	Ally sg = new Ally("Sir_Gawain", 10, 0, "Green_Knight_Quest", 20, 0);
	Ally sp = new Ally("Sir_Percival", 5, 0, "Holy_Grail", 20, 0);
	Ally ka = new Ally("King_Arthur", 10, 2);
	Ally op1 = new Ally("OVERPOWERED1", 40, 2);
	Ally op2 = new Ally("OVERPOWERED2", 80, 3);
	Amour a = new Amour("Amour", 10, 1);
	Tests qb = new Tests("Questing_Beast", 4);

	@Test
	public void testStrategy3() {
		GameState state = new GameState();
		AbstractAI s3 = new Strategy3();
		
		Player p1 = new Player(1);
		Player p2 = new Player(2);
		Player p3 = new Player(3);
		Player p4 = new Player(4);
		state.addPlayer(p1);
		state.addPlayer(p2);
		state.addPlayer(p3);
		state.addPlayer(p4);
		
		//Tournament testing
		state.setRevealedCard(camelot);
		state.setTourney();
		p1.addCardToHand(dagger);
		p1.addCardToHand(giant);
		p1.addCardToHand(sword);
		for(int i=0;i<10;i++) p2.addCardToHand(state.getAdventureDeck().draw());
		assertEquals(false, s3.doIParticipateInTournament(state, p1));
		p1.addCardToHand(kp);
		p1.addCardToHand(a);
		p1.addCardToHand(excalibur);
		p1.addCardToHand(sg);
		assert(s3.doIParticipateInTournament(state, p1));
		
		//Test testing
		assertEquals(0, s3.nextBid(state, p1));
		p1.addCardToHand(boar);
		p1.addCardToHand(saxons);
		assertEquals(2, s3.nextBid(state, p1));
		ArrayList<AdventureCard> dCards = s3.discardAfterWinningTest(state, p1);
		assert(dCards.contains(boar));
		assert(dCards.contains(saxons));
		
		//Participate in quest testing
		state.setRevealedCard(gk);
		state.setQuest();
		bknight.addWeapon(dagger);
		bknight.addWeapon(excalibur);
		Stage stage1 = new Stage(saxons);
		Stage stage2 = new Stage(qb);
		Stage stage3 = new Stage(bknight);
		state.getCurrentQuest().addStage(state, stage1);
		state.getCurrentQuest().addStage(state, stage2);
		state.getCurrentQuest().addStage(state, stage3);
		p3.addCardToHand(boar);
		p3.addCardToHand(sword);
		p3.addCardToHand(kp);
		assertEquals(false, s3.doIParticipateInQuest(state, p3));
		p3.addCardToHand(a);
		p3.addCardToHand(horse);
		assertEquals(false, s3.doIParticipateInQuest(state, p3));
		p3.addCardToHand(boar);
		p3.addCardToHand(saxons);
		p3.addCardToHand(sg);
		assert(s3.doIParticipateInQuest(state, p3));
		
		//Sponsor quest testing
		state.setRevealedCard(bh);
		state.setQuest();
		p1.setHand(new ArrayList<AdventureCard>());
		p2.setHand(new ArrayList<AdventureCard>());
		p3.setHand(new ArrayList<AdventureCard>());
		p4.setHand(new ArrayList<AdventureCard>());
		for(int i=0;i<8;i++) p2.addCardToHand(state.getAdventureDeck().draw());
		p1.addCardToHand(boar);
		p1.addCardToHand(saxons);
		p1.addCardToHand(excalibur);
		ArrayList<Stage> quest = s3.createQuest(state, p1);
		assertEquals(saxons, quest.get(0).getFoe());
		assertEquals(boar, quest.get(1).getFoe());
		assertEquals(45, quest.get(1).getTotalPower(state));
		
		//Test playing through quest
		state.setRevealedCard(gk);
		state.setQuest();
		dragon.addWeapon(excalibur);
		dragon.addWeapon(horse);
		dragon.addWeapon(dagger);
		Stage gk1 = new Stage(saxons);
		Stage gk2 = new Stage(giant);
		Stage gk3 = new Stage(dragon);
		state.getCurrentQuest().addStage(state, gk1);
		state.getCurrentQuest().addStage(state, gk2);
		state.getCurrentQuest().addStage(state, gk3);
		p1.addCardToHand(op1);
		p1.addCardToHand(op2);
		p1.addCardToHand(dagger);
		p1.addCardToHand(horse);
		p1.addCardToHand(sword);
		p1.addCardToHand(boar);
		p1.addCardToHand(saxons);
		p1.addCardToHand(excalibur);
		ArrayList<AdventureCard> sCards = s3.playStage(state, p1);
		assert(sCards.contains(op2));
		state.getCurrentQuest().advanceStage();
		sCards = s3.playStage(state, p1);
		assert(sCards.contains(op1));
		state.getCurrentQuest().advanceStage();
		sCards = s3.playStage(state, p1);
		assert(sCards.contains(excalibur));
		assert(sCards.contains(sword));
		assert(sCards.contains(dagger));
		assert(sCards.contains(horse));
	}
}

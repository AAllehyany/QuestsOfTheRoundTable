package group52.comp3004.AI.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import group52.comp3004.ResourceManager;
import group52.comp3004.Strategies.AbstractAI;
import group52.comp3004.Strategies.Strategy1;
import group52.comp3004.Strategies.Strategy2;
import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameState;
import group52.comp3004.game.GameTourney;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;
import javafx.embed.swing.JFXPanel;

public class AITest {
		JFXPanel jfxPanel = new JFXPanel();
		public ResourceManager resman = new ResourceManager();
		
		QuestCard gk = new QuestCard("Green_Knight_Quest", resman, 3);
		QuestCard qh = new QuestCard("Queens_Honor", resman, 4);
		QuestCard bh = new QuestCard("Boar_Hunt", resman, 2);
		QuestCard hg = new QuestCard("Holy_Grail", resman, 5);
		Tourneys camelot = new Tourneys("Camelot", resman, 3);
		Tourneys york = new Tourneys("York", resman, 0);
		
		Foe gknight = new Foe("Green_Knight", resman, 25, 40, "Green_Knight_Quest");
		Foe mordred = new Foe("Mordred", resman, 30);
		Foe giant = new Foe("Giant", resman, 40);
		Foe bknight = new Foe("Black_Knight", resman, 25, 35, "Rescue_Maiden");
		Foe boar = new Foe("Boar", resman, 5, 15, "Boar_Hunt");
		Foe saxons = new Foe("Saxons", resman, 10, 20, "Saxon_Raiders");
		Foe dragon = new Foe("Dragon", resman, 50, 70, "Slay_the_Dragon");
		Weapon excalibur = new Weapon("Excalibur", resman, 30);
		Weapon dagger = new Weapon("Dagger", resman, 5);
		Weapon horse = new Weapon("Horse", resman, 10);
		Weapon sword = new Weapon("Sword", resman, 10);
		Ally kp = new Ally("King_Pellinore", resman, 10, 0, "Questing_Beast_Search", 0, 4);
		Ally sg = new Ally("Sir_Gawain", resman, 10, 0, "Green_Knight_Quest", 20, 0);
		Ally sp = new Ally("Sir_Percival", resman, 5, 0, "Holy_Grail", 20, 0);
		Ally ka = new Ally("King_Arthur", resman, 10, 2);
		Amour a = new Amour("Amour", resman, 10, 1);
		Tests qb = new Tests("Questing_Beast", resman, 4);

		@Test
		public void testOtherEvolve() {
			GameState state = new GameState();
			Player p1 = new Player(0);
			Player p2 = new Player(1);
			Player p3 = new Player(2);
			p1.addShields(4);
			
			state.addPlayer(p2);
			state.addPlayer(p3);
			
			QuestCard qc = new QuestCard("Green_Knight_Quest", resman, 3);
			state.setRevealedCard(qc);
			state.setQuest();
			
			AbstractAI s = new Strategy2();
			assertEquals(false, (boolean) s.anyEvolve(state));
			
			state.addPlayer(p1);
			assertEquals(true, (boolean) s.otherEvolve(state, p2));
		}
		
		@Test
		public void testPlayerMethods() {
			GameState state = new GameState();
			Player p1 = new Player(0);
			state.addPlayer(p1);
			p1.addCardToHand(gknight);
			p1.addCardToHand(mordred);
			p1.addCardToHand(dagger);
			p1.addCardToHand(excalibur);
			p1.addCardToHand(a);
			p1.addCardToHand(sg);
			
			assertEquals(false, (boolean) p1.hasTest());
			p1.addCardToHand(qb);
			assertEquals(true, (boolean) p1.hasTest());
			
			p1.addCardToHand(kp);
			
			p1.sortHand(state);
			assertEquals(sg, (Ally) p1.getHand().get(0));
			assertEquals(kp, (Ally) p1.getHand().get(1));
			assertEquals(a, (Amour) p1.getHand().get(2));
			assertEquals(excalibur, (Weapon) p1.getHand().get(3));
			assertEquals(dagger, (Weapon) p1.getHand().get(4));
			assertEquals(mordred, (Foe) p1.getHand().get(5));
			assertEquals(gknight, (Foe) p1.getHand().get(6));
			assertEquals(qb, (Tests) p1.getHand().get(7));
			
			state.setRevealedCard(gk);
			state.setQuest();
			p1.sortHand(state);
			assertEquals(sg, (Ally) p1.getHand().get(0));
			assertEquals(kp, (Ally) p1.getHand().get(1));
			assertEquals(a, (Amour) p1.getHand().get(2));
			assertEquals(excalibur, (Weapon) p1.getHand().get(3));
			assertEquals(dagger, (Weapon) p1.getHand().get(4));
			assertEquals(gknight, (Foe) p1.getHand().get(5));
			assertEquals(mordred, (Foe) p1.getHand().get(6));
			assertEquals(qb, (Tests) p1.getHand().get(7));
			
			assertEquals(true, (boolean) p1.getDuplicates().isEmpty());
			
			p1.addCardToHand(excalibur);
			
			assertEquals(1, (int) p1.getDuplicates().size());
			assertEquals(excalibur, (Weapon) p1.getDuplicates().get(0));
			
			assertEquals(2, (int) p1.countFoes());
			assertEquals(0, (int) p1.countFoes(5));
			assertEquals(1, (int) p1.countFoes(30));
			
			assertEquals(2, (int) p1.getFoes(50).size());
			assertEquals(1, (int) p1.getFoes(30).size());
			
			assertEquals(2, (int) p1.getUniqueFoes(state).size());
			assertEquals(2, (int) p1.numUniqueFoes(state));
			
			p1.addCardToHand(giant);
			assertEquals(2,  (int) p1.getUniqueFoes(state).size());
			assertEquals(2, (int) p1.numUniqueFoes(state));
			
			p1.addCardToHand(bknight);
			assertEquals(3, (int) p1.getUniqueFoes(state).size());
			assertEquals(3, (int) p1.numUniqueFoes(state));
			
			assertEquals(10, (int) a.getBp());
		}
		
		@Test
		public void testStrategy1() {
			GameState state = new GameState();
			AbstractAI s1 = new Strategy1();
			
			Player p1 = new Player(0);
			Player p2 = new Player(1);
			Player p3 = new Player(2);
			Player p4 = new Player(3);
			state.addPlayer(p1);
			state.addPlayer(p2);
			
			// Tournament Testing
			state.setRevealedCard(york);
			assertEquals(false, (boolean) s1.doIParticipateInTournament(state));
			state.addPlayer(p3);
			state.addPlayer(p4);
			p1.addShields(4);
			assertEquals(true, (boolean) s1.doIParticipateInTournament(state));
			
			state.setTourney();
			GameTourney t = state.getCurrentTourney();
			t.addPlayer(p2);
			t.addPlayer(p3);
			p2.addCardToHand(dagger);
			p2.addCardToHand(mordred);
			p2.addCardToHand(boar);
			p2.addCardToHand(kp);
			p2.addCardToHand(excalibur);
			ArrayList<AdventureCard> tCards = s1.playTourney(state, p2);
			assert(tCards.isEmpty());
			p2.addCardToHand(dagger);
			p2.addCardToHand(dagger);
			tCards = s1.playTourney(state, p2);
			assertEquals(1, tCards.size());
			assertEquals(6, p2.getHand().size());
			assert(tCards.contains(dagger));
			p3.addShields(4);
			tCards = s1.playTourney(state, p2);
			assertEquals(3, tCards.size());
			assert(tCards.contains(kp));
			assert(tCards.contains(dagger));
			assert(tCards.contains(excalibur));
			assertEquals(3, p2.getHand().size());
			
			// Test Testing
			p1.addCardToHand(gknight);
			p1.addCardToHand(mordred);
			p1.addCardToHand(giant);
			p1.addCardToHand(boar);
			p1.addCardToHand(dagger);
			p1.addCardToHand(kp);
			
			assertEquals(1, s1.nextBid(state, p1));
			p1.setHand(new ArrayList<AdventureCard>());
			p1.addCardToHand(gknight);
			p1.addCardToHand(dagger);
			p1.addCardToHand(kp);
			p1.addCardToHand(a);
			assertEquals(0, s1.nextBid(state, p1));
			
			p1.addCardToHand(boar);
			p1.addCardToHand(saxons);
			ArrayList<AdventureCard> dCard = s1.discardAfterWinningTest(state, p1);
			assertEquals(boar, dCard.get(0));
			assertEquals(saxons, dCard.get(1));
			
			
			// Participate in quest testing
			state.setRevealedCard(hg);
			state.setQuest();
			assertFalse(s1.doIParticipateInQuest(state, p1));
			p1.addCardToHand(sg);
			p1.addCardToHand(dagger);
			p1.addCardToHand(dagger);
			p1.addCardToHand(dagger);
			p1.addCardToHand(dagger);
			p1.addCardToHand(dagger);
			p1.addCardToHand(dagger);
			p1.addCardToHand(dagger);
			p1.addCardToHand(dagger);
			p1.addCardToHand(dagger);
			p1.addCardToHand(dagger);
			p1.addCardToHand(dagger);
			assertFalse(s1.doIParticipateInQuest(state, p1));
			p1.addCardToHand(excalibur);
			p1.addCardToHand(excalibur);
			p1.addCardToHand(ka);
			p1.addCardToHand(sp);
			p1.addCardToHand(boar);
			assert(s1.doIParticipateInQuest(state, p1));
			
			// Sponsor quest testing
			p1.setHand(new ArrayList<AdventureCard>());
			p2.setHand(new ArrayList<AdventureCard>());
			p3.setHand(new ArrayList<AdventureCard>());
			p4.setHand(new ArrayList<AdventureCard>());
			
			p1.addShields(4);
			state.setRevealedCard(bh);
			state.setQuest();
			assertFalse(s1.doISponsorQuest(state, p2));
			p2.addCardToHand(dagger);
			p2.addCardToHand(mordred);
			p2.addCardToHand(excalibur);
			p2.addCardToHand(giant);
			p2.addCardToHand(dragon);
			assertFalse(s1.doISponsorQuest(state, p2));
			p1.removeShield(20);
			p3.removeShield(20);
			p4.removeShield(20);
			assertEquals(true, s1.doISponsorQuest(state, p2));
			ArrayList<Stage> quest = s1.createQuest(state, p2);
			assertEquals(2, quest.size());
			assertEquals(dragon, quest.get(1).getFoe());
			assertEquals(giant, quest.get(0).getFoe());
			assertEquals(3, p2.getHand().size());
			
			state.setRevealedCard(hg);
			state.setQuest();
			p1.addShields(6);
			p2.addShields(6);
			p3.addShields(6);
			p4.addShields(6);
			p1.setHand(new ArrayList<AdventureCard>());
			p1.addCardToHand(dagger);
			p1.addCardToHand(bknight);
			p1.addCardToHand(excalibur);
			p1.addCardToHand(excalibur);
			p1.addCardToHand(dagger);
			p1.addCardToHand(boar);
			p1.addCardToHand(mordred);
			p1.addCardToHand(qb);
			p1.addCardToHand(giant);
			p1.addCardToHand(dragon);
			assertEquals(10, p1.getHand().size());
			assert(s1.doISponsorQuest(state, p1));
			quest = s1.createQuest(state, p1);
			assertEquals(5, quest.size());
			assertEquals(dragon, quest.get(4).getFoe());
			assert(quest.get(3).isTestStage());
			assertEquals(giant, quest.get(2).getFoe());
			assertEquals(45, quest.get(2).getFoe().getBp(state));
			assertEquals(bknight, quest.get(1).getFoe());
			assertEquals(35, quest.get(1).getFoe().getBp(state));
			assertEquals(mordred, quest.get(0).getFoe());
			
			// Test playing through quest
			state.setRevealedCard(bh);
			state.setQuest();
			p1.setHand(new ArrayList<AdventureCard>());
			p2.setHand(new ArrayList<AdventureCard>());
			p3.setHand(new ArrayList<AdventureCard>());
			p4.setHand(new ArrayList<AdventureCard>());
			p2.addCardToHand(a);
			p2.addCardToHand(a);
			p2.addCardToHand(kp);
			p2.addCardToHand(sp);
			p2.addCardToHand(excalibur);
			ArrayList<AdventureCard> stageCards = s1.playStage(state, p2);
			assertEquals(2, stageCards.size());
			assertEquals(a, stageCards.get(0));
			assertEquals(kp, stageCards.get(1));
			p2.addTemp(a);
			p2.addField(kp);
			state.getCurrentQuest().advanceStage();
			stageCards = s1.playStage(state, p2);
			assertEquals(2, stageCards.size());
			assert(stageCards.contains(sp));
			assert(stageCards.contains(excalibur));
			p3.addCardToHand(a);
			p3.addCardToHand(excalibur);
			p3.addCardToHand(dagger);
			p3.addCardToHand(dagger);
			p3.addCardToHand(sg);
			stageCards = s1.playStage(state, p3);
			assertEquals(4, stageCards.size());
			assert(stageCards.contains(sg));
			assert(stageCards.contains(excalibur));
			assert(stageCards.contains(a));
			assert(stageCards.contains(dagger));
			
			state.setRevealedCard(gk);
			state.setQuest();
			p1.setHand(new ArrayList<AdventureCard>());
			p1.addCardToHand(a);
			p1.addCardToHand(kp);
			p1.addCardToHand(sg);
			p1.addCardToHand(dagger);
			p1.addCardToHand(dagger);
			p1.addCardToHand(excalibur);
			p1.addCardToHand(horse);
			stageCards = s1.playStage(state, p1);
			assertEquals(2, stageCards.size());
			assert(stageCards.contains(a));
			assert(stageCards.contains(sg));
			state.getCurrentQuest().advanceStage();
			stageCards = s1.playStage(state, p1);
			assertEquals(2, stageCards.size());
			assert(stageCards.contains(kp));
			assert(stageCards.contains(dagger));
			state.getCurrentQuest().advanceStage();
			stageCards = s1.playStage(state, p1);
			assertEquals(3, stageCards.size());
			assert(stageCards.contains(horse));
			assert(stageCards.contains(dagger));
			assert(stageCards.contains(excalibur));
		}
		
		@Test
		public void testStrategy2() {
			GameState state = new GameState();
			AbstractAI s2 = new Strategy2();
			
			Player p1 = new Player(0);
			Player p2 = new Player(1);
			Player p3 = new Player(2);
			Player p4 = new Player(3);
			state.addPlayer(p1);
			state.addPlayer(p2);
			state.addPlayer(p3);
			state.addPlayer(p4);
			p1.addShields(4);
			
			// Tournament Testing
			state.setRevealedCard(camelot);
			assertEquals(true, (boolean) s2.doIParticipateInTournament(state));
			
			p1.addCardToHand(gknight);
			p1.addCardToHand(mordred);
			p1.addCardToHand(dagger);
			p1.addCardToHand(excalibur);
			p1.addCardToHand(a);
			p1.addCardToHand(sg);
			
			ArrayList<AdventureCard> tCards1 = s2.playTourney(state, p1);
			assertEquals(excalibur, tCards1.get(0));
			assertEquals(a, tCards1.get(1));
			assertEquals(sg, tCards1.get(2));
			
			p2.addCardToHand(qb);
			p2.addCardToHand(gknight);
			p2.addCardToHand(dagger);
			p2.addCardToHand(excalibur);
			ArrayList<AdventureCard> tCards2 = s2.playTourney(state, p2);
			assertEquals(excalibur, tCards2.get(0));
			assertEquals(dagger, tCards2.get(1));
			
			state.setRevealedCard(gk);

			// Test Testing
			ArrayList<AdventureCard> dCards;
			p1.addCardToHand(boar);
			assertEquals(1, s2.nextBid(state, p1));
			dCards = s2.discardAfterWinningTest(state, p1);
			assertEquals(boar, dCards.get(0));
			p1.addCardToHand(boar);
			p1.addCardToHand(dagger);
			assertEquals(2, s2.nextBid(state, p1));
			dCards = s2.discardAfterWinningTest(state, p1);
			assert(dCards.contains(dagger));
			assert(dCards.contains(boar));
			
			// Sponsor Quest Testing
			state.setRevealedCard(qh);
			state.setQuest();
			
			p3.addCardToHand(gknight);
			p3.addCardToHand(mordred);
			p3.addCardToHand(dagger);
			p3.addCardToHand(excalibur);
			p3.addCardToHand(a);
			p3.addCardToHand(sg);
			p3.addCardToHand(kp);
			assertFalse(s2.doISponsorQuest(state, p3));
			
			p1.addShields(-4);
			assertFalse(s2.doISponsorQuest(state, p3));
			
			p3.addCardToHand(qb);
			assertFalse(s2.doISponsorQuest(state, p3));
			p3.addCardToHand(bknight);
			assertEquals(true, (boolean) s2.doISponsorQuest(state, p3));
			
			// Participate in quest testing
			p4.addCardToHand(saxons);
			p4.addCardToHand(excalibur);
			p4.addCardToHand(a);
			p4.addCardToHand(kp);
			assertFalse(s2.doIParticipateInQuest(state, p4));
			p4.addCardToHand(sg);
			p4.addCardToHand(dagger);
			assertFalse(s2.doIParticipateInQuest(state, p4));
			p4.addCardToHand(boar);
			assertEquals(true, (boolean) s2.doIParticipateInQuest(state, p4));
			p4.setHand(new ArrayList<AdventureCard>());
			for(int i=0;i<10;i++) p4.addCardToHand(dagger);
			assertFalse(s2.doIParticipateInQuest(state, p4));
			
			// Create Quest Testing	
			p1.setHand(new ArrayList<AdventureCard>());
			p2.setHand(new ArrayList<AdventureCard>());
			p3.setHand(new ArrayList<AdventureCard>());
			p4.setHand(new ArrayList<AdventureCard>());

			ArrayList<AdventureCard> qcard = new ArrayList<AdventureCard>();
			qcard.add(boar);
			qcard.add(saxons);
			qcard.add(bknight);
			qcard.add(dagger);
			qcard.add(gknight);
			
			p1.setHand(qcard);
			state.setRevealedCard(gk);
			state.setQuest();
			ArrayList<Stage> stages;
			stages = s2.createQuest(state, p1);
			assertEquals(3, (int) stages.size());
			assertEquals(boar, stages.get(0).getFoe());
			assertEquals(saxons, stages.get(1).getFoe());
			assertEquals(gknight, stages.get(2).getFoe());
			
			p1.setHand(qcard);
			p1.addCardToHand(qb);
			p1.sortHand(state);
			assertEquals(boar, p1.getHand().get(4));
			stages = s2.createQuest(state, p1);
			assertEquals(boar, stages.get(0).getFoe());
			assert(stages.get(1).isTestStage());
			assertEquals(gknight, stages.get(2).getFoe());
			
			state.setRevealedCard(bh);
			state.setQuest();
			p1.setHand(qcard);
			p1.addCardToHand(qb);
			p1.addCardToHand(horse);
			p1.addCardToHand(horse);
			stages = s2.createQuest(state, p1);
			assert(stages.get(0).isTestStage());
			assertEquals(bknight, stages.get(1).getFoe());
			assertEquals(40, stages.get(1).getFoe().getBp(state));
			assertEquals(5, p1.getHand().size());
			
			state.setRevealedCard(hg);
			state.setQuest();
			p1.addShields(6);
			p2.addShields(6);
			p3.addShields(6);
			p4.addShields(6);
			p1.setHand(qcard);
			p1.addCardToHand(mordred);
			p1.addCardToHand(qb);
			p1.addCardToHand(excalibur);
			p1.addCardToHand(giant);
			p1.addCardToHand(dragon);
			assert(s2.doISponsorQuest(state, p1));
			stages = s2.createQuest(state, p1);
			assertEquals(boar, stages.get(0).getFoe());
			assertEquals(saxons, stages.get(1).getFoe());
			assertEquals(mordred, stages.get(2).getFoe());
			assert(stages.get(3).isTestStage());
			assertEquals(dragon, stages.get(4).getFoe());
			
			// Test playing through quest
			state.setRevealedCard(bh);
			state.setQuest();
			p1.setHand(new ArrayList<AdventureCard>());
			p2.setHand(new ArrayList<AdventureCard>());
			p3.setHand(new ArrayList<AdventureCard>());
			p4.setHand(new ArrayList<AdventureCard>());
			p1.addCardToHand(a);
			p1.addCardToHand(dagger);
			p1.addCardToHand(excalibur);
			ArrayList<AdventureCard> stageCards = s2.playStage(state, p1);
			assertEquals(1, stageCards.size());
			assert(stageCards.contains(a));
			p1.addTemp(stageCards);
			p1.clearTemp();
			state.getCurrentQuest().advanceStage();
			stageCards = s2.playStage(state, p1);
			for(int i=0;i<stageCards.size();i++) System.out.println(stageCards.get(i).getName());
			assertEquals(2, stageCards.size());
			assert(stageCards.contains(dagger));
			assert(stageCards.contains(excalibur));
			
			state.setRevealedCard(hg);
			state.setQuest();
			p2.addCardToHand(a);
			p2.addCardToHand(kp);
			p2.addCardToHand(sg);
			p2.addCardToHand(horse);
			p2.addCardToHand(horse);
			p2.addCardToHand(sword);
			p2.addCardToHand(sword);
			p2.addCardToHand(dagger);
			p2.addCardToHand(dagger);
			stageCards = s2.playStage(state, p2);
			assertEquals(1, stageCards.size());
			assert(stageCards.contains(a));
			p2.addTemp(stageCards);
			p2.clearTemp();
			state.getCurrentQuest().advanceStage();
			stageCards = s2.playStage(state, p2);
			assertEquals(1, stageCards.size());
			assert(stageCards.contains(kp));
			p2.addTemp(stageCards);
			p2.clearTemp();
			state.getCurrentQuest().advanceStage();
			stageCards = s2.playStage(state, p2);
			assertEquals(1, stageCards.size());
			assert(stageCards.contains(sg));
			p2.addTemp(stageCards);
			p2.clearTemp();
			state.getCurrentQuest().advanceStage();
			stageCards = s2.playStage(state, p2);
			assertEquals(2, stageCards.size());
			assert(stageCards.contains(dagger));
			assert(stageCards.contains(sword));
			p2.addTemp(stageCards);
			p2.clearTemp();
			state.getCurrentQuest().advanceStage();
			state.getCurrentQuest().advanceStage();
			stageCards = s2.playStage(state, p2);
			assertEquals(3, stageCards.size());
			assert(stageCards.contains(dagger));
			assert(stageCards.contains(sword));
			assert(stageCards.contains(horse));
		}
}

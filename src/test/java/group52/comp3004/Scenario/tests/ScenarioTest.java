package group52.comp3004.Scenario.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import group52.comp3004.ResourceManager;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
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
import javafx.embed.swing.JFXPanel;

public class ScenarioTest {
	JFXPanel jfxPanel = new JFXPanel();
	public ResourceManager resman = new ResourceManager();
	
	QuestCard gk = new QuestCard("Green_Knight_Quest", resman, 3);
	QuestCard qh = new QuestCard("Queens_Honor", resman, 4);
	QuestCard bh = new QuestCard("Boar_Hunt", resman, 2);
	QuestCard hg = new QuestCard("Holy_Grail", resman, 5);
	Tourneys camelot = new Tourneys("Camelot", resman, 3);
	Tourneys york = new Tourneys("York", resman, 0);
	EventCard p = new EventCard("Prosperity", resman, new Realm());
	EventCard cd = new EventCard("Chivalrous_Deed", resman, new Deed());
	
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
	Weapon axe = new Weapon("Battle_Ax", resman, 30);
	Weapon lance = new Weapon("Lance", resman, 20);
	Ally kp = new Ally("King_Pellinore", resman, 10, 0, "Questing_Beast_Search", 0, 4);
	Ally sg = new Ally("Sir_Gawain", resman, 10, 0, "Green_Knight_Quest", 20, 0);
	Ally sp = new Ally("Sir_Percival", resman, 5, 0, "Holy_Grail", 20, 0);
	Ally ka = new Ally("King_Arthur", resman, 10, 2);
	Amour a = new Amour("Amour", resman, 10, 1);
	Tests qb = new Tests("Questing_Beast", resman, 4);
	
	@Test
	public void testScenario1() {
		GameState state = new GameState();
		Player p1 = new Player(0);
		Player p2 = new Player(1);
		Player p3 = new Player(2);
		Player p4 = new Player(3);
		state.addPlayer(p1);
		state.addPlayer(p2);
		state.addPlayer(p3);
		state.addPlayer(p4);
		p1.setGame(state);
		p2.setGame(state);
		p3.setGame(state);
		p4.setGame(state);
		p1.addCardToHand(saxons);
		p1.addCardToHand(sword);
		p1.addCardToHand(boar);
		p1.addCardToHand(dagger);
		for(int i=0;i<8;i++) {
			p1.addCardToHand(state.getAdventureDeck().drawCard());
		}
		assertEquals(12, p1.getHand().size());
		for(int i=0;i<12;i++) {
			p2.addCardToHand(state.getAdventureDeck().drawCard());
		}
		assertEquals(12, p2.getHand().size());
		p3.addCardToHand(horse);
		p3.addCardToHand(excalibur);
		for(int i=0;i<10;i++) {
			p3.addCardToHand(state.getAdventureDeck().drawCard());
		}
		assertEquals(12, p3.getHand().size());
		p4.addCardToHand(axe);
		p4.addCardToHand(lance);
		for(int i=0;i<10;i++) {
			p4.addCardToHand(state.getAdventureDeck().drawCard());
		}
		assertEquals(12, p4.getHand().size());
		
		state.setRevealedCard(bh);
		state.setQuest();
		state.getCurrentQuest().setSponsor(p1);
		Stage s1 = new Stage(saxons);
		p1.getHand().remove(saxons);
		Foe b = boar;
		b.addWeapon(dagger);
		b.addWeapon(sword);
		p1.getHand().remove(boar);
		p1.getHand().remove(dagger);
		p1.getHand().remove(sword);
		assertEquals(8, p1.getHand().size());
		Stage s2 = new Stage(b);
		state.getCurrentQuest().addStage(s1);
		state.getCurrentQuest().addStage(s2);
		assertEquals(10, state.getCurrentQuest().getStage(0).getTotalPower(state));
		assertEquals(30, state.getCurrentQuest().getStage(1).getTotalPower(state));
		state.getCurrentQuest().addPlayer(p2);
		state.getCurrentQuest().addPlayer(p3);
		state.getCurrentQuest().addPlayer(p4);
		assertEquals(3, state.getCurrentQuest().getPlayers().size());
		p3.playToTemp(horse);
		p4.playToTemp(axe);
		state.getCurrentQuest().playStage(state);
		assertEquals(2, state.getCurrentQuest().getPlayers().size());
		for(int i=0;i<state.getCurrentQuest().getPlayers().size();i++) {
			state.getCurrentQuest().getPlayers().get(i).addCardToHand(state.getAdventureDeck().drawCard());
		}
		assertEquals(12, p3.getHand().size());
		assertEquals(12, p4.getHand().size());
		p3.playToTemp(excalibur);
		p4.playToTemp(lance);
		assertEquals(30, state.getCurrentQuest().getStage(1).getTotalPower(state));
		assertEquals(35, (int) p3.getBattlePoints(state));
		assertEquals(25, (int) p4.getBattlePoints(state));
		state.getCurrentQuest().playStage(state);
		assertEquals(1, state.getCurrentQuest().getPlayers().size());
		for(int i=0;i<state.getCurrentQuest().getPlayers().size();i++) {
			state.getCurrentQuest().getPlayers().get(i).addCardToHand(state.getAdventureDeck().drawCard());
		}
		state.getCurrentQuest().end(state, 0);
		assertEquals(10, (int) p1.getShields());
		assertEquals(10, (int) p2.getShields());
		assertEquals(12, (int) p3.getShields());
		assertEquals(10, (int) p4.getShields());
		assertEquals(14, p1.getHand().size());
	}
	
}

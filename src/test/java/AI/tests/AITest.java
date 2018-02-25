package AI.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import group52.comp3004.ResourceManager;
import group52.comp3004.Strategies.AbstractAI;
import group52.comp3004.Strategies.Strategy2;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Weapon;
import group52.comp3004.cards.Tests;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;
import javafx.embed.swing.JFXPanel;

public class AITest {
		JFXPanel jfxPanel = new JFXPanel();
		public ResourceManager resman = new ResourceManager();
		
		QuestCard gk = new QuestCard("Green_Knight", resman, 3);
		Foe gknight = new Foe("Green_Knight", resman, 25, 40, "Green_Knight");
		Foe mordred = new Foe("Mordred", resman, 30);
		Foe giant = new Foe("Giant", resman, 40);
		Foe bknight = new Foe("Black_Knight", resman, 25, 35, "Rescue_Maiden");
		Weapon excalibur = new Weapon("Excalibur", resman, 30);
		Weapon dagger = new Weapon("Dagger", resman, 5);
		Ally kp = new Ally("King_Pellinore", resman, 10, 0, "Questing_Beast", 0, 4);
		Ally sg = new Ally("Sir_Gawain", resman, 10, 0, "Green_Knight", 20, 0);
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
			
			QuestCard qc = new QuestCard("Green_Knight", resman, 3);
			state.setRevealedCard(qc);
			GameQuest q = new GameQuest(qc, p2);
			state.setQuest();
			
			AbstractAI s = new Strategy2();
			assertEquals(false, (boolean) s.otherEvolve(state));
			
			state.addPlayer(p1);
			assertEquals(true, (boolean) s.otherEvolve(state));
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
			assertEquals(115, (int) p1.getBPInHand(state));
		}
}

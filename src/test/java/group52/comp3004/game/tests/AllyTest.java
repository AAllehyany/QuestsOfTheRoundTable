package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public class AllyTest {	
	@Test
	public void testAllyStats() {
		GameState state = new GameState();
		Ally a1 = new Ally("Sir_Gawain", 10, 0, "Green_Knight_Quest", 20, 0);
		assertEquals(0, (int) a1.getBids(state));
		assertEquals(10, (int) a1.getBp(state));
		Ally a2 = new Ally("King_Arthur", 10, 2);
		assertEquals(2, (int) a2.getBids(state));
		assertEquals(10, (int) a2.getBp(state));
	}

	@Test
	public void testQuestBonus() {
		Player p = new Player(0);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p);
		QuestCard q = new QuestCard("Green_Knight_Quest", 4);
		Ally a1 = new Ally("Sir_Gawain", 10, 0, "Green_Knight_Quest", 20, 0);
		Ally a2 = new Ally("King_Arthur", 10, 2);
		Ally a3 = new Ally("King_Pellinore", 10, 0, "Questing_Beast_Search", 0, 4);
		GameState state = new GameState(players);
		state.setRevealedCard(q);
		state.setQuest();
		assertEquals(true, (boolean) a1.bonusSatisfied(state));
		assertEquals(false, (boolean) a2.bonusSatisfied(state));
		assertEquals(false, (boolean) a3.bonusSatisfied(state));
		assertEquals(30, (int) a1.getBp(state));
		assertEquals(10, (int) a2.getBp(state));
		assertEquals(2, (int) a2.getBids(state));
		assertEquals(0, (int) a3.getBids(state));
		
		QuestCard hg = new QuestCard("Holy_Grail", 5);
		Ally sp = new Ally("Sir_Percival", 5, 0, "Holy_Grail", 20, 0);
		state.setRevealedCard(hg);
		state.setQuest();
		assertEquals(25, (int) sp.getBp(state));
	}
	
	@Test
	public void testAllyBonus() {
		Player p1 = new Player(0);
		ArrayList<Player> players = new ArrayList<Player> ();
		players.add(p1);
		Ally a1 = new Ally("Sir_Tristan", 10, 0, "Queen_Iseult", 20, 0);
		Ally a2 = new Ally("Queen_Iseult", 0, 2, "Sir_Tristan", 0, 2);
		p1.addCardToHand(a1);
		p1.addCardToHand(a2);
		p1.addField(a1);
		p1.addField(a2);
		GameState state = new GameState(players);
		assertEquals(true, (boolean) a1.bonusSatisfied(state));
		assertEquals(35, (int) p1.getBattlePoints(state));
	}
	
	@Test
	public void testMerlin() {
		GameState state = new GameState();
		QuestCard q = new QuestCard("Enchanted_Forest", 3);
		Player p1 = new Player(1);
		Player p2 = new Player(2);
		state.addPlayer(p1);
		state.addPlayer(p2);
		state.setRevealedCard(q);
		state.setQuest();
		state.nextPlayer();
		state.joinQuest();
		GameQuest gq = state.getCurrentQuest();
		Ally merlin = new Ally("Merlin");
		p2.addField(merlin);
		Foe giant = new Foe("Giant", 40);
		Foe saxons = new Foe("Saxons", 10, 20, "Saxon_Raiders");
		Foe dragon = new Foe("Dragon", 50, 70, "Slay_the_Dragon");
		Weapon dagger = new Weapon("Dagger", 5);
		saxons.addWeapon(dagger);
		Stage s1 = new Stage(saxons);
		Stage s2 = new Stage(giant);
		Stage s3 = new Stage(dragon);
		gq.addStage(s1);
		gq.addStage(s2);
		gq.addStage(s3);
		ArrayList<AdventureCard> s1cards = new ArrayList<AdventureCard>();
		s1cards.add(saxons);
		s1cards.add(dagger);
		ArrayList<AdventureCard> mcards;
		mcards = merlin.StartMerlinSpecial(state, 0);
		assertEquals(2, (int) mcards.size());
		for(int i=0;i<mcards.size();i++) {
			assertEquals(s1cards.get(i), (AdventureCard) mcards.get(i));
			assertEquals(true, (boolean) mcards.get(i).getFlipped());
		}
		merlin.EndMerlinSpecial(mcards);
		for(int i=0;i<mcards.size();i++) {
			assertEquals(false, (boolean) mcards.get(i).getFlipped());
		}
	}

}

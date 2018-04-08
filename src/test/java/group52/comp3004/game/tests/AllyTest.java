package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.CardFactory;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public class AllyTest {	
	
	QuestCard gk = CardFactory.createQuest("Green_Knight_Quest", 4);
	QuestCard qb = CardFactory.createQuest("Questing_Beast_Search", 4);
	QuestCard hg = CardFactory.createQuest("Holy_Grail", 5);
	QuestCard qh = CardFactory.createQuest("Queens_Honor", 4);
	
	Ally galahad = CardFactory.createAlly("Sir_Galahad", 15, 0);
	Ally sl = CardFactory.createAlly("Sir_Lancelot", 15, 0, "Queens_Honor", 25, 0);
	Ally ka = CardFactory.createAlly("King_Arthur", 10, 2);
	Ally st = CardFactory.createAlly("Sir_Tristan", 10, 0, "Queen_Iseult", 20, 0);
	Ally kp = CardFactory.createAlly("King_Pellinore", 10, 0, "Questing_Beast_Search", 10, 4);
	Ally gawain = CardFactory.createAlly("Sir_Gawain", 10, 0, "Green_Knight_Quest", 20, 0);
	Ally qg = CardFactory.createAlly("Queen_Guinever", 0, 3);
	Ally qi = CardFactory.createAlly("Queen_Iseult", 0, 2, "Sir_Tristan", 0, 4);
	Ally sp = CardFactory.createAlly("Sir_Percival", 5, 0, "Holy_Grail", 20, 0);
	Ally m = CardFactory.createAlly("Merlin");
	
	
	@Test
	public void testGawain() {
		GameState state = new GameState();
		Player p1 = new Player(1);
		state.addPlayer(p1);
		p1.addCardToHand(gawain);
		p1.addField(gawain);
		assertEquals(10, (int) gawain.getBp(state));
		assertEquals(15, (int) p1.getBattlePoints(state));
		assertEquals(0, (int) gawain.getBids(state));
		assertEquals(0, (int) p1.getBidPoints(state));
		state.setRevealedCard(gk);
		state.setQuest();
		assertEquals(20, (int) gawain.getBp(state));
		assertEquals(25, (int) p1.getBattlePoints(state));
		assertEquals(0, (int) gawain.getBids(state));
		assertEquals(0, (int) p1.getBidPoints(state));
	}
	
	@Test
	public void testPellinore() {
		GameState state = new GameState();
		Player p1 = new Player(1);
		state.addPlayer(p1);
		p1.addCardToHand(kp);
		p1.addField(kp);
		assertEquals(10, (int) kp.getBp(state));
		assertEquals(15, (int) p1.getBattlePoints(state));
		assertEquals(0, (int) kp.getBids(state));
		assertEquals(0, (int) p1.getBidPoints(state));
		state.setRevealedCard(qb);
		state.setQuest();
		assertEquals(10, (int) kp.getBp(state));
		assertEquals(15, (int) p1.getBattlePoints(state));
		assertEquals(4, (int) kp.getBids(state));
		assertEquals(4, (int) p1.getBidPoints(state));
	}
	
	@Test
	public void testPercival() {
		GameState state = new GameState();
		Player p1 = new Player(1);
		state.addPlayer(p1);
		p1.addCardToHand(sp);
		p1.addField(sp);
		assertEquals(5, (int) sp.getBp(state));
		assertEquals(10, (int) p1.getBattlePoints(state));
		assertEquals(0, (int) sp.getBids(state));
		assertEquals(0, (int) p1.getBidPoints(state));
		state.setRevealedCard(hg);
		state.setQuest();
		assertEquals(20, (int) sp.getBp(state));
		assertEquals(25, (int) p1.getBattlePoints(state));
		assertEquals(0, (int) sp.getBids(state));
		assertEquals(0, (int) p1.getBidPoints(state));
	}
	
	@Test
	public void testTristan() {
		GameState state = new GameState();
		Player p1 = new Player(1);
		Player p2 = new Player(2);
		state.addPlayer(p1);
		state.addPlayer(p2);
		p1.addCardToHand(st);
		p1.addField(st);
		assertEquals(10, (int) st.getBp(state));
		assertEquals(15, (int) p1.getBattlePoints(state));
		assertEquals(0, (int) st.getBids(state));
		assertEquals(0, (int) p1.getBidPoints(state));
		p2.addCardToHand(qi);
		p2.addField(qi);
		assert(st.bonusSatisfied(state));
		assertEquals(20, (int) st.getBp(state));
		assertEquals(25, (int) p1.getBattlePoints(state));
		assertEquals(0, (int) st.getBids(state));
		assertEquals(0, (int) p1.getBidPoints(state));
	}
	
	@Test
	public void testIseult() {
		GameState state = new GameState();
		Player p3 = new Player(3);
		Player p4 = new Player(4);
		state.addPlayer(p3);
		state.addPlayer(p4);
		p3.addCardToHand(qi);
		p3.addField(qi);
		assertEquals(0, (int) qi.getBp(state));
		assertEquals(5, (int) p3.getBattlePoints(state));
		assertEquals(2, (int) qi.getBids(state));
		assertEquals(2, (int) p3.getBidPoints(state));
		p4.addCardToHand(st);
		p4.addField(st);
		assert(qi.bonusSatisfied(state));
		assertEquals(0, (int) qi.getBp(state));
		assertEquals(5, (int) p3.getBattlePoints(state));
		assertEquals(4, (int) qi.getBids(state));
		assertEquals(4, (int) p3.getBidPoints(state));
	}
	
	@Test
	public void testGuinevere() {
		GameState state = new GameState();
		Player p1 = new Player(1);
		state.addPlayer(p1);
		p1.addCardToHand(qg);
		p1.addField(qg);
		assertEquals(0, (int) qg.getBp(state));
		assertEquals(5, (int) p1.getBattlePoints(state));
		assertEquals(3, (int) qg.getBids(state));
		assertEquals(3, (int) p1.getBidPoints(state));
	}
	
	@Test
	public void testArthur() {
		GameState state = new GameState();
		Player p1 = new Player(1);
		state.addPlayer(p1);
		p1.addCardToHand(ka);
		p1.addField(ka);
		assertEquals(10, (int) ka.getBp(state));
		assertEquals(15, (int) p1.getBattlePoints(state));
		assertEquals(2, (int) ka.getBids(state));
		assertEquals(2, (int) p1.getBidPoints(state));
	}
	
	@Test
	public void testLancelot() {
		GameState state = new GameState();
		Player p1 = new Player(1);
		state.addPlayer(p1);
		p1.addCardToHand(sl);
		p1.addField(sl);
		assertEquals(15, (int) sl.getBp(state));
		assertEquals(20, (int) p1.getBattlePoints(state));
		assertEquals(0, (int) sl.getBids(state));
		assertEquals(0, (int) p1.getBidPoints(state));
		state.setRevealedCard(qh);
		state.setQuest();
		assertEquals(25, (int) sl.getBp(state));
		assertEquals(30, (int) p1.getBattlePoints(state));
		assertEquals(0, (int) sl.getBids(state));
		assertEquals(0, (int) p1.getBidPoints(state));
	}
	
	@Test
	public void testGalahad() {
		GameState state = new GameState();
		Player p1 = new Player(1);
		state.addPlayer(p1);
		p1.addCardToHand(galahad);
		p1.addField(galahad);
		assertEquals(15, (int) sl.getBp(state));
		assertEquals(20, (int) p1.getBattlePoints(state));
		assertEquals(0, (int) sl.getBids(state));
		assertEquals(0, (int) p1.getBidPoints(state));
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
		gq.addStage(state, s1);
		gq.addStage(state, s2);
		gq.addStage(state, s3);
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

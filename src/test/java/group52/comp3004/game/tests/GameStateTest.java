package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import group52.comp3004.GameStateDemo;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Phase;
import group52.comp3004.players.Player;

public class GameStateTest {
	
	@Test
	public void testAddsPlayer() {
		GameStateDemo game = new GameStateDemo();
		
		assertEquals(new ArrayList<String>(), game.getPlayers());
		
		game.addPlayer("xXEdgyXx");
		
		assertEquals(Arrays.asList("xXEdgyXx"), game.getPlayers());
	}

	
	@Test
	public void testChangesTurnProperly() {
		GameState gs = new GameState(Arrays.asList(new Player(13), new Player(22), new Player(31), new Player(40)));
		
		assertEquals(0, gs.getCurrentTurn());
		
		gs.nextTurn();
		assertEquals(1, gs.getCurrentTurn());
		
		gs.nextTurn();
		assertEquals(2, gs.getCurrentTurn());
		
		gs.nextTurn();
		assertEquals(3, gs.getCurrentTurn());
		
		gs.nextTurn();
		assertEquals(0, gs.getCurrentTurn());
	}
	
	@Test
	public void testChangesPlayerProperly() {
		GameState gs = new GameState(Arrays.asList(new Player(13), new Player(22), new Player(31), new Player(40)));
		
		assertEquals(0, gs.getCurrentTurn());
		assertEquals(0, gs.getCurrentPlayer());
		
		gs.nextTurn();
		assertEquals(1, gs.getCurrentPlayer());
		
		gs.nextTurn();
		assertEquals(2, gs.getCurrentPlayer());
		
		gs.nextTurn();
		assertEquals(3, gs.getCurrentPlayer());
		
		gs.nextPlayer();
		assertEquals(0, gs.getCurrentPlayer());
		
		gs.nextPlayer();
		assertEquals(1, gs.getCurrentPlayer());
		
		gs.nextPlayer();
		assertEquals(2, gs.getCurrentPlayer());
		
		gs.nextPlayer();
		assertEquals(3, gs.getCurrentPlayer());
	}
	
	@Test
	public void testDeals12CardsToAllPlayers() {
		GameState gs = new GameState();
		Player p1 = new Player(123);
		Player p2 = new Player(1234);
		Player p3 = new Player(1247);
		Player p4 = new Player(1468);
		
		gs.addPlayer(p1);
		gs.addPlayer(p2);
		gs.addPlayer(p3);
		gs.addPlayer(p4);
		
		assertEquals(0, p1.getHand().size());
		assertEquals(0, p2.getHand().size());
		assertEquals(0, p3.getHand().size());
		assertEquals(0, p4.getHand().size());
		
		gs.dealCardsToPlayers();
		
		assertEquals(12, p1.getHand().size());
		assertEquals(12, p2.getHand().size());
		assertEquals(12, p3.getHand().size());
		assertEquals(12, p4.getHand().size());
	}
	
	
	@Test
	public void testPlaysQuestProperly() {
		QuestCard quest = new QuestCard("The Quest", 3);
		GameState gs = new GameState();
		
		Player p1 = new Player(123);
		Player p2 = new Player(1234);
		Player p3 = new Player(1247);
		Player p4 = new Player(1468);
		
		gs.addPlayer(p1);
		gs.addPlayer(p2);
		gs.addPlayer(p3);
		gs.addPlayer(p4);
		
		Player sponsor = gs.getCurrentSponsor();
		
		assertNull(sponsor);
		
		gs.setQuest();
		
		assertNull(gs.getCurrentQuest());
		
		gs.setRevealedCard(quest);
		assertEquals(quest, gs.getRevealed());
		
		assertEquals(null, gs.getCurrentSponsor());
		
		// assume player one does not want to sponsor the quest
		gs.nextPlayer();
		
		assertEquals(1, gs.getCurrentPlayer());
		assertEquals(null, sponsor);
		
		gs.setQuest();
		assertEquals(null, gs.getRevealed());
		assertEquals(Phase.SponsorQuest, gs.getPhase());
		
		GameQuest gq = gs.getCurrentQuest();
		
		assertEquals(quest, gq.getQuest());
		
		assertEquals(p2, gs.getCurrentSponsor());
		
		// the sponsor can't play in the quest
		gs.joinQuest();
		
		assertEquals(new ArrayList<Player>(), gq.getPlayers());
		
		// make players join the quest
		gs.nextPlayer();
		gs.joinQuest();
		
		assertEquals(Arrays.asList(p3), gq.getPlayers());
		
		// can't join the quest twice
		gs.joinQuest();
		assertEquals(Arrays.asList(p3), gq.getPlayers());
		
		gs.nextPlayer();
		gs.joinQuest();
		assertEquals(Arrays.asList(p3, p4), gq.getPlayers());
		
		
		// p1 does not join
		gs.nextPlayer();
		gs.nextPlayer();
		

		
		gs.playCardToTemp(new Weapon("Horse", 10));
		assertEquals(0, p2.getTemp().size());
		
		gs.setPhase(Phase.SetupQuest);
		gs.setUpQuestStage(new Foe("Foe", 10, 15));
		
		assertEquals(1, gs.getCurrentQuest().getStages().size());
		
		gs.setUpQuestStage(new Foe("Foe1", 20, 15));
		gs.setUpQuestStage(new Foe("Foe3", 15, 15)); // test foes increasing power
		assertEquals(2, gs.getCurrentQuest().getStages().size());
		
		gs.setUpQuestStage(new Foe("Foe4", 26, 15));
		assertEquals(3, gs.getCurrentQuest().getStages().size());
		
		gs.setUpQuestStage(new Foe("Foe4", 27, 15));
		assertEquals(3, gs.getCurrentQuest().getStages().size()); // test only set up up to stage nums
		
		// test in quest player turns
		gs.nextPlayer();
		assertEquals(p3, gs.getPlayerByIndex(gs.getCurrentPlayer()));
		
		gs.nextPlayer();
		assertEquals(p4, gs.getPlayerByIndex(gs.getCurrentPlayer()));
		
		gs.nextPlayer();
		assertEquals(p2, gs.getPlayerByIndex(gs.getCurrentPlayer()));
		
		//TODO: test players playing
		
		
		
		
	}
	
}

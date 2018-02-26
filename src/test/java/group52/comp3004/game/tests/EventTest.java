package group52.comp3004.game.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import group52.comp3004.ResourceManager;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Camelot;
import group52.comp3004.cards.Deed;
import group52.comp3004.cards.EventCard;
import group52.comp3004.cards.Favor;
import group52.comp3004.cards.Plague;
import group52.comp3004.cards.Pox;
import group52.comp3004.cards.Realm;
import group52.comp3004.cards.Recognition;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;
import javafx.embed.swing.JFXPanel;

class EventTest {
	JFXPanel jfxPanel = new JFXPanel();
	private GameState model;
	private ResourceManager resman = new ResourceManager();
	@Test
	public void testDeed() {
		model = new GameState();
		Player a = new Player(1);
		Player b = new Player(2);
		Player c = new Player(3);
		model.addPlayer(a);
		model.addPlayer(b);
		model.addPlayer(c);
		EventCard Deed =  new EventCard("Chivalrous_Deed",resman, new Deed());
		Deed.run(model);
		assertEquals(13, (int) a.getShields());
		assertEquals(13, (int) b.getShields());
		assertEquals(13, (int) c.getShields());
	}
	@Test
	public void testPox() {
		model = new GameState();
		Player a = new Player(1);
		Player b = new Player(2);
		Player c = new Player(3);
		model.addPlayer(a);
		model.addPlayer(b);
		model.addPlayer(c);
		EventCard Pox =  new EventCard("Pox",resman, new Pox());
		Pox.run(model);
		
		assertEquals(10, (int) a.getShields());
		assertEquals(9, (int) b.getShields());
		assertEquals(9, (int) c.getShields());
	}
	@Test
	public void testPlugue() {
		model = new GameState();
		Player a = new Player(1);
		Player b = new Player(2);
		Player c = new Player(3);
		model.addPlayer(a);
		model.addPlayer(b);
		model.addPlayer(c);
		EventCard Plague =  new EventCard("Plague",resman, new Plague());
		Plague.run(model);
		
		assertEquals(8, (int) a.getShields());
		assertEquals(10, (int) b.getShields());
		assertEquals(10, (int) c.getShields());
	}

	@Test
	public void testRec() {
		model = new GameState();
		Player a = new Player(1);
		Player b = new Player(2);
		Player c = new Player(3);
		model.addPlayer(a);
		model.addPlayer(b);
		model.addPlayer(c);
		EventCard Recog =  new EventCard("King's_Recognition",resman, new Recognition());
		Recog.run(model);
	
		assertEquals(2, (int) model.getBonusShields());
	}
	@Test
	public void testFavor() {
		model = new GameState();
		Player a = new Player(1);
		Player b = new Player(2);
		Player c = new Player(3);
		model.addPlayer(a);
		model.addPlayer(b);
		model.addPlayer(c);
		EventCard Favor =  new EventCard("Queen's_Favor",resman, new Favor());
		Favor.run(model);
		
		assertEquals(2, (int) a.getHand().size());
		assertEquals(2, (int) b.getHand().size());
		assertEquals(2, (int) c.getHand().size());
	}
	@Test
	public void testCamelot() {
		model = new GameState();
		Player a = new Player(1);
		Player b = new Player(2);
		Player c = new Player(3);
		Ally a1 = new Ally("Sir_Gawain", resman, 10, 0, "Green_Knight", 20, 0);
		Ally a2 = new Ally("King_Arthur", resman, 10, 2);
		model.addPlayer(a);
		model.addPlayer(b);
		model.addPlayer(c);
		a.addCardToHand(a1);
		b.addCardToHand(a2);
		a.addField(a1);
		b.addField(a2);
		assertEquals(1, (int) a.getField().size());
		assertEquals(1, (int) b.getField().size());
		EventCard Camelot =  new EventCard("Called_to_Camelot",resman, new Camelot());
		Camelot.run(model);
		
		assertEquals(1, (int) a.getField().size());
		assertEquals(1, (int) b.getField().size());
		assertEquals(0, (int) c.getHand().size());
	}
	
	@Test
	public void testArms() {
		model = new GameState();
		Player a = new Player(1);
		Player b = new Player(2);
		Player c = new Player(3);
		model.addPlayer(a);
		model.addPlayer(b);
		model.addPlayer(c);
		EventCard Favor =  new EventCard("Queen's_Favor",resman, new Favor());
		Favor.run(model);
		
		assertEquals(2, (int) a.getHand().size());
		assertEquals(2, (int) b.getHand().size());
		assertEquals(2, (int) c.getHand().size());
	}

	@Test
	public void testRealm() {
		model = new GameState();
		Player a = new Player(1);
		Player b = new Player(2);
		Player c = new Player(3);
		model.addPlayer(a);
		model.addPlayer(b);
		model.addPlayer(c);
		EventCard Realm =  new EventCard("Queen's_Favor",resman, new Realm());
		Realm.run(model);
		
		assertEquals(2, (int) a.getHand().size());
		assertEquals(2, (int) b.getHand().size());
		assertEquals(2, (int) c.getHand().size());
	}
	
}

package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


import group52.comp3004.ResourceManager;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.game.GameTourney;
import group52.comp3004.players.Player;
import javafx.embed.swing.JFXPanel;

class TourneyTest {
	JFXPanel jfxPanel = new JFXPanel();
	private Tourneys camelot;
	private GameTourney tournament;
	private ResourceManager resman = new ResourceManager();
	@Test
	public void testawardshield() {
		camelot = new Tourneys("Camelot", resman, 3);
		tournament = new GameTourney(camelot);
		Ally a1 = new Ally("Sir_Gawain", resman, 20, 0, "Green_Knight", 20, 0);
		Ally a2 = new Ally("King_Arthur", resman, 10, 2);

		Player p1 = new Player(2);
		Player p2 = new Player(3);
		Player p3 = new Player(4);
		
		
		tournament.addPlayer(p1);
		tournament.addPlayer(p2);
		tournament.addPlayer(p3);
		p1.addField(a1);
		p2.addField(a2);
		System.out.println(p2.getBattlePoints());
		System.out.println(p3.getBattlePoints());
		
		
		assertEquals(10, (int) p1.getShields());
		assertEquals(10, (int) p2.getShields());
		assertEquals(10, (int) p3.getShields());
		System.out.println(tournament.winner().get(0));
		tournament.awardShields();
		tournament.end();
		assertEquals(16, (int) p1.getShields());
		assertEquals(10, (int) p2.getShields());
		assertEquals(10, (int) p3.getShields());
	}
	@Test
	public void testTie() {
		camelot = new Tourneys("Camelot", resman, 3);
		tournament = new GameTourney(camelot);
		Ally a1 = new Ally("Sir_Gawain", resman, 20, 0, "Green_Knight", 20, 0);
		Ally a2 = new Ally("Sir_Gawain", resman, 20, 0, "Green_Knight", 20, 0);

		Player p1 = new Player(2);
		Player p2 = new Player(3);
		Player p3 = new Player(4);
		
		
		tournament.addPlayer(p1);
		tournament.addPlayer(p2);
		tournament.addPlayer(p3);
		p1.addField(a1);
		p2.addField(a2);
		System.out.println(p2.getBattlePoints());
		System.out.println(p1.getBattlePoints());
		
		
		assertEquals(10, (int) p1.getShields());
		assertEquals(10, (int) p2.getShields());
		assertEquals(10, (int) p3.getShields());
		System.out.println(tournament.winner().get(0));
		tournament.awardShields();
		tournament.end();
		assertEquals(16, (int) p1.getShields());
		assertEquals(16, (int) p2.getShields());
		assertEquals(10, (int) p3.getShields());
	}

}

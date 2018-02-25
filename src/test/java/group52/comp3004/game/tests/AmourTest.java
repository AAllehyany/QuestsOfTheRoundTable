package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import group52.comp3004.ResourceManager;
import group52.comp3004.cards.Amour;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;
import javafx.embed.swing.JFXPanel;

public class AmourTest {
	JFXPanel jfxPanel = new JFXPanel();
	public ResourceManager resman = new ResourceManager();

	@Test
	public void testGetStats() {
		GameState state = new GameState();
		Player p1 = new Player(0);
		state.addPlayer(p1);
		Amour a = new Amour("Amour", resman, 10, 1);
		p1.addCardToHand(a);
		p1.addTemp(a);
		assertEquals(10, (int) a.getBP());
		assertEquals(1, (int) a.getBids());
	}

}

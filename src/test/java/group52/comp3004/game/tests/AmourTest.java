package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import group52.comp3004.ResourceManager;
import group52.comp3004.cards.Amour;
import javafx.embed.swing.JFXPanel;

public class AmourTest {
	JFXPanel jfxPanel = new JFXPanel();
	public ResourceManager resman = new ResourceManager();

	@Test
	public void testGetStats() {
		Amour a = new Amour("Amour", resman, 10, 1);
		assertEquals(10, (int) a.getBP());
		assertEquals(1, (int) a.getBids());
	}

}

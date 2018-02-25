package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import group52.comp3004.ResourceManager;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.Stage;
import javafx.embed.swing.JFXPanel;

public class StageTest {
	JFXPanel jfxPanel = new JFXPanel();
	private ResourceManager resman = new ResourceManager();
	
	@Test
	public void testHasCorrectBp() {
		Foe theNicefoe = new Foe("Saxons", resman, 10, 20, "Hi");
		Stage stage = new Stage(theNicefoe);
		assertEquals(10, stage.getTotalPower());
	}
	
	@Test
	public void testNumCardsPlayed() {
		
		Foe theEvilFoe = new Foe("Dragon", resman, 16, 16, "Hi");
		Weapon goodWeapon = new Weapon("Excalibur", resman, 16);
		
		
		Stage stage = new Stage(theEvilFoe);
		
		assertEquals(1, stage.totalCardsPlayed());
		
		theEvilFoe.addWeapon(goodWeapon);
		
		assertEquals(2, stage.totalCardsPlayed());
	}
}

package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.Foe;
import group52.comp3004.game.Stage;

public class StageTest {

	@Test
	public void testHasCorrectBp() {
		Foe theNicefoe = new Foe("XD", 10, 20);
		Stage stage = new Stage(theNicefoe);
		assertEquals(10, stage.getTotalPower());
	}
}

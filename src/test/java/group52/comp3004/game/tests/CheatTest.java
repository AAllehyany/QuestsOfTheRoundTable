package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.CardFactory;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public class CheatTest {
	@Test
	public void testCheat() {
		GameState state = new GameState();
		Player p1 = new Player(0);
		QuestCard bh = CardFactory.createQuest("Boar_Hunt", 2);
		Foe b = CardFactory.createFoe("Boar", 5, 15, "Boar_Hunt");
		Foe g = CardFactory.createFoe("Giant", 40);
		Weapon d = CardFactory.createWeapon("Dagger", 5);
		Stage s1 = new Stage(b);
		g.addWeapon(d);
		Stage s2 = new Stage(g);
		state.addPlayer(p1);
		state.setRevealedCard(bh);
		state.setQuest();
		state.getCurrentQuest().addStage(state, s1);
		state.getCurrentQuest().addStage(state, s2);
		ArrayList<Integer> stages = state.getCurrentQuest().getStageCardNum();
		assertEquals(1, (int) stages.get(0));
		assertEquals(2, (int) stages.get(1));
	}
}

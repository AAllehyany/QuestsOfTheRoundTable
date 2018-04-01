package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;

class WeaponTest {
	private GameState state;
	
	Weapon excalibur = new Weapon("Excalibur", 30);
	Weapon horse = new Weapon("Horse", 10);
	Weapon sword = new Weapon("Sword", 10);
	Weapon dagger = new Weapon("Dagger", 5);
	Weapon lance = new Weapon("Lance", 20);
	Weapon battleax = new Weapon("Battle_Ax", 15);

	@Test
	void playerAddWeapon() {	
		state = new GameState();
		Player p1 = new Player(1);
		p1.addTemp(excalibur);
		assertEquals(35, (int) p1.getBattlePoints(state));
	}
	
	@Test
	void playerAddSameWeapon() {	
		state = new GameState();
		Player p1 = new Player(1);
		p1.addTemp(excalibur);
		boolean result = p1.canPlayTemp(excalibur);
		assertEquals(false, (boolean) result);
		assertEquals(35, (int) p1.getBattlePoints(state));
	}
	
	@Test
	void playerAddMultipleWeapons() {	
		state = new GameState();
		Player p1 = new Player(1);
		p1.addTemp(excalibur);
		p1.addTemp(horse);
		p1.addTemp(dagger);
		assertEquals(50, (int) p1.getBattlePoints(state));
	}
	
	@Test
	void playerClearWeapon() {	
		state = new GameState();
		Player p1 = new Player(1);
		p1.addTemp(excalibur);
		p1.addTemp(horse);
		p1.addTemp(dagger);
		p1.clearTemp();
		assertEquals(5, (int) p1.getBattlePoints(state));
	}
	
	@Test
	void foeAddWeapon() {
		state = new GameState();
		Foe giant = new Foe("Giant", 40);
		giant.addWeapon(dagger);
		assertEquals(45, (int) giant.getBp(state));
	}
	
	@Test
	void foeAddSameWeapon() {
		state = new GameState();
		Foe giant = new Foe("Giant", 40);
		giant.addWeapon(dagger);
		boolean result = giant.addWeapon(dagger);
		assertEquals(false, (boolean) result);
		assertEquals(45, (int) giant.getBp(state));
	}
	
	@Test
	void foeAddMultipleWeapon() {
		state = new GameState();
		Foe giant = new Foe("Giant", 40);
		giant.addWeapon(dagger);
		giant.addWeapon(lance);
		giant.addWeapon(battleax);
		assertEquals(80, (int) giant.getBp(state));
	}

	@Test
	void foeClearWeapon() {
		state = new GameState();
		Foe giant = new Foe("Giant", 40);
		giant.addWeapon(dagger);
		giant.addWeapon(lance);
		giant.addWeapon(battleax);
		giant.clearWeapons();
		assertEquals(40, (int) giant.getBp(state));
	}
}

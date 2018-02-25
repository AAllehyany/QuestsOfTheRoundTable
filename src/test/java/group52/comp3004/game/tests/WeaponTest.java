package group52.comp3004.game.tests;

import static org.junit.jupiter.api.Assertions.*;

import group52.comp3004.players.Player;
import javafx.embed.swing.JFXPanel;
import group52.comp3004.cards.Weapon;
import group52.comp3004.ResourceManager;
import group52.comp3004.cards.Foe;

import org.junit.jupiter.api.Test;

class WeaponTest {
	JFXPanel jfxPanel = new JFXPanel();
	public ResourceManager resman = new ResourceManager();

	Weapon excalibur = new Weapon("Excalibur", resman, 30);
	Weapon horse = new Weapon("Horse", resman, 10);
	Weapon sword = new Weapon("Sword", resman, 10);
	Weapon dagger = new Weapon("Dagger", resman, 5);
	Weapon lance = new Weapon("Lance", resman, 20);
	Weapon battleax = new Weapon("Battle_Ax", resman, 15);

	@Test
	void playerAddWeapon() {	
		Player p1 = new Player(1);
		p1.addTemp(excalibur);
		assertEquals(35, (int) p1.getBattlePoints());
	}
	
	@Test
	void playerAddSameWeapon() {	
		Player p1 = new Player(1);
		p1.addTemp(excalibur);
		boolean result = p1.canPlayWeapon(excalibur);
		assertEquals(false, (boolean) result);
		assertEquals(35, (int) p1.getBattlePoints());
	}
	
	@Test
	void playerAddMultipleWeapons() {	
		Player p1 = new Player(1);
		p1.addTemp(excalibur);
		p1.addTemp(horse);
		p1.addTemp(dagger);
		assertEquals(50, (int) p1.getBattlePoints());
	}
	
	@Test
	void playerClearWeapon() {	
		Player p1 = new Player(1);
		p1.addTemp(excalibur);
		p1.addTemp(horse);
		p1.addTemp(dagger);
		p1.clearTemp();
		assertEquals(5, (int) p1.getBattlePoints());
	}
	
	@Test
	void foeAddWeapon() {
		Foe giant = new Foe("Giant", resman, 40);
		giant.addWeapon(dagger);
		assertEquals(45, (int) giant.getBp());
	}
	
	@Test
	void foeAddSameWeapon() {
		Foe giant = new Foe("Giant", resman, 40);
		giant.addWeapon(dagger);
		boolean result = giant.addWeapon(dagger);
		assertEquals(false, (boolean) result);
		assertEquals(45, (int) giant.getBp());
	}
	
	@Test
	void foeAddMultipleWeapon() {
		Foe giant = new Foe("Giant", resman, 40);
		giant.addWeapon(dagger);
		giant.addWeapon(lance);
		giant.addWeapon(battleax);
		assertEquals(80, (int) giant.getBp());
	}

	@Test
	void foeClearWeapon() {
		Foe giant = new Foe("Giant", resman, 40);
		giant.addWeapon(dagger);
		giant.addWeapon(lance);
		giant.addWeapon(battleax);
		giant.clearWeapons();
		assertEquals(40, (int) giant.getBp());
	}
}

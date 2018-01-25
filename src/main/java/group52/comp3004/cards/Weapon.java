package group52.comp3004;

import group52.comp3004.cards.AdventureCard;

public class Weapon extends AdventureCard{
	private final int battlePoint;
	
	public Weapon(String name, int bp) {
		super(name);
		this.battlePoint= bp;
		// TODO Auto-generated constructor stub
	}

	public int getBP() {
		return this.battlePoint;
	}
}

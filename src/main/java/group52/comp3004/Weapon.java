package group52.comp3004;

public class Weapon extends AdventureCard{
private int battlePoint;
	public Weapon(CardType name) {
		super(name);
		this.battlePoint= 0;
		// TODO Auto-generated constructor stub
	}

	public void setBP(int battlePoint) {
		this.battlePoint= battlePoint;
	}
	
	public int getBP() {
		return this.battlePoint;
	}
}

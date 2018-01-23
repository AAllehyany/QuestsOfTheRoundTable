

public class AdventureCard extends NewCard{

	private int battlePoint;
	public AdventureCard(CardType name) {
		super(name);
		this.battlePoint = battlePoint;
		// TODO Auto-generated constructor stub
	}
	
	public void setBP(int battlePoint) {
		this.battlePoint= battlePoint;
	}
	
	public int getBP() {
		return this.battlePoint;
	}

}

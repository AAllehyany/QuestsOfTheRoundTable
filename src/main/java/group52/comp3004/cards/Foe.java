package group52.comp3004;

public class Foe extends AdventureCard{
	private int highBP;
	public Foe(CardType name) {
		super(name);
		highBP = 0;
		// TODO Auto-generated constructor stub
	}
	
	public void setHighBP(int highBP) {
		this.highBP= highBP;
	}
	
	public int getHighBP() {
		return this.highBP;
	}
}

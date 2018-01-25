package group52.comp3004.cards;

public class Foe extends AdventureCard{

	private final int bp;
	private final int highBp;
	
	public Foe(String name, int bp, int highBp) {
		super(name);
		this.bp = bp;
		this.highBp = highBp;
		// TODO Auto-generated constructor stub
	}
	
	public int getBp() {
		return this.highBp;
	}
}

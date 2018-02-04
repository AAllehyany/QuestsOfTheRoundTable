package group52.comp3004.cards;

public class Ally extends AdventureCard
{
	private final int bp;
	
	// TODO special abilities
	
	public Ally(String name, int bp) {
		
		super(name);
		this.bp = bp;
		
	}

	public int getBp() {
		return bp;
	}
	
	

}



public class AdventureCard extends NewCard{

	private String description;
	public AdventureCard(CardType name) {
		super(name);
		this.description = null;
		// TODO Auto-generated constructor stub
	}
	
	public void setDes(String description) {
		this.description= description;
	}
	
	public int getDes() {
		return this.description;
	}

}

package group52.comp3004.cards;

public class AdventureCard extends Card{

	private String description;
	public AdventureCard(String name) {
		super(name);
		this.description = null;
		// TODO Auto-generated constructor stub
	}
	
	public void setDes(String description) {
		this.description= description;
	}
	
	public String getDes() {
		return this.description;
	}

}

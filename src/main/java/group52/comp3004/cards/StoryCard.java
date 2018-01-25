package group52.comp3004;

public class StoryCard extends NewCard{
	private String description;
	public StoryCard(CardType name) {
		super(name);
		this.description =description;
		// TODO Auto-generated constructor stub
	}
	
	public void setDes(String description) {
		this.description=description;
		
	}
	public String getDes() {
		return this.description;
	}
}

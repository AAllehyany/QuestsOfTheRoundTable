package group52.comp3004.cards;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class AdventureCard extends Card{

	private String description;
	protected int bp;
	
	public AdventureCard(String name) {
		super(name);
		this.description = null;
		
		//get face resources
		//front = resman.getFront(name, 'a'); //need to fix names
		//back = resman.getAdventureBack();
		Image adventureBackImg = new Image("/image/Cards/Backs/adventure_back.jpg");
		//back = new ImagePattern(adventureBackImg);
	}
	
	public void setDes(String description) {
		this.description= description;
	}
	
	public String getDes() {
		return this.description;
	}
	
	public int getBp() {
		return this.bp;
	}

}

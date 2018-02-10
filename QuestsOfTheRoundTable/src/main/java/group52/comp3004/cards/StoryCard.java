package group52.comp3004.cards;

public class StoryCard extends Card{
	public StoryCard(String name) {
		super(name);
		
		//get face resources
		//front = resman.getFront(name, 's');
		back = resman.getStoryBack();
	}

}

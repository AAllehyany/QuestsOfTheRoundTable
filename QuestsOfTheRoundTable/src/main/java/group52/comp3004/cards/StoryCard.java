package group52.comp3004.cards;

import group52.comp3004.ResourceManager;

public class StoryCard extends Card{
	public StoryCard(String name, ResourceManager rm) {
		super(name, rm);
		
		//get face resources
		//front = resman.getFront(name, 's');
		back = resman.getStoryBack();
	}

}

package group52.comp3004.cards;

public class QuestCard extends StoryCard {
	
	private final int stages;
	
	/**
	 * Adds functionality for quest type stroy cards
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 * @param stages number of stages in the quest
	 */
	public QuestCard(String name, int stages) {
		super(name);
		this.stages = stages;
	}
	
	/**
	 * Get the number of stages in the quest
	 */
	public int getStages() { return this.stages; }

}

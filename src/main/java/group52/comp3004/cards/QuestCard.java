package group52.comp3004.cards;

public class QuestCard extends StoryCard {
	
	private final int stages;
	// private final List<Foe> foes;
	
	public QuestCard(String name, int stages) {
		super(name);
		this.stages = stages;
	}
	
	public int getStages() { return this.stages; }

}

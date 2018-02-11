package group52.comp3004.cards;

public class QuestCard extends StoryCard {
	
	private final int stages;
	private String foe;
	// private final List<Foe> foes;
	
	public QuestCard(String name, int stages, String foe) {
		super(name);
		this.stages = stages;
		this.foe = foe;
	}
	
	public int getStages() { return this.stages; }
	public String getFoe() { return this.foe; }

}

package group52.comp3004.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import group52.comp3004.cards.QuestCard;
import group52.comp3004.players.Player;

public class GameQuest {
	
	private final QuestCard quest;
	private boolean withTest;
	private List<Stage> stages;
	private List<Player> players;
	private int currentStage;
	private Player sponsor;
	private boolean over;
	
	/**
	 * @param quest
	 * @param hasTest
	 */
	public GameQuest(QuestCard quest, Player sponsor) {
		this.quest = quest;
		this.withTest = false;
		this.stages = new ArrayList<Stage>();
		this.players = new ArrayList<Player>();
		this.currentStage = 0;
		this.sponsor = sponsor;
		this.over = false;
	}
	
	public boolean isWithTest() {
		return withTest;
	}

	
	public QuestCard getQuest() {
		return quest;
	}
	
	public List<Stage> getStages() {
		return stages;
	}
	
	public boolean addStage(Stage stage) {
		Stage highestStage = stages.stream().max( (s1, s2) -> Integer.compare(s1.getTotalPower(), s2.getTotalPower())).orElse(null);
		
		if((highestStage == null || stage.getTotalPower() >= highestStage.getTotalPower()) && stages.size() < quest.getStages()) {
			stages.add(stage);
			return true;
		}
		
		return false;
	}
	
	public Stage currentStage() {
		return stages.get(currentStage);
	}
	
	public int getCurrentStage() {
		return currentStage;
	}
	
	public void advanceStage() {
		if(currentStage < (quest.getStages() - 1)) currentStage += 1;
	}
	
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	public void playStage() {
		if(over) return;
		this.players = players.stream().filter(p -> p.getBattlePoints() >= stages.get(currentStage).getTotalPower()).collect(Collectors.toList());
		if(currentStage == (quest.getStages() - 1)) this.over = true;
		advanceStage();	
	}
	
	public int getNumStages() {
		return this.quest.getStages();
	}
	
	public void awardShields() {
		if(over) players.forEach(p -> p.addShields(quest.getStages()));
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public boolean isOver() {
		return this.over;
	}
	
	
}

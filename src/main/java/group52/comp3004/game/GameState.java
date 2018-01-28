package group52.comp3004.game;

import java.util.List;

import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.players.Player;

public class GameState {
	
	private List<Player> players;
	private int currentTurn;
	private int currentPlayer;
	private Phase phase;
	private GameQuest quest;
	private StoryCard revealed;
	
	/**
	 * @param players
	 */
	public GameState(List<Player> players) {
		super();
		this.players = players;
		this.currentTurn = 0;
		this.currentPlayer = 0;
		phase = Phase.TurnStart;
		quest = null;
	}
	
	
	public void nextTurn() {
		currentTurn = (currentTurn + 1) % players.size();
		currentPlayer = currentTurn;
	}
	
	public void nextPlayer() {
		currentPlayer = (currentPlayer + 1) % players.size();
	}
	
	public int getCurrentTurn() {
		return this.currentTurn;
	}
	
	public int getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public void changePhase(Phase phase) {
		this.phase = phase;
	}
	
	public void setGameQuest(QuestCard quest) {
		this.quest = new GameQuest(quest, this.players.get(currentPlayer));
	}
	
	public GameQuest getGameQuest() {
		return this.quest;
	}
	
	public void removeQuest() {
		this.quest = null;
	}
	
	public Phase getPhase() {
		return this.phase;
	}
	
	public StoryCard getRevealed() {
		return this.revealed;
	}
	
}

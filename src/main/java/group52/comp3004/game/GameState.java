package group52.comp3004.game;

import java.util.List;

import group52.comp3004.players.Player;

public class GameState {
	
	private List<Player> players;
	private int currentTurn;
	private int currentPlayer;
	private Phase phase;
	/**
	 * @param players
	 */
	public GameState(List<Player> players) {
		super();
		this.players = players;
		this.currentTurn = 0;
		this.currentPlayer = 0;
		phase = Phase.TurnStart;
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
	
}

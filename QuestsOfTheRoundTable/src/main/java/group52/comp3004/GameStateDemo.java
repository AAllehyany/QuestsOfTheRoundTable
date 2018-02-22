package group52.comp3004;

import java.util.ArrayList;
import java.util.List;

public class GameStateDemo {
	
	List<String> players;

	
	public GameStateDemo() {
		this.players = new ArrayList<String>();
	}
	
	public List<String> getPlayers() { return this.players; }
	public void addPlayer(String name) { this.players.add(name); }
	
}

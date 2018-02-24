package group52.comp3004.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.players.Player;
public class GameTourney {
	private final Tourneys tourney;
	private List<Player> players;
	private Player sponsor;
	private List<Player> winner;
	private List<Player> count;
	private boolean over;
	
	public GameTourney(Tourneys tourney, Player sponsor) {
		this.tourney = tourney;
		this.players = new ArrayList<Player>();
		this.sponsor = sponsor;
		this.over= false;
	}
	
	public Tourneys getTourney() {
		return tourney;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	//Why need to add tourney inside player?
	public void addPlayer(Player player) {
		/*if(!this.players.contains(player) && player.getTourney() == null) {
			this.players.add(player);
			player.setTourney(this);
		}*/
	}

	public boolean isOver() {
		return this.over;
	}
	
	public void dealCards() {
		for(int i=0;i<this.players.size();i++) {
			this.players.get(i).getGame().dealToPlayer(1);
		}
			
	}
	
	public List<Player> winner() {
		int count = battle(this.players).size();
		if(count>1) {
				winner = battle(battle(this.players));
			}
		else {
			winner = battle(this.players);
		}
		return this.winner;
	}
	
	public List<Player> battle(List<Player> player) {

		Player highest= player.get(0);
		for(int i=1;i<this.players.size();i++) {
			if(player.get(i).getBattlePoints()>highest.getBattlePoints()) {
				highest =player.get(i);
				count.clear();
			}else if(player.get(i).getBattlePoints()==highest.getBattlePoints()) {
				count.add(player.get(i));
			}
		}
		return count;
	}
	public void awardShields() {
		if(over) winner.forEach(p -> p.addShields(tourney.getShields()));
	}
}

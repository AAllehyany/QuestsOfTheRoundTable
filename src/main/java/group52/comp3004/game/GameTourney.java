package group52.comp3004.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import group52.comp3004.cards.Tourneys;
import group52.comp3004.players.Player;


public class GameTourney {
	private final Tourneys tourney;
	private List<Player> players;
	private List<Player> count,count1;
	private int bonus;
	private List<Player> winner;
	private boolean over;
	static final private Logger logger = Logger.getLogger(GameTourney.class);
	
	public GameTourney(Tourneys tourney) {
		this.tourney = tourney;
		this.players = new ArrayList<Player>();
		this.count = new ArrayList<Player>();
		this.count1 = new ArrayList<Player>();
		this.winner=new ArrayList<Player>();
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
		logger.info("Player: " + player.getId() + " joined the quest");
		this.players.add(player);
		player.setTourney(this);
	}

	public boolean isOver() {
		return this.over;
	}
	
	public void dealCards() {
		for(int i=0;i<this.players.size();i++) {
			this.players.get(i).getGame().dealToPlayer(i);
		}
			
	}
	
	public List<Player> winner(GameState state) {
		List<Player> win= battle(state, this.players);
		if(this.players.size()==1) {
			this.winner= this.players;
		}else {
			int count = win.size();
			if(count>1) {
					this.winner = secondBattle(state, win);
				}
			else {
				this.winner = win;
			}
		}
		for(int i=0;i<winner.size();i++) 
			logger.info("Player: " + winner.get(i).getId() + " won the tournament");
		return this.winner;
	}
	
	public List<Player> secondBattle(GameState state, List<Player> player) {
	    this.count1.clear();
		int highest = 0;
		this.count1.add(player.get(0));
		for(int i=1;i<player.size();i++) {
			if(player.get(i).getBattlePoints(state)>highest) {
				highest =player.get(i).getBattlePoints(state);
				this.count1.clear();
				this.count1.add(player.get(i));
			}else if(player.get(i).getBattlePoints(state)==highest) {
				this.count1.add(player.get(i));
			}
		}
		for(int i=0;i<player.size();i++) {
			state.getAdventureDeck().discardCard(this.players.get(i).clearTemp());
		}
		for(int i=0;i<this.count1.size();i++) 
			logger.info("Player: " + this.count.get(i).getId() + " won the second tournament round");
		return this.count1;
	}
	
	public List<Player> battle(GameState state, List<Player> player) {
		this.count.clear();
		int highest = 0;
		for(int i=0;i<this.players.size();i++) {
			if(player.get(i).getBattlePoints(state)>highest) {
				highest = player.get(i).getBattlePoints(state);
				this.count.clear();
				this.count.add(player.get(i));
			}else if(player.get(i).getBattlePoints(state)==highest) {
				this.count.add(player.get(i));
			}
		}
		for(int i=0;i<player.size();i++) {
			state.getAdventureDeck().discardCard(this.players.get(i).clearTemp());
		}
		for(int i=0;i<count.size();i++) 
			logger.info("Player: " + this.count.get(i).getId() + " won the first tournament round");
		return this.count;
	}
	
	public void awardShields() {
		 this.winner.forEach(p -> p.addShields(players.size()+tourney.getShields()));
	}
	
	public void end(GameState state) {
		for(int i =0;i<this.players.size();i++) {
			state.getAdventureDeck().discardCard(this.getPlayers().get(i).getTemp());
			this.getPlayers().get(i).getTemp().clear();
		}
		this.over = true;
		this.count.clear();
		this.count1.clear();
	}

	public boolean isPlayer(Player player) {
		// TODO Auto-generated method stub
		return this.players.contains(player); // || player == sponsor;
	}
}

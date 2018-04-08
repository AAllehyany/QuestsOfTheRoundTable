package group52.comp3004.game;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import group52.comp3004.cards.Tourneys;
import group52.comp3004.players.Player;

/**
 * Handles set up, playing, and ending a tourney state.
 * <p>Tourneys is the card itself. GameTourney handles the quest game play.</p>
 * @author Sandy
 *
 */
public class GameTourney {
	private final Tourneys tourney;
	private List<Player> players;
	private List<Player> count,count1;
	private List<Player> winner;
	private boolean over;
	static final private Logger logger = Logger.getLogger(GameTourney.class);
	
	/**
	 * Constructor for a new tourney.
	 * @param tourney The specific tourney card drawn. Important since each card awards a different number of bonus shields.
	 */
	public GameTourney(Tourneys tourney) {
		this.tourney = tourney;
		this.players = new ArrayList<Player>();
		this.count = new ArrayList<Player>();
		this.count1 = new ArrayList<Player>();
		this.winner=new ArrayList<Player>();
		this.over= false;
	}
	
	/**
	 * Get the tourney card drawn.
	 * @return
	 */
	public Tourneys getTourney() {
		return tourney;
	}
	
	/**
	 * Get list of players involved in the tourney.
	 * @return
	 */
	public List<Player> getPlayers() {
		return this.players;
	}
	
	/**
	 * Adds a player to the tourney. Needs a separate list because not all players may have joined the tourney.
	 * @param player
	 */
	public void addPlayer(Player player) {
		logger.info("Player: " + player.getId() + " joined the quest");
		this.players.add(player);
		player.setTourney(this);
	}

	/**
	 * Tests whether the tourney is complete.
	 * @return true if tourney is over.
	 */		
	public boolean isOver() {
		return this.over;
	}
	
	/**
	 * Each player gets a card dealt to them at the start of a tourney.
	 */
	public void dealCards() {
		for(int i=0;i<this.players.size();i++) {
			this.players.get(i).getGame().dealToPlayer(i);
		}
			
	}
	
	/**
	 * Determine who the winners of a tournament are, by playing either a second round of battles or 
	 * directly determining the winner
	 * @return the players who won the tournament
	 */
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
	
	/**
	 * Award shields to winner equal to number of players in tourney plus the bonus provided by the card.
	 */
	public void awardShields() {
		 this.winner.forEach(p -> p.addShields(players.size()+tourney.getShields()));
	}

	/**
	 * Determine the winner/s of the second round in a tournament if there is a tie in the first round of 
	 * the tournament
	 * @param the current conditions of the game
	 * @param player the list of players taking part in the second battle
	 * @return the list of players who won the tournament
	 */
	public List<Player> secondBattle(GameState state, List<Player> player) {
	    this.count1.clear();
		int highest = 0;
		for(int i=0;i<player.size();i++) {
			if(player.get(i).getBattlePoints(state)>highest) {
				highest =player.get(i).getBattlePoints(state);
				this.count1.clear();
				this.count1.add(player.get(i));
			}else if(player.get(i).getBattlePoints(state)==highest) {
				this.count1.add(player.get(i));
			}
		}
		for(int i=0;i<player.size();i++) {
			state.getAdventureDeck().discard(this.players.get(i).clearTemp());
		}
		for(int i=0;i<this.count1.size();i++) 
			logger.info("Player: " + this.count.get(i).getId() + " won the second tournament round");
		return this.count1;
	}
	
	/**
	 * Determine the winner/s of the initial tournament round
	 * @param state the current conditions of the game
	 * @param player the list of players taking part in the tournament
	 * @return the list of players who won the first round of the tournament
	 */
	public List<Player> battle(GameState state, List<Player> player) {
		this.count.clear();
		int highest = 0;
		for(int i=0;i<player.size();i++) {
			if(player.get(i).getBattlePoints(state)>highest) {
				highest = player.get(i).getBattlePoints(state);
				this.count.clear();
				this.count.add(player.get(i));
			}else if(player.get(i).getBattlePoints(state)==highest) {
				this.count.add(player.get(i));
			}
		}
		for(int i=0;i<player.size();i++) {
			state.getAdventureDeck().discard(this.players.get(i).clearTemp());
		}
		for(int i=0;i<count.size();i++) 
			logger.info("Player: " + this.count.get(i).getId() + " won the first tournament round");
		return this.count;
	}
	

	/**
	 * Handle end of tourney. 
	 */
	public void end(GameState state) {
		for(int i =0;i<this.players.size();i++) {
			state.getAdventureDeck().discard(this.getPlayers().get(i).emptyTemp());
		}
		this.over = true;
		this.count.clear();
		this.count1.clear();
	}

	/**
	 * Tests whether a player is a part of the tourney
	 * @param player Player to search for
	 * @return True if the player is participating in the tourney
	 */
	public boolean isPlayer(Player player) {
		return this.players.contains(player); // || player == sponsor;
	}
}

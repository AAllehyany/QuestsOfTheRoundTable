package group52.comp3004.game;

import java.util.ArrayList;
import java.util.List;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.decks.Deck;
import group52.comp3004.players.Player;

public class GameState {
	
	private List<Player> players;
	private int currentTurn;
	private int currentPlayer;
	private int currentSponsor;
	private Phase phase;
	private Deck<AdventureCard> adventureDeck;
	private GameQuest currentQuest;
	private StoryCard revealedCard;
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
	
	public GameState() {
		super();
		this.players = new ArrayList<>();
		this.currentTurn = 0;
		this.currentPlayer = 0;
		this.currentSponsor = -1;
		phase = Phase.TurnStart;
		adventureDeck = new Deck<AdventureCard>(Deck.createAdventureDeck());
		currentQuest = null;
		revealedCard = null;
	}
	
	
	public void startGame() {
		dealCardsToPlayers();
	}
	
	public void dealCardsToPlayers() {
		this.players.forEach(player -> {
			for(int i = 0; i < 12; i++) {
				player.addCardToHand(adventureDeck.drawCard());
			}
		});
	}


	public void addPlayer(Player player) {
		if(players.size() < 4) players.add(player);
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
	
	public Player getPlayerByIndex(int index) {
		return this.players.get(index);
	}
	
	public void dealToPlayer(int index) {
		AdventureCard card = adventureDeck.drawCard();
		if(card != null)
			this.players.get(index).addCardToHand(card);
	}
	
	public int numPlayers() {
		return this.players.size();
	}
	public void joinQuest() {
		if(currentQuest != null) currentQuest.addPlayer(getPlayerByIndex(currentPlayer));
	}
	
	public void setQuest() {
		if(revealedCard != null && revealedCard instanceof QuestCard)
		{
			currentSponsor = currentPlayer;
			currentQuest = new GameQuest((QuestCard) revealedCard, getPlayerByIndex(currentSponsor));
		}
	}
	
	public void endQuest() {
		currentSponsor = -1;
		currentQuest = null;
		
		// remove all allies and all cards and make people draw (call quest end)
	}
	
	public void playCurrentQuestStage() {
		if(currentQuest != null) currentQuest.playStage();
	}
	
	public GameQuest getCurrentQuest() {
		return this.currentQuest;
	}
}

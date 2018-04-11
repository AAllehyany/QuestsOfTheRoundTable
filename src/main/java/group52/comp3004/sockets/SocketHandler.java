package group52.comp3004.sockets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.EventCard;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Phase;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

@Component
public class SocketHandler extends TextWebSocketHandler{
	
	final static Logger logger = Logger.getLogger(SocketHandler.class);
	private GameState game = new GameState();
	private ConcurrentHashMap<WebSocketSession, Player> players = new ConcurrentHashMap();
	private static int id = 0;
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage mes) throws Exception{
		Map<String, String> payload = new Gson().fromJson(mes.getPayload(), Map.class);
		String event = payload.get("event");
				
		switch(event) {
		/*********Game phase handlers***********************/			
		case "TURN_START":
			turnStart(session, payload);
			break;
		case "REVEAL_STORY":
			break;
		case "HANDLE_EVENT":
			handleEvent(session, payload);
			break;			
		case "SPONSOR_QUEST":
			sponsorQuest(session, payload);
			break;		
		case "JOIN_QUEST":
			joinQuest(session, payload);
			break;			
		case "SETUP_QUEST":
			setupQuest(session, payload);
			break;
		case "PLAY_QUEST":
			playQuest(session, payload);
			break;			
		case "PLAY_STAGE":
			playStage(session, payload);
			break;		
		case "JOIN_TOURNEY":
			joinTourney(session, payload);
			break;
		case "RUN_TOURNEY":
			runTourney(session, payload);
			break;
		case "TURN_END":
			turnEnd(session, payload);
			break;
		case "GAME_OVER":
			gameOver(session, payload);
			break;
		/********************************************************/
		default:
			invalidEvent(session, payload);
		}	
	}
	
	/**
	 * Handles an invalid event being sent to the server
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void invalidEvent(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		message.put("type", "INVALID_EVENT");
		message.put("data", "Event invalid");
		session.sendMessage(new TextMessage(gson.toJson(message)));
		
	}
	
	/**
	 * Handles the players joining a game. No more than 4 players may join a single game.
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void joinGame(WebSocketSession session) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("Player " + id + " attempting to join game");
		Player player = players.get(session);
		
		if(game.getAllPlayers().size() >= 4) {
			logger.info("Game has too many players.");
			message.put("type", "ERROR");
			message.put("data", "Game has too many players.");
			session.sendMessage(new TextMessage(gson.toJson(message)));
			return;
		}
		
		logger.info("Player " + id + " joined game!");
		game.addPlayer(player);
		id++;
		message.put("type", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
		session.sendMessage(new TextMessage(gson.toJson(message)));		
		if(game.getAllPlayers().size() == 4) {
			this.startGame(session);
		}
		
	}
	
	/**
	 * Handles the StartGame phase
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void startGame(WebSocketSession session) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("Starting Game");
		//add ai players when room are made
		
		game.dealCardsToPlayers();
		game.setPhase(Phase.TurnStart);
		
		message.put("type", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
		for(WebSocketSession user : players.keySet()){
			user.sendMessage(new TextMessage(gson.toJson(message)));
		}
	}
	
	/**
	 * Handles the TurnStart phase
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void turnStart(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("New turn starts");
		
		game.setPhase(Phase.RevealStory);
		
		message.put("type", "PHASE_CHANGE");
		message.put("data", "RevealStory");
		
		this.revealStory(session);
		
		session.sendMessage(new TextMessage(gson.toJson(message)));
	}
	
	/**
	 * Handles the RevealStory phase
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void revealStory(WebSocketSession session) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		game.setRevealedCard(game.getStoryDeck().draw());
		logger.info("Next story card: " + game.getRevealedCard().getName());

		String nextPhase = "";
		if(game.getRevealedCard() instanceof EventCard) {
			nextPhase = "HandleEvent";
			game.setPhase(Phase.HandleEvent);
		}
		else if(game.getRevealedCard() instanceof Tourneys) {
			//game.setTourney();
			nextPhase = "SponsorTourney";
			game.setPhase(Phase.SponsorTourney);
		}
		else if(game.getRevealedCard() instanceof QuestCard) {
			//game.setQuest();
			nextPhase = "SponsorQuest";
			game.setPhase(Phase.SponsorQuest);
		}
		else {
			logger.info("Unknown card type added to story");
			nextPhase = "Broken";
			game.setPhase(Phase.Broken);
		}

		message.put("type", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
		for(WebSocketSession user : players.keySet()){
			user.sendMessage(new TextMessage(gson.toJson(message)));
		}
	}
	

	/**
	 * Handles the HandleEvent phase
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void handleEvent(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		logger.info("Handling event: " + game.getRevealedCard().getName());
		
		if(game.getRevealedCard() instanceof EventCard)
			((EventCard)game.getRevealedCard()).run(game);
		game.setPhase(Phase.TurnEnd);
		
		message.put("type", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
	}
	
	private void joinQuest(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("attempting to join quest...");
		Player sponsor = game.getCurrentSponsor();
		Player current = players.get(session);
		GameQuest quest = game.getCurrentQuest();
		
		if(quest == null || quest.isPlayer(current)) {
			logger.info("Attempting to join a quest illegally.");
			message.put("type", "ERROR");
			message.put("data", "Cannot join the quest");
			session.sendMessage(new TextMessage(gson.toJson(message)));
			return;
		}
		
		if(sponsor.getId() == current.getId()) {
			logger.info("Player attempting play in a quest when he is the Sponsor...");
			message.put("type", "ERROR");
			message.put("data", "Cannot join the quest if you are the sponsor.");
			session.sendMessage(new TextMessage(gson.toJson(message)));
			return;
		}
		
		quest.addPlayer(current);
		current.setQuest(quest);
		
		logger.info("Player " + current.getId() + " joined quest");
		message.put("type", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
		
		for(WebSocketSession user : players.keySet()) {
			user.sendMessage(new TextMessage(gson.toJson(message)));
		}
		
		
	}
	private void sponsorQuest(WebSocketSession session, Map<String, String> payload) throws Exception { 
		Gson gson = new GsonBuilder().create(); 
		Map<String, String> message = new HashMap<>(); 
		if(game.getPhase() != Phase.SponsorQuest || game.getCurrentSponsor() != null) { 
			logger.info("Player attempting to sponsor quest outside sponsor quest phase or when there is already a sponsor."); 
			message.put("type", "ERROR"); 
			message.put("data", "Cannot sponsor quest"); 
			session.sendMessage(new TextMessage(gson.toJson(message))); 
			return; 
		} 	
		
		if(game.getPlayerByIndex(game.getCurrentPlayer()).getId() != players.get(session).getId()) {  
			logger.info("Player attempting to sponsor quest illegally");  
			message.put("type", "ERROR");  
			message.put("data", "Cannot sponsor quest"); 
			session.sendMessage(new TextMessage(gson.toJson(message)));
			return;
		}
		
		if(game.getPlayerByIndex(game.getCurrentPlayer()).hasTest()) {
			if(game.getPlayerByIndex(game.getCurrentPlayer()).countFoes()+1<game.getCurrentQuest().getNumStages())
				logger.info("Player does not have enough cards to sponosr the quest");
			return;
		}else {
			if(game.getPlayerByIndex(game.getCurrentPlayer()).countFoes()<game.getCurrentQuest().getNumStages())
				return;
		}
		
		logger.info("Player can successfully sponsor the quest");
		
		game.setQuest(); 
		game.setPhase(Phase.SetupQuest); 
		
		logger.info("Quest successfully sposnored by the player");
		message.put("type", "GAME_STATE_UPDATE"); 		
		message.put("data", gson.toJson(game)); 
		
		for(WebSocketSession user : players.keySet()) { 
		 	user.sendMessage(new TextMessage(gson.toJson(message))); 
		} 
	} 
	
	private void playStage(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("attempting to join quest...");
		Player sponsor = game.getCurrentSponsor();
		Player current = players.get(session);
		GameQuest quest = game.getCurrentQuest();
		
		if(current.getId() != sponsor.getId() || quest.getPlayers().stream().anyMatch(p -> !p.isReady())) {
			logger.info("Cannot play stage yet");
			message.put("type", "ERROR");
			message.put("data", "Players not ready yet.");
			session.sendMessage(new TextMessage(gson.toJson(message)));
			return;
		}
		
		game.playCurrentQuestStage();
		
		message.put("event", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
	}

	/**
	 * Set up Quest
	 * @purpose Sets up the quest stages by the sponsor
	 * 
	 */
	private void setupQuest(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("attempting to set up quest stages...");
		Player sponsor = game.getCurrentSponsor();
		Player current = players.get(session);
		
		if(sponsor.getId() != current.getId()) {
			logger.info("Player attempting to set up quest is not the sponsor. Rejecting...");
			message.put("type", "ERROR");
			message.put("data", "Cannot set up quest stages because you are not the sponsor");
			session.sendMessage(new TextMessage(gson.toJson(message)));
			return;
		}
		
		ArrayList<AdventureCard> cards = new ArrayList<>();
		
		ArrayList<String> names = gson.fromJson(payload.get("data"), ArrayList.class);
		
		for(String name : names) {
			AdventureCard card = current.getCard(name);
			
			if(card == null) {
				logger.info("Attempting to set up quest with cards not in hand");
				message.put("type", "ERROR");
				message.put("data", "Card " + name + " does not exist in your hand!");
				session.sendMessage(new TextMessage(gson.toJson(message)));
				return;
			}
			
			cards.add(card);
		}
		
		ArrayList<Stage> stages = new ArrayList<>();
		Foe currentFoe = null;
		
		for(AdventureCard card : cards) {
			if(card instanceof Foe) {
				if(currentFoe != null) {
					stages.add(new Stage(currentFoe));
				}
				currentFoe = (Foe) card;
			}
			else if(card instanceof Tests) {
				if(currentFoe != null) {
					stages.add(new Stage(currentFoe));
					currentFoe = null;
				}
				stages.add(new Stage((Tests) card));
			}
			else if(card instanceof Weapon) {
				currentFoe.addWeapon((Weapon) card);
			}
		}
		
		if(currentFoe != null) stages.add(new Stage(currentFoe));
		
		
		stages.forEach(s -> game.getCurrentQuest().addStage(game, s));
		logger.info("Stages in the quest: " + game.getCurrentQuest().getStages().size());
		logger.info("Prepared stages for the quest: " + stages.size());
		
		message.put("type", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
		
		logger.info("Sending quest information to players");
		for(WebSocketSession user : players.keySet()) {
			user.sendMessage(new TextMessage(gson.toJson(message)));
		}		
	}
	
	/**
	 * Play quest
	 * @purpose Plays the quest by setting up the fields of players.
	 * 
	 */
	private void playQuest(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("attempting to participate in quest...");
		Player sponsor = game.getCurrentSponsor();
		Player current = players.get(session);
		GameQuest quest = game.getCurrentQuest();
		
		if(sponsor.getId() == current.getId() || quest.isPlayer(current)) {
			logger.info("Player attempting to play in quest illegally. Rejecting...");
			message.put("type", "ERROR");
			message.put("data", "Cannot play in quest.");
			session.sendMessage(new TextMessage(gson.toJson(message)));
			return;
		}
		
		if(current.isReady()) {
			logger.info("Player already set up the quest and tries to set up again.");
			message.put("type", "ERROR");
			message.put("data", "Already set up your cards for this stage!");
			session.sendMessage(new TextMessage(gson.toJson(message)));
			return;
		}
		
		ArrayList<AdventureCard> cards = new ArrayList<>();
		
		ArrayList<String> names = gson.fromJson(payload.get("data"), ArrayList.class);
		
		for(String name : names) {
			AdventureCard card = current.getCard(name);
			
			if(card == null) {
				logger.info("Attempting to play in quest with cards not in hand");
				message.put("type", "ERROR");
				message.put("data", "Card " + name + " does not exist in your hand!");
				session.sendMessage(new TextMessage(gson.toJson(message)));
				return;
			}
			
			cards.add(card);
		}
				
		for(AdventureCard card : cards) {
			if(!current.canPlayTemp(card) || 
					(!(card instanceof Ally) && !(card instanceof Amour) && !(card instanceof Weapon))) {
				message.put("type", "ERROR");
				message.put("data", "Illegal card type to play in quest");
				session.sendMessage(new TextMessage(gson.toJson(message)));
				return;
			}
			
			current.playToTemp(card);
		}

		current.setReady();
		
		message.put("type", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
		
		logger.info("Cards played in the current stage of quest");
		logger.info("Sending play quest information to players");
		for(WebSocketSession user : players.keySet()) {
			if(user != session) {
				user.sendMessage(new TextMessage(gson.toJson(message)));
			}
		}
	}
	
	/**
	 * Handles the JoinTourney phase
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void joinTourney(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		int joined = 0;
		for(int i = 0; i < game.getAllPlayers().size(); i++) {
			if(game.getPlayerByIndex(i).getAI()==null) {
				//Code for joining
					logger.info(" player " + game.getCurrentPlayer()+ "joined the tournament");
					game.getCurrentTourney().addPlayer(game.getPlayerByIndex(game.getCurrentPlayer()));
					joined++;
				}
			else {
				if(game.getPlayerByIndex(i).getAI().doIParticipateInTournament(game, game.getPlayerByIndex(i))) {
					logger.info(" player " + game.getCurrentPlayer()+ "joined the tournament");
					game.getCurrentTourney().addPlayer(game.getPlayerByIndex(game.getCurrentPlayer()));
					joined++;
				}
			}
			game.nextPlayer();
		}
		
		if(joined < 1) {
			game.setPhase(Phase.TurnEnd);
			//this.discardBeforeEnd();
			game.endTourney();
			message.put("type", "PHASE_CHANGE");
			message.put("data", "TurnEnd");
		}
		else if (joined >= 1){
			game.setPhase(Phase.RunTourney);	
			game.getCurrentTourney().dealCards();
			//way to not send whole game state?
			message.put("type", "GAME_STATE_UPDATE");
			message.put("data", gson.toJson(game));
		}
		
		for(WebSocketSession user : players.keySet()){
			user.sendMessage(new TextMessage(gson.toJson(message)));
		}
	}
	
	/**
	 * Handles the RunTourney phase
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void runTourney(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		game.getCurrentTourney().winner(game);
		
		game.setPhase(Phase.TurnEnd);
		//this.discardBeforeEnd();
		
		message.put("type", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
	}
	
	/**
	 * Handles the TurnEnd phase
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void turnEnd(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("Turn Ends");
		game.setPhase(Phase.TurnStart);
		game.nextTurn();
		
		message.put("type", "PHASE_CHANGE");
		message.put("data", "TurnStart");
	}
	
	/**
	 * Handles the GameOver phase
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void gameOver(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("Game is finished.");
		
		game.setPhase(Phase.GameOver);
		
		message.put("type", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		logger.info("Player connected to the websocket");
		players.put(session, new Player(id));	
		try {
			this.joinGame(session);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
		logger.info("Player disconnected to the websocket");
		Player player = players.remove(session);
		game.removePlayer(player);
	}
}
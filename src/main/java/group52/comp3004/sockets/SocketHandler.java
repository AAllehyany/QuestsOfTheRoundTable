package group52.comp3004.sockets;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import group52.comp3004.cards.EventCard;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.game.GameState;
import group52.comp3004.game.Phase;
import group52.comp3004.players.Player;

@Component
public class SocketHandler extends TextWebSocketHandler{
	
	final static Logger logger = Logger.getLogger(SocketHandler.class);
	private GameState game = new GameState();
	private ConcurrentHashMap<WebSocketSession, Player> players = new ConcurrentHashMap();
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage mes) throws Exception{
		Map<String, String> payload = new Gson().fromJson(mes.getPayload(), Map.class);
		String event = payload.get("event");
				
		switch(event) {
		case "JOIN_GAME":
			joinGame(session, payload);
			break;
			
		/*********Game phase handlers***********************/
		case "START_GAME":
			startGame(session, payload);
			break;
		case "TURN_START":
			turnStart(session, payload);
			break;
		case "REVEAL_STORY":
			revealStory(session, payload);
			break;
		case "HANDLE_EVENT":
			handleEvent(session, payload);
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
		message.put("event", "INVALID_EVENT");
		message.put("data", "Event invalid");
		session.sendMessage(new TextMessage(gson.toJson(message)));
		
	}
	
	/**
	 * Handles the players joining a game. No more than 4 players may join a single game.
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void joinGame(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("Player attempting to join game");
		Player player = players.get(session);
		
		if(game.getAllPlayers().size() >= 4) {
			logger.info(message);
			message.put("event", "JOIN_FAILED");
			message.put("data", "Game has too many players.");
			session.sendMessage(new TextMessage(gson.toJson(message)));
			return;
		}
		
		logger.info("Player joined game!");
		game.addPlayer(player);
		message.put("event", "JOIN_SUCCESS");
		message.put("data", "Joined game!");
		session.sendMessage(new TextMessage(gson.toJson(message)));
		
	}
	
	/**
	 * Handles the StartGame phase
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void startGame(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("Starting Game");
		//add ai players when room are made
		
		game.dealCardsToPlayers();
		game.setPhase(Phase.TurnStart);
		
		message.put("event", "GAME_STATE_UPDATE");
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
		Map<String, String> data = new HashMap<>();
		
		logger.info("New turn starts");
		
		game.setPhase(Phase.RevealStory);
		
		
		message.put("event", "PHASE_CHANGE");
		message.put("data", "");
		session.sendMessage(new TextMessage(gson.toJson(message)));
	}
	
	/**
	 * Handles the RevealStory phase
	 * @param session current session
	 * @param payload message to be sent to client 
	 * @throws Exception error in sending message 
	 */
	private void revealStory(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		Map<String, String> data = new HashMap<>();
		
		game.setRevealedCard(game.getStoryDeck().draw());
		logger.info("Next story card: " + game.getRevealedCard().getName());

		String nextPhase = "";
		if(game.getRevealedCard() instanceof EventCard) {
			nextPhase = "HandleEvent";
			game.setPhase(Phase.HandleEvent);
		}
		else if(game.getRevealedCard() instanceof Tourneys) {
			game.setTourney();
			nextPhase = "SponsorTourney";
			game.setPhase(Phase.SponsorTourney);
		}
		else if(game.getRevealedCard() instanceof QuestCard) {
			game.setQuest();
			nextPhase = "SponsorQuest";
			game.setPhase(Phase.SponsorQuest);
		}
		else {
			logger.info("Unknown card type added to story");
			nextPhase = "Broken";
			game.setPhase(Phase.Broken);
		}
	
		data.put("CardID", String.valueOf(game.getRevealedCard().getID()));
		data.put("Phase", nextPhase);
		message.put("event", "REVEAL_STORY");
		message.put("data", gson.toJson(data));
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
		
		message.put("event", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
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
			message.put("event", "PHASE_CHANGE");
			message.put("data", "TurnEnd");
		}
		else if (joined >= 1){
			game.setPhase(Phase.RunTourney);	
			game.getCurrentTourney().dealCards();
			//way to not send whole game state?
			message.put("event", "GAME_STATE_UPDATE");
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
		
		message.put("event", "GAME_STATE_UPDATE");
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
		
		message.put("event", "PHASE_CHANGE");
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
		
		message.put("event", "PHASE_CHANGE");
		message.put("data", "GAME_END");
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		logger.info("Player connected to the websocket");
		players.put(session, new Player(2));
	}
}
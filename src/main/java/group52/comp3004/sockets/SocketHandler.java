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

import group52.comp3004.game.GameState;
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
		case "SPONSOR_TOURNEY":
			sponsorTourney(session, payload);
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
	
	private void invalidEvent(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		message.put("event", "INVALID_EVENT");
		message.put("data", "Event invalid");
		session.sendMessage(new TextMessage(gson.toJson(message)));
		
	}
	
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
	
	private void startGame(WebSocketSession session, Map<String, String> payload) throws Exception {
		
	}
	
	private void turnStart(WebSocketSession session, Map<String, String> payload) throws Exception {
		
	}
	
	private void revealStory(WebSocketSession session, Map<String, String> payload) throws Exception {
		
	}
	
	private void handleEvent(WebSocketSession session, Map<String, String> payload) throws Exception {
		
	}
	
	private void sponsorTourney(WebSocketSession session, Map<String, String> payload) throws Exception {
		
	}
	
	private void joinTourney(WebSocketSession session, Map<String, String> payload) throws Exception {
		
	}
	
	private void runTourney(WebSocketSession session, Map<String, String> payload) throws Exception {
		
	}
	
	private void turnEnd(WebSocketSession session, Map<String, String> payload) throws Exception {
		
	}
	
	private void gameOver(WebSocketSession session, Map<String, String> payload) throws Exception {
		
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		logger.info("Player connected to the websocket");
		players.put(session, new Player(2));
	}
}
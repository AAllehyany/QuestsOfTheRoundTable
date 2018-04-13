package group52.comp3004.sockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
import group52.comp3004.cards.CardFactory;
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
	private static int readyCounter = 0;
	
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
			logger.info(payload.get("data"));
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
		case "SETUP_TOURNEY":
			setupTourney(session, payload);
			break;
		case "TURN_END":
			turnEnd(session, payload);
			break;
		case "GAME_OVER":
			gameOver(session, payload);
			break;			
		case "ADD_CARD_SPONSOR":
			addCardSponsor(session, payload);
			break;
			
		case "ADD_CARD_QUEST":
			addCardQuest(session, payload);
			break;
		
		case "ADD_CARD_TOURNEY":
			addCardTourney(session, payload);
			break;
		case "MERLIN":
			merlin(session, payload);
			break;
		case "CHEAT":
			cheat(session, payload);
			break;
		/********************************************************/
		default:
			invalidEvent(session, payload);
		}	
	}
	
	private void addCardSponsor(WebSocketSession session, Map<String, String> payload) throws Exception {
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
		
		String cardId = String.valueOf(payload.get("data"));
		AdventureCard card = current.getCard((int) Float.parseFloat(cardId));
		
		if(game.getPhase() == Phase.SetupQuest) {
			if(!(card instanceof Foe) && !(card instanceof Tests) && !(card instanceof Weapon)) {
				logger.info("Clicking illegal card for setting quest");
				message.put("type", "ERROR");
				message.put("data", "Illegal card type in quest");
				session.sendMessage(new TextMessage(gson.toJson(message)));
			}
			else {
				int numFoes = (int) current.getTemp().stream().filter(c -> c instanceof Foe).count();
				int numTests = (int) current.getTemp().stream().filter(c -> c instanceof Tests).count();
				
				if(card instanceof Tests && numTests > 0) {
					message.put("type", "ERROR");
					message.put("data", "Illegal card type in quest");
					session.sendMessage(new TextMessage(gson.toJson(message)));
					return;
				}
				
				if(!(card instanceof Weapon)) {
					
					if((numFoes + numTests) >= game.getCurrentQuest().getNumStages()) {
						message.put("type", "ERROR");
						message.put("data", "Too many cards in stage");
						session.sendMessage(new TextMessage(gson.toJson(message)));
						return;
					}
				}
				
				
				// check for adding weapons.
				if( card instanceof Weapon ) {
					if(current.getTempWeapons().stream().map(c -> c.getName()).collect(Collectors.toList()).contains(card.getName())) {
						message.put("type", "ERROR");
						message.put("data", "Too many weapons of same type");
						session.sendMessage(new TextMessage(gson.toJson(message)));
						return;
					}
					
					if(current.getTemp().size() > 0 && !(current.getTemp().get(current.getTemp().size() - 1) instanceof Tests)) {
						logger.info(card.getName()+ " clicked");
						current.getTemp().add(card);
						current.addTempWeapon(card);
						current.getHand().remove(card);
						
						message.put("type", "GAME_STATE_UPDATE");
						message.put("data", gson.toJson(game));
						session.sendMessage(new TextMessage(gson.toJson(message)));
					}
						
				}
				else {
					current.getTempWeapons().clear();
					logger.info(card.getName()+ " clicked");
					current.getTemp().add(card);
					current.getHand().remove(card);
					message.put("type", "GAME_STATE_UPDATE");
					message.put("data", gson.toJson(game));
					session.sendMessage(new TextMessage(gson.toJson(message)));
				}
				
			}
		}
		
	}
	
	private void addCardTourney(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		logger.info("attempting to play in tourney...");
		Player current = players.get(session);
		
		
		String cardId = String.valueOf(payload.get("data"));
		AdventureCard card = current.getCard((int) Float.parseFloat(cardId));
		
		if(!(card instanceof Ally) && !(card instanceof Amour) && !(card instanceof Weapon)) {
			return;
		}
		
		if(card instanceof Weapon) {
			if(!current.canPlayTemp(card)) return;
			logger.info(card.getName()+ " clicked");
			current.getTemp().add(card);
			current.getHand().remove(card);
			message.put("type", "GAME_STATE_UPDATE");
			message.put("data", gson.toJson(game));
			session.sendMessage(new TextMessage(gson.toJson(message)));
		}
		else {
			int countAmours = (int) current.getTemp().stream().filter(c -> c instanceof Amour).count();
			if(card instanceof Amour && countAmours > 0) return;
			
			logger.info(card.getName()+ " clicked");
			current.getTemp().add(card);
			current.getHand().remove(card);
			message.put("type", "GAME_STATE_UPDATE");
			message.put("data", gson.toJson(game));
			session.sendMessage(new TextMessage(gson.toJson(message)));
		}
		
		
	}
	
	private void addCardQuest(WebSocketSession session, Map<String, String> payload) throws Exception {
		
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("attempting to participate in quest...");
		Player sponsor = game.getCurrentSponsor();
		Player current = players.get(session);
		GameQuest quest = game.getCurrentQuest();
		
		if(sponsor.getId() == current.getId() || !quest.isPlayer(current)) {
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
		
		String cardId = String.valueOf(payload.get("data"));
		AdventureCard card = current.getCard((int) Float.parseFloat(cardId));
		
		if(!(card instanceof Ally) && !(card instanceof Amour) && !(card instanceof Weapon)) {
			logger.info("Player attempting to play in quest illegally. Rejecting...");
			message.put("type", "ERROR");
			message.put("data", "Illegal card type in quest.");
			session.sendMessage(new TextMessage(gson.toJson(message)));
			return;
		}
		
		if(card instanceof Weapon) {
			if(!current.canPlayTemp(card)) {
				logger.info("Player attempting to play in quest illegally. Rejecting...");
				message.put("type", "ERROR");
				message.put("data", "Too many weapons in same quest.");
				session.sendMessage(new TextMessage(gson.toJson(message)));
				return;
			}
			logger.info(card.getName()+ " clicked");
			current.getTemp().add(card);
			current.getHand().remove(card);
			message.put("type", "GAME_STATE_UPDATE");
			message.put("data", gson.toJson(game));
			session.sendMessage(new TextMessage(gson.toJson(message)));
		}
		else {
			int countAmours = (int) current.getTemp().stream().filter(c -> c instanceof Amour).count();
			if(card instanceof Amour && countAmours > 0) {
				logger.info("Player attempting to play in quest illegally. Rejecting...");
				message.put("type", "ERROR");
				message.put("data", "too many amours.");
				session.sendMessage(new TextMessage(gson.toJson(message)));
				return;
			}
			
			logger.info(card.getName()+ " clicked");
			current.getTemp().add(card);
			current.getHand().remove(card);
			message.put("type", "GAME_STATE_UPDATE");
			message.put("data", gson.toJson(game));
			session.sendMessage(new TextMessage(gson.toJson(message)));
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
		
		this.revealStory(session);
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
			game.setTourney();
			nextPhase = "JoinTourney";
			game.setPhase(Phase.JoinTourney);
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
		
		for(WebSocketSession user : players.keySet()) {
			user.sendMessage(new TextMessage(gson.toJson(message)));
		}
	}
	
	private void joinQuest(WebSocketSession session, Map<String, String> payload) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		logger.info("attempting to join quest...");
		Player sponsor = game.getCurrentSponsor();
		Player current = players.get(session);
		GameQuest quest = game.getCurrentQuest();
		
		String joined = String.valueOf(payload.get("joined"));
		String playerId = String.valueOf(payload.get("playerId"));
		int id = (int)Float.parseFloat(playerId);
		
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
		
		if(joined.equals("true")) {	
			game.getCurrentQuest().addPlayer(game.getPlayerById(id));
		}
		else {
			logger.info("Player: " + id + " declined the quest");
			game.getCurrentQuest().incrementDeclined();
		}
		
		System.out.println("Number of players in quest: "+ game.getCurrentQuest().getPlayers().size());
		System.out.println("Sponsor: "+ game.getCurrentQuest().getSponsor().getId());
		if(game.getCurrentQuest().getDeclined() == 4) {
			game.setPhase(Phase.TurnEnd);
			message.put("type", "PHASE_CHANGE");
			message.put("data", "TURN_END");
			for(WebSocketSession user : players.keySet()){
				user.sendMessage(new TextMessage(gson.toJson(message)));
			}
		}
		else if (game.getCurrentQuest().getReceived() == 3){
			game.setPhase(Phase.PlayQuest);
			message.put("type", "GAME_STATE_UPDATE");
			message.put("data", gson.toJson(game));
			for(WebSocketSession user : players.keySet()){
				user.sendMessage(new TextMessage(gson.toJson(message)));
			}
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
		
		Player current = players.get(session);
		
		if(!game.canSponsorQuest(current)) {
			logger.info("Player does not have enough cards to sponosr the quest");
			return;
		}
		
		logger.info("Player can successfully sponsor the quest");
		
		game.setQuest(current); 
		game.setPhase(Phase.SetupQuest); 
		
		logger.info("Quest successfully sposnored by the player");
		message.put("type", "GAME_STATE_UPDATE"); 		
		message.put("data", gson.toJson(game)); 
		
		for(WebSocketSession user : players.keySet()) { 
		 	user.sendMessage(new TextMessage(gson.toJson(message))); 
		} 
	} 
	
	private void mordred(WebSocketSession session, Map<String, String> payload) throws Exception{
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		Integer a = Integer.parseInt(payload.get("ally"));
		Integer p = Integer.parseInt(payload.get("player"));
		Integer o = Integer.parseInt(payload.get("owner"));
		
		Player owner = null;
		Player player = null;
		Ally ally = null;
		
		for(int i=0;i<game.getAllPlayers().size();i++) {
			if(game.getAllPlayers().get(i).getId()==p) player = game.getAllPlayers().get(i);
			if(game.getAllPlayers().get(i).getId()==o) owner = game.getAllPlayers().get(i);
		}
		
		for(int i=0;i<player.getField().size();i++) {
			if(player.getField().get(i).getID()==a) ally = (Ally) player.getField().get(i);
		}
		
		Foe m = CardFactory.createFoe("Mordred", 30);
		m.MordredSpecial(game, owner, p, ally);
		
		logger.info("Mordred successfully removed an ally");
		message.put("type", "GAME_STATE_UPDATE");
		message.put("data",  gson.toJson(game));
		for(WebSocketSession user : players.keySet()) 
			user.sendMessage(new TextMessage(gson.toJson(message)));
	}
	
	private void merlin(WebSocketSession session, Map<String, String> payload) throws Exception{
		Gson gson= new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		Integer s = Integer.parseInt(payload.get("stage"));
		
		logger.info("Revealing cards");
		Ally m = CardFactory.createAlly("Merlin");
		m.StartMerlinSpecial(game, s);
	}
	
	private void cheat(WebSocketSession session, Map<String, String> payload) throws Exception{
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		if(game.getCurrentQuest()==null) {
			logger.info("Not on a quest");
			return;
		}
		
		logger.info("Revealing number of cards");
		message.put("type", "CHEAT_UPDATE");
		message.put("data",  gson.toJson(game.getCurrentQuest().getStageCardNum()));
		session.sendMessage(new TextMessage(gson.toJson(message)));
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
		
		ArrayList<AdventureCard> cards = current.getTemp();
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
		if(game.getCurrentQuest().getStages().size() < stages.size() ||
				stages.size() != game.getCurrentQuest().getQuest().getStages()) {
			current.tempToHand();
			game.getCurrentQuest().clearAllStages();
			message.put("type", "GAME_STATE_UPDATE");
			message.put("data", gson.toJson(game));
			session.sendMessage(new TextMessage(gson.toJson(message)));
		}
		else {
			//cards are moved into quest
			current.getTemp().clear();
			for(int i = 0; i < stages.size(); i++) {
				Stage stage = stages.get(i);
				
				if(stage.isTestStage()) {
					game.addMiddleArea(stage.getTest());
				}
				else {
					game.addMiddleArea(stage.getFoe());
				}
			}
			game.setPhase(Phase.RunQuest);
			message.put("type", "GAME_STATE_UPDATE");
			message.put("data", gson.toJson(game));
			
			logger.info("Sending quest information to players");
			for(WebSocketSession user : players.keySet()) {
				user.sendMessage(new TextMessage(gson.toJson(message)));
			}		
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
		
		logger.info("Playing in a Quest!");
		logger.info("Num players ready: " + readyCounter);
		readyCounter++;
		logger.info("Num players ready: " + readyCounter);
		int numPlayers = quest.getPlayers().size();
		
		logger.info("Num players in quest so far: " + numPlayers);
		
		logger.info("Received player submission for quest!");
		logger.info("Moving to next player in quest!");
		//this.playQuest();
		message.put("type", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
		
		logger.info("Cards played in the current stage of quest");
		logger.info("Sending play quest information to players");
		for(WebSocketSession user : players.keySet()) {
			if(user != session) {
				user.sendMessage(new TextMessage(gson.toJson(message)));
			}
		}
		
//		if(numPlayers == readyCounter) {
//			System.out.println("Everyone is ready!");
//			game.playCurrentQuestStage();
//			readyCounter = 0;
//			if(game.getCurrentQuest().isOver()) {
//				logger.info("No one left in the quest, quest over!");
//				//this.endQuest();
//				return;
//			}
//			//middleController.setStageArrow(model.getCurrentQuest().getCurrentStage());
//			
//		}
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
		
		System.out.println(payload);
		
		String joined = String.valueOf(payload.get("joined"));
		String playerId = String.valueOf(payload.get("playerId"));
		int id = (int)Float.parseFloat(playerId);
		
		if(joined.equals("true")) {	
			game.getCurrentTourney().addPlayer(game.getPlayerById(id));
		}
		else {
			logger.info("Player: " + id + " declined the tourney");
			game.getCurrentTourney().incrementDeclined();
		}
		
		if(game.getCurrentTourney().getDeclined() == 4) {
			game.setPhase(Phase.TurnEnd);
			message.put("type", "PHASE_CHANGE");
			message.put("data", "TURN_END");
			for(WebSocketSession user : players.keySet()){
				user.sendMessage(new TextMessage(gson.toJson(message)));
			}
		}
		else if (game.getCurrentTourney().getReceived() == 4){
			game.setPhase(Phase.SetupTourney);
			message.put("type", "GAME_STATE_UPDATE");
			message.put("data", gson.toJson(game));
			for(WebSocketSession user : players.keySet()){
				user.sendMessage(new TextMessage(gson.toJson(message)));
			}
		}
	}
	
	/**Handles tournament setup
	 * @param session current session
	 */
	private void setupTourney(WebSocketSession session, Map<String, String> payload) {
		logger.info("Players setting up tourney");
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		
		System.out.println(payload);
		
		message.put("type",  "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
	}
	
	/**
	 * Handles the RunTourney phase
	 * @param session current session
	 * @throws Exception error in sending message 
	 */
	private void runTourney(WebSocketSession session) throws Exception {
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		System.out.println("Running the tournment");
		if(game.getCurrentTourney().battle(game, game.getCurrentTourney().getPlayers()).size()>1 &&
				game.getCurrentTourney().getRound()<2) {
			game.setPhase(Phase.SetUpTourney);
		}else {
			game.getCurrentTourney().winner(game);
		}
		
		game.setPhase(Phase.TurnEnd);
		//this.discardBeforeEnd();
		
		message.put("type", "GAME_STATE_UPDATE");
		message.put("data", gson.toJson(game));
		for(WebSocketSession user : players.keySet()){
			user.sendMessage(new TextMessage(gson.toJson(message)));
		}
		
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
	public void afterConnectionEstablished(WebSocketSession session) throws IOException {
		logger.info("Player connected to the websocket");
		players.put(session, new Player(id));	
		
		Gson gson = new GsonBuilder().create();
		Map<String, String> message = new HashMap<>();
		message.put("type", "RECEIVE_ID");
		message.put("data", gson.toJson(id));
		session.sendMessage(new TextMessage(gson.toJson(message)));
		
		id++;
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
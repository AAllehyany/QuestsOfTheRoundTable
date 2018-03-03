package group52.comp3004.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Arms;
import group52.comp3004.cards.EventCard;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.GameTourney;
import group52.comp3004.game.Phase;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;



public class GameController implements Initializable {

	final static Logger logger = Logger.getLogger(GameController.class);
	@FXML
	private GridPane gamepane;
	private DoubleProperty stageSize = new SimpleDoubleProperty();
	private List<PlayerAreaController> playerControllers;
	private MiddleAreaController middleController;

	@FXML
	private Button twoPlayers, threePlayers, fourPlayers,battle;
	@FXML
	private Button finishSponsor;
	
	private GameState model;
	
	private int readyCounter, readyCounter1;
	
	private int currentStageQuest;
	
	//Constructor

	public GameController() {
		model = new GameState();
//		Player demo = new Player(1, this, model);
//		Player demow = new Player(2, this, model);
//		Player demoww = new Player(3, this, model);
//		Player demowww= new Player(4, this, model);
//		model.addPlayer(demo);
//		model.addPlayer(demow);
//		model.addPlayer(demoww);
//		model.addPlayer(demowww);
		stageSize.set(0);
		playerControllers = new ArrayList<>();
		middleController = null;
		readyCounter = 0;
		readyCounter1 =0;
	}

	public DoubleProperty stageSizeProperty() {
		return stageSize;
	}
	

	//PURPOSE: Controller Initialization
	@Override
	public void initialize(URL location, ResourceBundle resources)  {
		logger.info("Game controller created");
		
		gamepane.setAlignment(Pos.CENTER);
		
		twoPlayers.setOnAction(e -> this.startGame(2));
		threePlayers.setOnAction(e -> this.startGame(3));
		fourPlayers.setOnAction(e -> this.startGame(4));
		battle.setOnAction(null);
		battle.setVisible(false);
		finishSponsor.setVisible(false);
		
		//finishSponsor.setOnAction(e -> this.handleReady());
	}
	@FXML
	private void discard() {
		logger.info("cards discarded");
		model.setPhase(Phase.TurnEnd);
		this.endTurn();
	}
	@FXML
	private void handleReady() {
		Phase phase = model.getPhase();
		logger.info("Clicking ready!");
		Player current = model.getPlayerByIndex(model.getCurrentPlayer());
		if(phase == Phase.SetupQuest) {
			
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
			
			
			stages.forEach(s -> model.getCurrentQuest().addStage(s));
			logger.info("Stages in the quest: " + model.getCurrentQuest().getStages().size());
			logger.info("Prepared stages for the quest: " + stages.size());
			if(model.getCurrentQuest().getStages().size() < stages.size() ||
					stages.size() != model.getCurrentQuest().getQuest().getStages()) {
				current.tempToHand();
				model.getCurrentQuest().clearAllStages();
				this.updateAll();
			}
			else {
				//move temp cards into middle area
				for(int i = 0; i < model.getCurrentQuest().getStages().size(); i++) {
					middleController.addStage(stages.get(i), model.getResourceManager());
					
				}
				
				//cards are moved into quest
				current.getTemp().clear();
				this.updateAll();
				this.runQuest();
			}
		} else if(phase == Phase.PlayQuest) {
			logger.info("Playing in a Quest!");
			logger.info("Num players ready: " + readyCounter);
			readyCounter++;
			logger.info("Num players ready: " + readyCounter);
			GameQuest quest = model.getCurrentQuest();
			int numPlayers = quest.getPlayers().size();
			
			logger.info("Num players in quest so far: " + numPlayers);
			if(numPlayers == readyCounter) {
				System.out.println("Everyone is ready!");
				model.playCurrentQuestStage();
				readyCounter = 0;
				if(model.getCurrentQuest().isOver()) {
					logger.info("No one left in the quest, quest over!");
					this.endQuest();
					this.updateAll();
					return;
				}
				middleController.setStageArrow(model.getCurrentQuest().getCurrentStage());
				
			}
			
			logger.info("Received player submission for quest!");
			logger.info("Moving to next player in quest!");
			model.nextPlayer();
			this.playQuest();
			
		}
	}

	@FXML
	private void battle() {
		Phase phase = model.getPhase();
		logger.info("ready for battle!");
		if(phase == Phase.SetUpTourney) {
			logger.info("Playing in a Tournament!");
			logger.info("Num players ready: " + readyCounter1);
			readyCounter1++;
			logger.info("Num players ready: " + readyCounter1);
			GameTourney tourney = model.getCurrentTourney();
			int numPlayers = tourney.getPlayers().size();
			
			logger.info("Num players in tourney so far: " + numPlayers);
			if(numPlayers == readyCounter1) {
				logger.info("Everyone is ready!");
				model.getCurrentTourney().battle(model.getCurrentTourney().getPlayers());
				model.getCurrentTourney().awardShields();
				readyCounter1 = 0;
				this.endTourney();
				this.updateAll();
				if(model.getCurrentTourney().isOver()) {
					logger.info("tournament over!");
					this.endTourney();
					this.updateAll();
					return;
				}
			}
			
			logger.info("Received player submission for tournament!");
			logger.info("Moving to next player in tourney!");
			model.nextPlayer();
			Player p = model.getPlayerByIndex(model.getCurrentPlayer());
			while(!(model.getCurrentTourney().isPlayer(p))) {
				logger.info("Player not in a tournament, moving to next one!");
				model.nextPlayer();
				p = model.getPlayerByIndex(model.getCurrentPlayer());
			}
			
			this.updateAll();
		}
	}
	
	/*****************************************************************************************************/
	//GAME STATE EXECUTION METHODS
	/*****************************************************************************************************/
	//PURPOSE: Execute the start game phase
	//		*adds all play areas to the game
	//		*move to phase TurnStart after
	public void startGame(int p) {
		logger.info("Starting Game");
		//remove the startGame button
		threePlayers.setOnAction(null);
		threePlayers.setVisible(false);
		fourPlayers.setOnAction(null);	
		fourPlayers.setVisible(false);
		twoPlayers.setOnAction(null);
		twoPlayers.setVisible(false);
		
		int AICounter = 0;
		// create players
		for(int i=0;i<p;i++) {
			model.addPlayer(new Player(i, this, model));
			if(i==p-1 && AICounter==i) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Player " + i);
				alert.setHeaderText("Would you like this player to be a human or an AI?");
				alert.setContentText("Select your option.");
				ButtonType human = new ButtonType("Human");
				ButtonType cancel = new ButtonType("Cancel");
				alert.getButtonTypes().setAll(human, cancel);
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == human) model.getPlayerByIndex(i).setAI(0);
				else this.endGame();
			}else{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Player " + i);
				alert.setHeaderText("Would you like this player to be a human or an AI?");
				alert.setContentText("Select your option.");
				ButtonType human = new ButtonType("Human");
				ButtonType strategy1 = new ButtonType("Strategy 1");
				ButtonType strategy2 = new ButtonType("Strategy 2");
				ButtonType cancel = new ButtonType("Cancel");
				alert.getButtonTypes().setAll(human, strategy1, strategy2, cancel);
				Optional<ButtonType> result = alert.showAndWait();
				if(result.get() == human) model.getPlayerByIndex(i).setAI(0);
				else if(result.get()==strategy1) {
					model.getPlayerByIndex(i).setAI(1);
					AICounter++;
				}
				else if(result.get()==strategy2) {
					model.getPlayerByIndex(i).setAI(2);
					AICounter++;
				}
				else this.endGame();
			}
		}
		
		//call GUI creation methods
		this.createMiddleArea();
		this.createPlayerAreas();
		this.addAdventureDeck();
		this.addStoryDeck();

		this.updateInfo();
		this.model.dealCardsToPlayers();
		//update all controllers
		this.updateAll();
		//move into normal game loop
		model.setPhase(Phase.TurnStart);
		this.startTurn();
	}
	
	//PURPOSE: Execute TurnStart Phase
	//		*placeholder if we want to add future behaviour
	public void startTurn() {
		//move to next phase
		updateAll();
		model.setPhase(Phase.RevealStory);
		this.revealStory();
	}

	//PURPOSE: Execute RevealStory Phase
	//		*Model deals a story card from deck
	//		*story card is added to middle area
	//		*next phase depends on type of card dealt
	public void revealStory() {
		//reveal story card
		middleController.reset();
		middleController.addStory(model.dealStoryCard());
		//move to next phase depending on card type
		if(model.getRevealedCard() instanceof EventCard) {System.out.println("    -->Event");
			if(model.getRevealedCard().getName()=="Call_to_Arms") {
				model.setPhase(Phase.Arms);
				this.handleArms();
			}else {
				model.setPhase(Phase.HandleEvent);
				this.handleEvent();
			}

		}

		else if(model.getRevealedCard() instanceof Tourneys) {System.out.println("    -->Tourney");
			model.setPhase(Phase.SponsorTourney);
			model.setTourney();
			this.sponsorTourney();
		}
		else if(model.getRevealedCard() instanceof QuestCard) {System.out.println("    -->Quest");
			model.setPhase(Phase.SponsorQuest);
			model.setQuest();
			this.sponsorQuest();
		}
		else {
			System.out.println("Unknown card type added to story");
			model.setPhase(Phase.Broken);
		}
		//this.updateAll();
	}

	private void handleArms() {
		
		
	}

	//PURPOSE: Execute the event behaviour contained in the event card
	public void handleEvent() {
		if(model.getRevealedCard() instanceof EventCard)
			((EventCard)model.getRevealedCard()).run(model);
		//move to next phase
		model.setPhase(Phase.TurnEnd);
		this.endTurn();
	}
	//PURPOSE: Execute SponsorTourney Phase
	public void sponsorTourney() {
		//move to next phase
		model.setPhase(Phase.JoinTourney);
		this.joinTourney();
	}
	//PURPOSE: Execute JoinTourney Phase
	public void joinTourney() {
		model.setTourney();
		int joined = 0;
		for(int i = 0; i < model.getAllPlayers().size(); i++) {
			if(model.getPlayerByIndex(i).getAI()==null) {
				Optional<ButtonType> result = makeAlertBox("tournament", "Tournament " + model.getRevealed().getName(),
						"Do you want to join the tournament, player " + model.getCurrentPlayer() + "?");
				if (result.get() == ButtonType.OK){
					joined++;
					logger.info(" player " + model.getCurrentPlayer()+ "joined the tournament");
					model.getCurrentTourney().addPlayer(model.getPlayerByIndex(model.getCurrentPlayer()));
					model.setPhase(Phase.JoinTourney);
				}
			}else {
				if(model.getPlayerByIndex(i).getAI().doIParticipateInTournament(model)) {
					logger.info(" player " + model.getCurrentPlayer()+ "joined the tournament");
					model.getCurrentTourney().addPlayer(model.getPlayerByIndex(model.getCurrentPlayer()));
					model.setPhase(Phase.JoinTourney);
					joined++;
				}
			}
			model.nextPlayer();
		}
		
		if(joined < 1) {
			this.endTurn();
			return;
		}
		
		model.getCurrentTourney().dealCards();
		updateAll();
		this.setUpTourney();
	}

	private void setUpTourney() {
		logger.info("          ->Setup Tourney");
		model.setPhase(Phase.SetUpTourney);
	}

	//PURPOSE: Execute RunTourney Phase
	public void runTourney() {
		//move to next phase
		model.setPhase(Phase.TurnEnd);
		this.endTurn();
	}
	//PURPOSE: Execute SponsorQuest Phase
	public void sponsorQuest() {
		
		finishSponsor.setVisible(true);
		
		boolean sponsored = false;
		
		for(int i = 0; i < model.getAllPlayers().size(); i++) {
			Player player = model.getPlayerByIndex(model.getCurrentPlayer());
			if(player.getAI()==null) {
				boolean numTests = player.hasTest();
				int numFoes = player.countFoes();
				if(numTests) numFoes++;
				if(numFoes>=((QuestCard) model.getRevealed()).getStages()){
					Optional<ButtonType> result = makeAlertBox("Quest sponsoring", "Quest " + model.getRevealed().getName(),
							"Do you want to sponsor the quest, player " + player.getId() + "?");
			
					if (result.get() == ButtonType.OK){
						System.out.println("Quest sponsored by player " + model.getCurrentPlayer());
						model.setQuest();
						this.setupQuest();
						sponsored = true;
					    break;
					}
				}
			}else {
				if(player.getAI().doISponsorQuest(model, player)){
					System.out.println("Quest sponsored by player " + model.getCurrentPlayer());
					ArrayList<Stage> quest = player.getAI().createQuest(model, player);
					model.setQuest();
					quest.forEach(s->model.getCurrentQuest().addStage(s));
					this.updateAll();
					this.runQuest();
					sponsored = true;
				    break;
				}
			}
			model.nextPlayer();
			this.updateAll();
		}
		
		if(!sponsored) this.endTurn();
	}

	//PURPOSE: Execute SetupQuest Phase
	public void setupQuest() {
		logger.info("          ->Setup Quest");
		model.setPhase(Phase.SetupQuest);
	}

	//PURPOSE: Execute RunQuest Phase
	public void runQuest() {
		//remove the ready button
		//finishSponsor.setOnAction(null);
		//finishSponsor.setVisible(false);
		logger.info("          ->Run Quest");
		//Players play cards into quest
		int joined = 0;
		model.setPhase(Phase.RunQuest);
		for(int i = 0; i < model.getAllPlayers().size(); i++) {
			if(model.getCurrentPlayer() == model.getSponsorIndex()) {
				model.nextPlayer();
				continue;
			}
			Player player = model.getPlayerByIndex(model.getCurrentPlayer());
			if(player.getAI()==null) {
				Optional<ButtonType> result = makeAlertBox("Quest Joining", "Quest " + model.getCurrentQuest().getQuest().getName(),
						"Do you want to play in the quest, player " + player.getId() + "?");
	
				if (result.get() == ButtonType.OK){
					model.joinQuest();
					joined += 1;
				    //break;
				}
			}else {
				if(player.getAI().doIParticipateInQuest(model, player)){
					model.joinQuest();
					joined += 1;
				    //break;
				}
			}
			model.nextPlayer();
			this.updateAll();
		}
		
		if(joined == 0) {
			this.endQuest();
		} else {
			middleController.setStageArrow(model.getCurrentQuest().getCurrentStage());
			this.playQuest();
			
		}
		
	}

	public void playQuest() {	
		//move the stage indicator one over
		//middleController.setStageArrow(model.getCurrentQuest().getCurrentStage());
		
		ArrayList<Player> players = new ArrayList<Player>(model.getCurrentQuest().getPlayers());
		for(int i=0;i<players.size();i++) {
			players.get(i).addCardToHand(model.getAdventureDeck().drawCard());
		}
		if(model.getCurrentQuest().getStages().get(model.getCurrentQuest().getCurrentStage()).isTestStage()) {
			int stoppedBidding = 0;
			while(stoppedBidding < model.getCurrentQuest().getPlayers().size()) {
				model.nextPlayer();
				Player p = model.getPlayerByIndex(model.getCurrentPlayer());
				while(!(model.getCurrentQuest().isPlayer(p))) {
					System.out.println("Player not in a quest, moving to next one!");
					model.nextPlayer();
					p = model.getPlayerByIndex(model.getCurrentPlayer());
				}
				
				if(p.getAI()==null) {
					TextInputDialog dialog = new TextInputDialog("");
					dialog.setTitle("Test Quest Stage");
					dialog.setHeaderText("Playing in a test quest stage. Max bid is: " + model.getMaxBid());
					dialog.setContentText("Enter a bid higher than max bid, Player " + p.getId() + ":");
	
					// Traditional way to get the response value.
					Optional<String> result = dialog.showAndWait();
					if (result.isPresent()){
					   int numOffer = Integer.parseInt(result.get());
					   boolean stopped = false;
					   while(numOffer <= model.getMaxBid() || numOffer > p.getHand().size()) {
						    TextInputDialog dialog1 = new TextInputDialog("");
							dialog.setTitle("Test Quest Stage");
							dialog.setHeaderText("Playing in a test quest stage. Max bid is: " + model.getMaxBid());
							dialog.setContentText("You entered an invalid bid. Enter a bid higher than max bid, Player " + p.getId() + ":");
	
							// Traditional way to get the response value.
							Optional<String> result2 = dialog.showAndWait();
							
							if(result2.isPresent()) {
								numOffer = Integer.parseInt(result2.get());
							}
							else {
								stopped = true;
							}
					   }
					   
					   if(stopped) {
						   model.playerStopBidding();
						   stoppedBidding++;
					   }
					   else {
						   model.bidCards(numOffer);
					   }
					} else {
						model.playerStopBidding();
						stoppedBidding++;
					}
				}else {
					int AIBid = p.getAI().nextBid(model, p);
					if(AIBid>model.getMaxBid()) {
						model.bidCards(AIBid);
					}else {
						model.playerStopBidding();
						stoppedBidding++;
					}
				}
			}
			
			GameQuest quest = model.getCurrentQuest();
			System.out.println("Playing a test stage");
			model.playCurrentQuestStage();
			if(model.getCurrentQuest().isOver()) {
				logger.info("No one left, quest over!");
				this.endQuest();
				this.updateAll();
				return;
			}
			
			model.setPhase(Phase.DiscardForTest);
		}
		else {
			model.setPhase(Phase.PlayQuest);
			Player p = model.getPlayerByIndex(model.getCurrentPlayer());
			while(!(model.getCurrentQuest().isPlayer(p))) {
				logger.info("Player not in a quest, moving to next one!");
				model.nextPlayer();
				p = model.getPlayerByIndex(model.getCurrentPlayer());
			}
		}
		
		
		this.updateAll();
		
	}

	//PURPOSE: Execute EndQuest Phase

	public void endTourney() {
		model.endTourney();
		for(Player p: model.getAllPlayers()) {
			p.clearTemp();
		}
		model.setPhase(Phase.TurnEnd);
		this.readyCounter1=0;
		this.updateAll();
		this.endTurn();
	}
	public void endQuest() {
		model.endQuest();
		//move to next phase
		model.setPhase(Phase.TurnEnd);
		this.readyCounter=0;
		this.updateAll();
		this.endTurn();
	}

	//PURPOSE: Execute TurnEnd Phase
	public void endTurn() {
//		while(model.getAllPlayers().get(model.getCurrentPlayer()).getHand().size()>12) {
//			// discard cards until 12 in hand
//		}
		middleController.reset();
		//move to next phase
		model.setPhase(Phase.TurnStart);
		model.nextTurn();
		this.updateAll();
		this.startTurn();
	}	

	//PURPOSE: Execute GameOver Phase
	public void endGame() {
		
	}


	/*********************************************************************************************/
	//UTILITY METHODS
	/*********************************************************************************************/

	public void playCard(AdventureCard card) {
		
	}



	//CLICK EVENT: A card in the hand has been clicked
	public void handCardOnClick(AdventureCard card, int index) {
		logger.info("Clicked card: " + card.getName());
		if(!(card instanceof Ally)) return;
		Player player = this.model.getPlayerByIndex(index);
		player.playCardToField((Ally) card);
		this.playerControllers.get(index).update(player.getHand(),player.getField(), player);
	}

	public void discardOnClick(AdventureCard card, int index) {
		if(model.getPhase()==Phase.HandleEvent) {
			logger.info("Discarded card: " + card.getName());
			Player player =this.model.getPlayerByIndex(index);
			player.discard(card);
			model.getAdventureDeck().discardCard(card);
			this.playerControllers.get(index).update(player.getHand(),player.getField(), player);
		}
	}

	//CLICK EVENT: Card dealt from adventure deck
	public void dealPlayer(int index) {
		logger.info("Dealt card");
		this.model.dealToPlayer(index);
		Player player = this.model.getPlayerByIndex(index);
		this.playerControllers.get(index).update(player.getHand(),player.getField(),player);
	}

	private Optional<ButtonType> makeAlertBox(String title, String header, String context) {
		
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(context);

		Optional<ButtonType> result = alert.showAndWait();
		
		return result;
	}

	//
	public void updateInfo() {
		for(int i = 0; i < model.numPlayers(); i++) {
			Player player = this.model.getPlayerByIndex(i);
			this.playerControllers.get(i).updateInfo(player);
		}
	}



	//PURPOSE: Update cards for all controllers in game

	public void updateAll() {
		//update player hand and field arraylists for each player
		for(int i = 0; i < this.playerControllers.size(); i++) {
			if(model.getCurrentPlayer()!=i) {
				for(AdventureCard c :model.getPlayerByIndex(i).getHand()) {
					c.flipDown();
				}
			}else {
				for(AdventureCard c :model.getPlayerByIndex(i).getHand()) {
					c.flipUp();
				}
			}
		}
		for(int i = 0; i < this.playerControllers.size(); i++) {
			int index = (model.getCurrentPlayer() + i) % model.getAllPlayers().size();
			Player player = this.model.getPlayerByIndex(index);
			ArrayList<AdventureCard> field = new ArrayList<>();
			field.addAll(player.getField());
			field.addAll(player.getTemp());
			
			this.playerControllers.get(i).update(player.getHand(),field,player);
		}
		//update middle area
		//middleController.setStoryCard(model.getRevealedCard());

	}	

	

	/*********************************************************************************************/

	//GUI Initialization Methods

	/*********************************************************************************************/

	//PURPOSE: Load in the FXML files for the middle area

	public void createMiddleArea() {
		try {
			FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/fxml/middle_area.fxml"));
			StackPane middle = loader1.load();
			gamepane.add(middle, 2, 2, 4, 2);
			middleController = loader1.getController();
		}

		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	//PURPOSE: Load in the FXML files for the player areas

	public void createPlayerAreas() {
		for(int i = 0; i < model.numPlayers(); i++) {
			final int index = i;
			try {
				FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/fxml/player_area.fxml"));
				GridPane player = loader2.load();
				PlayerAreaController controller = loader2.getController();
				//controller.setHandClickBehaviour(card -> handCardOnClick((AdventureCard) card, index));
				this.playerControllers.add(controller);
				//add the player areas to the 
				if(index == 0) gamepane.add(player, 1, 6, 4, 2);
				else if(index == 1) {	
					player.getTransforms().add(new Rotate(90, Rotate.Z_AXIS));
					gamepane.add(player, 2, 0, 2, 4);
				}
				else if(index == 2) {
					player.getTransforms().add(new Rotate(180, Rotate.Z_AXIS));
					gamepane.add(player, 7, 3, 4, 2);
				}
				else if(index == 3) {
					player.getTransforms().add(new Rotate(270, Rotate.Z_AXIS));
					player.getTransforms().add(new Translate(-140, 0));
					gamepane.add(player, 6, 6, 1, 4);
				}
				
				Button readyButton = new Button("Ready");
				readyButton.setOnAction(e -> this.handleReady());
				gamepane.add(readyButton, 1, 4, 1, 1);
				Button readyBattle = new Button("Ready for battle");
				readyBattle.setOnAction(e -> this.battle());
				gamepane.add(readyBattle, 6, 4, 1, 1);
				Button discardButton = new Button("Discard");
				discardButton.setOnAction(e -> this.discard());
				gamepane.add(discardButton, 7, 4, 1, 1);
			}

			catch(Exception ex) {
				logger.fatal(ex.getMessage());
			}
		}
	}

	//PURPOSE: add Adventure Deck GUI item
	public void addAdventureDeck() {
		AdventureCard adventureDeck = new AdventureCard("Sword", model.getResourceManager());
		adventureDeck.setFaceDown();
		StackPane aPane = new StackPane();
		aPane.setAlignment(Pos.CENTER);
		aPane.getChildren().add(adventureDeck);
		gamepane.add(adventureDeck, 0, 5);
	}

	//PURPOSE: add Story Deck GUI item
	//	-->Incomplete
	public void addStoryDeck() {
		StoryCard storyDeck = new StoryCard("Boar_Hunt", model.getResourceManager());
		storyDeck.setFaceDown();
		HBox sPane = new HBox();
		sPane.setAlignment(Pos.CENTER);
		sPane.getChildren().add(storyDeck);
		gamepane.add(storyDeck, 7, 5);	
	}

}
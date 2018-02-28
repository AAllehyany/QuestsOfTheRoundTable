package group52.comp3004.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.EventCard;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;



public class GameController implements Initializable {

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
	
	private int readyCounter;
	
	//Constructor

	public GameController() {
		model = new GameState();
		Player demo = new Player(1, this, model);
		Player demow = new Player(2, this, model);
		Player demoww = new Player(3, this, model);
		Player demowww= new Player(4, this, model);
		model.addPlayer(demo);
		model.addPlayer(demow);
		model.addPlayer(demoww);
		model.addPlayer(demowww);
		stageSize.set(0);
		playerControllers = new ArrayList<>();
		middleController = null;
		readyCounter = 0;
	}

	public DoubleProperty stageSizeProperty() {
		return stageSize;
	}
	

	//PURPOSE: Controller Initialization
	@Override
	public void initialize(URL location, ResourceBundle resources)  {
		System.out.println("Game controller created");
		
		gamepane.setAlignment(Pos.CENTER);
		
		twoPlayers.setOnAction(e -> this.startGame());
		threePlayers.setOnAction(e -> this.startGame());
		fourPlayers.setOnAction(e -> this.startGame());
		battle.setVisible(false);
		
		//finishSponsor.setOnAction(e -> this.handleReady());
	}

	@FXML
	private void handleReady() {
		Phase phase = model.getPhase();
		System.out.println("Clicking ready!");
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
			System.out.println("Stages: " + model.getCurrentQuest().getStages().size());
			System.out.println("Prepared: " + stages.size());
			if(model.getCurrentQuest().getStages().size() < stages.size() ||
					stages.size() != model.getCurrentQuest().getQuest().getStages()) {
				current.tempToHand();
				model.getCurrentQuest().clearAllStages();
				this.updateAll();
			}
			else {
				current.getTemp().clear();
				this.updateAll();
				this.runQuest();
			}
		} else if(phase == Phase.PlayQuest) {
			System.out.println("Playing in a Quest!");
			System.out.println("Num players ready: " + readyCounter);
			readyCounter++;
			System.out.println("Num players ready: " + readyCounter);
			GameQuest quest = model.getCurrentQuest();
			int numPlayers = quest.getPlayers().size();
			
			System.out.println("Num players in quest so far: " + numPlayers);
			if(numPlayers == readyCounter) {
				System.out.println("Everyone is ready!");
				model.playCurrentQuestStage();
				readyCounter = 0;
				if(model.getCurrentQuest().isOver()) {
					System.out.println("No one left, quest over!");
					this.endQuest();
					this.updateAll();
					return;
				}
			}
			
			System.out.println("Received player submission for quest!");
			System.out.println("Moving to next player in quest!");
			model.nextPlayer();
			Player p = model.getPlayerByIndex(model.getCurrentPlayer());
			while(!(model.getCurrentQuest().isPlayer(p))) {
				System.out.println("Player not in a quest, moving to next one!");
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
	public void startGame() {
		System.out.println("Starting Game");
		//remove the startGame button
		threePlayers.setOnAction(null);
		threePlayers.setVisible(false);
		fourPlayers.setOnAction(null);	
		fourPlayers.setVisible(false);
		twoPlayers.setOnAction(null);
		twoPlayers.setVisible(false);

		
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
			model.setPhase(Phase.HandleEvent);
			this.handleEvent();
		}

		else if(model.getRevealedCard() instanceof Tourneys) {System.out.println("    -->Tourney");
			model.setPhase(Phase.SponsorTourney);
			this.sponsorTourney();
		}
		else if(model.getRevealedCard() instanceof QuestCard) {System.out.println("    -->Quest");
			model.setPhase(Phase.SponsorQuest);
			this.sponsorQuest();
		}
		else {
			System.out.println("Unknown card type added to story");
			model.setPhase(Phase.Broken);
		}
		//this.updateAll();
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
		for(int i = 0; i < model.getAllPlayers().size(); i++) {
			Optional<ButtonType> result = makeAlertBox("tournament", "Tournament " + model.getRevealed().getName(),
					"Do you want to join the tournament, player " + model.getCurrentPlayer() + "?");
			if (result.get() == ButtonType.OK){
				System.out.println(" player " + model.getCurrentPlayer()+ "joined the tournament");
				model.getCurrentTourney().addPlayer(model.getPlayerByIndex(model.getCurrentPlayer()));
				model.setPhase(Phase.JoinTourney);
			}
			model.nextPlayer();
		}
		model.setPhase(Phase.SetUpTourney);
		this.setUpTourney();
	}

	private void setUpTourney() {
		battle.setVisible(true);
		battle.setOnAction(e ->model.nextPlayer());
	}

	//PURPOSE: Execute RunTourney Phase
	public void runTourney() {
		//move to next phase
		model.setPhase(Phase.TurnEnd);
		this.endTurn();
	}
	//PURPOSE: Execute SponsorQuest Phase
	public void sponsorQuest() {
		
		boolean sponsored = false;
		
		for(int i = 0; i < model.getAllPlayers().size(); i++) {
			Player player = model.getPlayerByIndex(model.getCurrentPlayer());
			Optional<ButtonType> result = makeAlertBox("Quest sponsoring", "Quest " + model.getRevealed().getName(),
					"Do you want to sponsor the quest, player " + player.getId() + "?");

			if (result.get() == ButtonType.OK){
				System.out.println("Quest sponsored by player " + model.getCurrentPlayer());
				model.setQuest();
				this.setupQuest();
				sponsored = true;
			    break;
			}
			model.nextPlayer();
			this.updateAll();
		}
		
		if(!sponsored) this.endTurn();
	}

	//PURPOSE: Execute SetupQuest Phase
	public void setupQuest() {
		System.out.println("          ->Setup Quest");
		model.setPhase(Phase.SetupQuest);
	}

	//PURPOSE: Execute RunQuest Phase
	public void runQuest() {
		//remove the ready button
		//finishSponsor.setOnAction(null);
		//finishSponsor.setVisible(false);
		System.out.println("          ->Run Quest");
		//Players play cards into quest
		int joined = 0;
		model.setPhase(Phase.RunQuest);
		for(int i = 0; i < model.getAllPlayers().size(); i++) {
			if(model.getCurrentPlayer() == model.getSponsorIndex()) {
				model.nextPlayer();
				continue;
			}
			Player player = model.getPlayerByIndex(model.getCurrentPlayer());
			Optional<ButtonType> result = makeAlertBox("Quest Joining", "Quest " + model.getCurrentQuest().getQuest().getName(),
					"Do you want to play in the quest, player " + player.getId() + "?");

			if (result.get() == ButtonType.OK){
				model.joinQuest();
				joined += 1;
			    //break;
			}
			model.nextPlayer();
			this.updateAll();
		}
		
		if(joined == 0) {
			this.endQuest();
		} else {
			this.playQuest();
			
		}
		
	}

	public void playQuest() {
		model.setPhase(Phase.PlayQuest);
		Player p = model.getPlayerByIndex(model.getCurrentPlayer());
		while(!(model.getCurrentQuest().isPlayer(p))) {
			System.out.println("Player not in a quest, moving to next one!");
			model.nextPlayer();
			p = model.getPlayerByIndex(model.getCurrentPlayer());
		}
		
		this.updateAll();
		
	}

	//PURPOSE: Execute EndQuest Phase

	public void endQuest() {
		model.endQuest();
		//move to next phase
		model.setPhase(Phase.TurnEnd);
		this.updateAll();
		this.endTurn();
	}

	//PURPOSE: Execute TurnEnd Phase
	public void endTurn() {
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
		System.out.println("Clicked card: " + card.getName());
		if(!(card instanceof Ally)) return;
		Player player = this.model.getPlayerByIndex(index);
		player.playCardToField((Ally) card);
		this.playerControllers.get(index).update(player.getHand(),player.getField(), player);
	}

	public void discardOnClick(AdventureCard card, int index) {
		if(model.getPhase()==Phase.HandleEvent) {
			System.out.println("Discarded card: " + card.getName());
			Player player =this.model.getPlayerByIndex(index);
			player.discard(card);
			this.playerControllers.get(index).update(player.getHand(),player.getField(), player);
		}
	}

	//CLICK EVENT: Card dealt from adventure deck
	public void dealPlayer(int index) {
		System.out.println("Dealt card");
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
			}

			catch(Exception ex) {
				System.out.println(ex.getMessage());
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
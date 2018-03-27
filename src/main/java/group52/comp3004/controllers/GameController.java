package group52.comp3004.controllers;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.EventCard;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.cards.Weapon;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import group52.comp3004.game.GameTourney;
import group52.comp3004.game.Phase;
import group52.comp3004.game.Stage;
import group52.comp3004.players.Player;

public class GameController{

	final static Logger logger = Logger.getLogger(GameController.class);

	private GameState model;
	
	private int readyCounter, readyCounter1;
	
	private int currentStageQuest;
	
	//Constructor
	public GameController() {
		readyCounter = 0;
		readyCounter1 =0;
	}

	private void discard() {
		if(model.getPhase()==Phase.DiscardForTest) {
			for(int i = 0; i < model.getAllPlayers().size(); i++) {
				if(model.getPlayerByIndex(model.getCurrentPlayer()).getHand().size()>12) {
					logger.info("has to discard card");
				}else {
					model.nextPlayer();
				}
			}
		}
			logger.info("cards discarded");
			model.setPhase(Phase.TurnEnd);
			this.endTurn();

	}

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
			}
			
			logger.info("Received player submission for quest!");
			logger.info("Moving to next player in quest!");
			model.nextPlayer();
			this.playQuest();
			
		}
	}

	private void battle() {
		Phase phase = model.getPhase();
		logger.info("ready for battle!");
		if(phase == Phase.SetUpTourney) {
			if(model.getCurrentTourney().getPlayers().size()==1) {
				System.out.println("Only 1 player joined the tournament, he wins");
				model.getCurrentTourney().awardShields();
				this.endTourney();
			}else{
				logger.info("Playing in a Tournament!");
				logger.info("Num players ready: " + readyCounter1);
				readyCounter1++;
				logger.info("Num players ready: " + readyCounter1);
				GameTourney tourney = model.getCurrentTourney();
				int numPlayers = tourney.getPlayers().size();
				
				logger.info("Num players in tourney so far: " + numPlayers);
				if(numPlayers == readyCounter1) {
					logger.info("Everyone is ready!");
					model.getCurrentTourney().winner();
					model.getCurrentTourney().awardShields();
					readyCounter1 = 0;
					model.getCurrentTourney().end();
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
		
		int AICounter = 0;
		// create players
		for(int i=0;i<p;i++) {
			model.addPlayer(new Player(i, this, model));
			if(i==p-1 && AICounter==i) {

			}else{
			/*	if(result.get() == human) model.getPlayerByIndex(i).setAI(0);
				else if(result.get()==strategy1) {
					model.getPlayerByIndex(i).setAI(1);
					AICounter++;
				}
				else if(result.get()==strategy2) {
					model.getPlayerByIndex(i).setAI(2);
					AICounter++;
				}
				else this.endGame();*/
			}
		}

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
		logger.info("player" +model.getCurrentPlayer() + "needs to discard two allies or one weapon, hit discard when done");
		model.setPhase(Phase.Arms);
		this.updateAll();
	}

	//PURPOSE: Execute the event behaviour contained in the event card
	public void handleEvent() {
		if(model.getRevealedCard() instanceof EventCard)
			((EventCard)model.getRevealedCard()).run(model);
		//move to next phase
		model.setPhase(Phase.TurnEnd);
		this.discardBeforeEnd();
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
				//Code for joining
					joined++;
					logger.info(" player " + model.getCurrentPlayer()+ "joined the tournament");
					model.getCurrentTourney().addPlayer(model.getPlayerByIndex(model.getCurrentPlayer()));
					model.setPhase(Phase.JoinTourney);
				}
			else {
				if(model.getPlayerByIndex(i).getAI().doIParticipateInTournament(model, model.getPlayerByIndex(i))) {
					logger.info(" player " + model.getCurrentPlayer()+ "joined the tournament");
					model.getCurrentTourney().addPlayer(model.getPlayerByIndex(model.getCurrentPlayer()));
					model.setPhase(Phase.JoinTourney);
					joined++;
				}
			}
			model.nextPlayer();
		}
		if(joined < 1) {
			this.discardBeforeEnd();
			return;
		}
		
		model.getCurrentTourney().dealCards();
		updateAll();
		this.setUpTourney();
	}

	private void setUpTourney() {
		if(model.getCurrentTourney().getPlayers().size()==0) {
			System.out.println("no one joins the tournament");
			this.endTourney();}
		else {
				logger.info("          ->Setup Tourney");
				model.setPhase(Phase.SetUpTourney);
			}

	}

	//PURPOSE: Execute RunTourney Phase
	public void runTourney() {
		//move to next phase
		model.setPhase(Phase.TurnEnd);
		this.discardBeforeEnd();
	}
	//PURPOSE: Execute SponsorQuest Phase
	public void sponsorQuest() {
		boolean sponsored = false;
		
		for(int i = 0; i < model.getAllPlayers().size(); i++) {
			Player player = model.getPlayerByIndex(model.getCurrentPlayer());
			if(player.getAI()==null) {
				boolean numTests = player.hasTest();
				int numFoes = player.countFoes();
				if(numTests) numFoes++;
				if(numFoes>=((QuestCard) model.getRevealed()).getStages()){
					//getting quest sponsorship
			
					//yes
						System.out.println("Quest sponsored by player " + model.getCurrentPlayer());
						model.setQuest();
						this.setupQuest();
						sponsored = true;
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
		
		if(!sponsored) this.discardBeforeEnd();
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
				//get ask to play quest
	
				//yes
					model.joinQuest();
					joined += 1;
			}else {
				if(player.getAI().doIParticipateInQuest(model, player)){
					model.joinQuest();
					joined += 1;
				}
			}
			model.nextPlayer();
			this.updateAll();
		}
		
		if(joined == 0) {
			this.endQuest();
		} else {
			//move quest arrow
			this.playQuest();
			
		}
		
	}

	public void playQuest() {	
		//move the stage indicator one over
		//middleController.setStageArrow(model.getCurrentQuest().getCurrentStage());
		
		ArrayList<Player> players = new ArrayList<Player>(model.getCurrentQuest().getPlayers());
		for(int i=0;i<players.size();i++) {
			players.get(i).addCardToHand(model.getAdventureDeck().draw());
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
					/*TextInputDialog dialog = new TextInputDialog("");
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
					}*/
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
		this.discardBeforeEnd();
	}
	public void endQuest() {
		model.endQuest();
		//move to next phase
		model.setPhase(Phase.TurnEnd);
		this.readyCounter=0;
		this.updateAll();
		this.discardBeforeEnd();
	}

	//PURPOSE: Execute TurnEnd Phase
	public void discardBeforeEnd() {
		for(int i=0;i<model.getAllPlayers().size();i++) {
			if(model.getAllPlayers().get(model.getCurrentPlayer()).getHand().size()>12) {
				System.out.println("Player " + model.getCurrentPlayer() + " has more than 12 cards in hand");
				// discard cards until 12 in hand
				model.setPhase(Phase.DiscardForTest);
				
				
			}
		}
	}
	public void endTurn() {
//		while(model.getAllPlayers().get(model.getCurrentPlayer()).getHand().size()>12) {
//			// discard cards until 12 in hand
//		}
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
	}

	public void discardOnClick(AdventureCard card, int index) {
		if(model.getPhase()==Phase.HandleEvent) {
			logger.info("Discarded card: " + card.getName());
			Player player =this.model.getPlayerByIndex(index);
			player.discard(card);
			model.getAdventureDeck().discard(card);
		}
	}

	//CLICK EVENT: Card dealt from adventure deck
	public void dealPlayer(int index) {
		logger.info("Dealt card");
		this.model.dealToPlayer(index);
		Player player = this.model.getPlayerByIndex(index);
	}

	//
	public void updateInfo() {
		for(int i = 0; i < model.numPlayers(); i++) {
			Player player = this.model.getPlayerByIndex(i);
		}
	}

	//PURPOSE: Update cards for all controllers in game
	public void updateAll() {
		//update player hand and field arraylists for each player
		/*for(int i = 0; i < this.playerControllers.size(); i++) {
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
		}*/
	}	
}
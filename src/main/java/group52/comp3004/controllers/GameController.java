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
			
			
			stages.forEach(s -> model.getCurrentQuest().addStage(this.model, s));
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
					model.getCurrentTourney().winner(this.model);
					model.getCurrentTourney().awardShields();
					readyCounter1 = 0;
					model.getCurrentTourney().end(this.model);
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
		player.addField((Ally) card);
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
package group52.comp3004.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.game.GameState;
import group52.comp3004.players.Player;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class GameController implements Initializable {
	@FXML
	private GridPane gamepane;
	private DoubleProperty stageSize = new SimpleDoubleProperty();
	
	@FXML
	private PlayerAreaController player1AreaController;
	
	@FXML
	private Button dealtoplayer1;
	GameState model;
	
	//Constructor
	public GameController() {
		model = new GameState();
		Player demo = new Player(1234);
		model.addPlayer(demo);
		stageSize.set(0);
	}

	
	public DoubleProperty stageSizeProperty() {
		return stageSize;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Game controller created");
		player1AreaController.setHandClickBehaviour(card -> handCardOnClick(card));
		dealtoplayer1.setOnAction(e -> dealPlayer(0));
	}
	
	public void playCard(AdventureCard card) {
		
	}
	
	public void handCardOnClick(AdventureCard card) {
		Player player = this.model.getPlayerByIndex(0);
		player.playCardToField(card);
		this.player1AreaController.update(player.getHand(),player.getField());
	}
	
	public void dealPlayer(int index) {
		this.model.dealToPlayer(index);
		Player player = this.model.getPlayerByIndex(0);
		this.player1AreaController.update(player.getHand(),player.getField());
	}
	
	
	
}

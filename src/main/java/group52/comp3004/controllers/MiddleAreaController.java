package group52.comp3004.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import group52.comp3004.ResourceManager;
import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.cards.Tests;
import group52.comp3004.game.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class MiddleAreaController implements Initializable{
	
	@FXML
	private StackPane middlePane;
	private HBox questContainer;
	
	public MiddleAreaController() {
		super();
		questContainer = new HBox();
		questContainer.setAlignment(Pos.CENTER);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		middlePane.getChildren().add(questContainer);
		System.out.println("Middle area crated");
	}
	
	public void updateCards(ArrayList<AdventureCard> cards) {
		questContainer.getChildren().clear();
		questContainer.getChildren().addAll(cards);
	}
	
	//PURPOSE: add a stage to the middle area
	public void addStage(Stage stage, ResourceManager rm) {		
		StackPane cardPane = new StackPane();
		if(stage.isTestStage()) {
			Tests test = stage.getTest();
			cardPane.getChildren().addAll(test);
		}
		else {
			HBox bp = new HBox();
			Circle swordIcon = new Circle(20);
			swordIcon.setFill(rm.getSword());
			Text amountBP = new Text(""+stage.getTotalPower());
			amountBP.setFill(Color.WHITE);
			bp.getChildren().add(swordIcon);
			bp.getChildren().add(amountBP);
			
			StackPane foeInfo = new StackPane();
			foeInfo.getChildren().add(bp);
			
			Foe foe = stage.getFoe();
			cardPane.getChildren().addAll(foeInfo, foe);
		}		
	
		questContainer.getChildren().add(cardPane);
	}
	//PURPOSE: add the dealt story card to play
	public void addStory(StoryCard card) {
		questContainer.getChildren().add(card);
	}
	//PURPOSE: Empties the contents of the middle pane to get ready for new story
	public void reset() {
		questContainer.getChildren().clear();
	}
}

package group52.comp3004.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import group52.comp3004.ResourceManager;
import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.cards.Tests;
import group52.comp3004.game.Stage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;

public class MiddleAreaController implements Initializable{
	
	@FXML
	private StackPane middlePane;
	private HBox questContainer;
	private Rectangle currentStageArrow;
	private StackPane currentStage;
	
	private static Logger logger = Logger.getLogger(MiddleAreaController.class);
	public MiddleAreaController() {
		super();
		questContainer = new HBox();
		questContainer.setAlignment(Pos.CENTER);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		middlePane.getChildren().add(questContainer);
		logger.info("Middle area crated");
	}
	
	public void updateCards(ArrayList<AdventureCard> cards) {
		questContainer.getChildren().clear();
		questContainer.getChildren().addAll(cards);
	}
	
	//PURPOSE: add a stage to the middle area
	public void addStage(Stage stage, ResourceManager rm) {	
		//create the current stage arrow if it has been made yet
		if (currentStageArrow == null) {
			currentStageArrow = new Rectangle(20, 20);
			currentStageArrow.setFill(rm.getArrow());
			currentStageArrow.getTransforms().add(new Translate(0, 60));
		}
		//build the stage GUI
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
	
	//PURPOSE: move the current stage arrow to its new location
	public void setStageArrow(int index) {
		if(index > 2) {
			currentStage.getChildren().remove(currentStageArrow);
		}
		//first index is the quest card so move input index by one to get correct stage
		if(questContainer.getChildren().get(index+1) instanceof StackPane) {
			currentStage = (StackPane)questContainer.getChildren().get(index+1);
			currentStage.getChildren().add(currentStageArrow);
		}
		else {
			System.out.println("***ERROR: Wrong middle area element accessed***");
		}		
	}
}

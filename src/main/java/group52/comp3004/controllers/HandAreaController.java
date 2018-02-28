package group52.comp3004.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import group52.comp3004.GUI.cards.GameCard;
import group52.comp3004.cards.AdventureCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

public class HandAreaController implements Initializable{
	
	@FXML
	private HBox handContainer;

	private static Logger logger = Logger.getLogger(HandAreaController.class);
	public HandAreaController() {
		super();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.info("Field area crated");
	}
	
	public void updateCards(ArrayList<AdventureCard> cards) {
		handContainer.getChildren().clear();
		handContainer.getChildren().addAll(cards);
		
		// Change offset depending on hand size
		if(cards.size()<=8) handContainer.setPrefWidth(280);
		else handContainer.setPrefWidth(560);
		if(cards.size()>1) {
			if (handContainer.getPrefWidth()==560) {
				handContainer.setSpacing((560-cards.size()*71)/(cards.size()-1));
			}else {
				handContainer.setSpacing((280-cards.size()*71)/(cards.size()-1));
			}
		}
	}
}

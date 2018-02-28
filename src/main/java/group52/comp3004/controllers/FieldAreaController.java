package group52.comp3004.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import group52.comp3004.GUI.cards.GameCard;
import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Arms;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

public class FieldAreaController implements Initializable{

	@FXML
	private HBox fieldContainer;
	
	final static Logger logger = Logger.getLogger(FieldAreaController.class);


	/**
	 * 
	 */
	public FieldAreaController() {
		super();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		logger.info("Field area crated");
	}
	
	
	public void updateCards(ArrayList<AdventureCard> cards) {
		fieldContainer.getChildren().clear();
		fieldContainer.getChildren().addAll(cards);
		
		// Change offset depending on field size
		if(cards.size()>1) fieldContainer.setSpacing((280-cards.size()*71)/(cards.size()-1));
	}
}

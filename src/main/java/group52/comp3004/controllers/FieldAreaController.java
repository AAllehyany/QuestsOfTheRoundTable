package group52.comp3004.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import group52.comp3004.GUI.cards.GameCard;
import group52.comp3004.cards.AdventureCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

public class FieldAreaController implements Initializable{

	@FXML
	private HBox fieldContainer;


	/**
	 * 
	 */
	public FieldAreaController() {
		super();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Field area crated");
	}
	
	
	public void updateCards(ArrayList<AdventureCard> cards) {
		fieldContainer.getChildren().clear();
		fieldContainer.getChildren().addAll(cards);
	}
}

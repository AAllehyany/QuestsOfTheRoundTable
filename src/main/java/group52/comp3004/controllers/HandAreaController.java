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

public class HandAreaController implements Initializable{
	
	@FXML
	private HBox handContainer;

	public HandAreaController() {
		super();
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Field area crated");
	}
	
	public void updateCards(ArrayList<AdventureCard> cards) {
		handContainer.getChildren().clear();
		handContainer.getChildren().addAll(cards);
		
		// Change offset depending on hand size
		handContainer.setSpacing((400-cards.size()*50)/(cards.size()-1));
	}
}

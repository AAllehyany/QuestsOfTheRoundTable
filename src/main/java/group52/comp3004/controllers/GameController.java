package group52.comp3004.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.annotation.Resources;

import group52.comp3004.game.GameState;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

public class GameController implements Initializable {
	@FXML
	private GridPane gamepane;
	private DoubleProperty stageSize = new SimpleDoubleProperty();
	
	GameState model;
	
	//Constructor
	public GameController() {
		stageSize.set(0);
	}

	
	public DoubleProperty stageSizeProperty() {
		return stageSize;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	
}

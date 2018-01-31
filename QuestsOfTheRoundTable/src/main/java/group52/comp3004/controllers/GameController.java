package group52.comp3004.controllers;

import java.net.URL;

import javax.annotation.Resources;

import group52.comp3004.game.GameState;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameController {
	@FXML
	private GridPane gamepane;
	private DoubleProperty stageSize = new SimpleDoubleProperty();
	
	GameState model;
	
	//Constructor
	public GameController() {
		stageSize.set(0);
	}
	
	@FXML
	protected void initialize(URL location, Resources resource) {
			    
	}
	
	public DoubleProperty stageSizeProperty() {
		return stageSize;
	}
	
	
}

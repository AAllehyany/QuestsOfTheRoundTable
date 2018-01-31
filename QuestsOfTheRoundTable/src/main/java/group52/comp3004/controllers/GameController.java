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
		//Create static grid for game elements
		ColumnConstraints col = new ColumnConstraints(100);
	    gamepane.getColumnConstraints().addAll(col, col, col, col, col, col);
	    
	    RowConstraints row = new RowConstraints(100);
	    gamepane.getRowConstraints().addAll(row, row, row, row, row, row);
	    
	    /*Rectangle rect1 = new Rectangle(100, 100);
	    rect1.setFill(Color.RED);
	    Rectangle rect2 = new Rectangle(100, 100);
	    rect2.setFill(Color.GREEN);
	    Rectangle rect3 = new Rectangle(100, 100);
	    rect3.setFill(Color.BLUE);
	    
	    gamepane.add(rect1, 0, 0);
	    gamepane.add(rect2, 3, 3);
	    gamepane.add(rect3, 6, 6);*/
	    
	    //gamepane.
	    
	}
	
	public DoubleProperty stageSizeProperty() {
		return stageSize;
	}
	
	
}

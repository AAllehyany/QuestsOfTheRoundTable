package group52.comp3004;

import java.awt.Color;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class View{
	//Variables
	Stage stage;
	Model model;
	//window size
	double w_size = 600;
	private String css;
	
	//Constructor
	public View(Model myModel, Stage myStage) {
		model = myModel;
		stage = myStage;
		css = getClass().getResource("/view.css").toExternalForm();
		initView();
	}
	
	public void initView() {
		stage.setTitle("Quest of the Round Table");
		Group root = new Group();
        Scene scene = new Scene(root, w_size, w_size);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        
        Group playArea1 = new Group();
        Rectangle area1 = new Rectangle(w_size/3, w_size/6, w_size/3, w_size/6);
        //area1.setFill(Color.black);
        //area1.setArcHeight(30);
        //area1.setArcWidth(30);
        
        Rectangle player1 = new Rectangle(4*w_size/6, 0, w_size/6, w_size/6);
        Rectangle hand1 = new Rectangle(w_size/3, 0, w_size/3, w_size/6);
        Rectangle extraHand1 = new Rectangle(w_size/6, 0, w_size/6, w_size/6);
        
        root.getChildren().add(area1);
        root.getChildren().add(player1);
        root.getChildren().add(hand1);
        root.getChildren().add(extraHand1);
        
        stage.show();
	}
}
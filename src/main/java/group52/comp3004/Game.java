package group52.comp3004;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Game extends Application{
	//MVC classes
	private Model model;
	private View view;
	private Controller controller;
	
	@Override
	public void start(Stage myStage) {
		model = new Model();
		view = new View(model, myStage);
		controller = new Controller(model, view);
	}
    
    //main class
    public static void main(String[] args) {
    	System.out.println("Hello world");
    	launch(args);    	
    }
}

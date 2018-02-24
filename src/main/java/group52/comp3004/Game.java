package group52.comp3004;

import group52.comp3004.GUI.View;
import group52.comp3004.controllers.GameController;
import group52.comp3004.game.GameState;
import javafx.application.Application;
import javafx.stage.Stage;


public class Game extends Application{
	//MVC classes
	//private GameState model;
	private View view;
	private GameController controller;
	
	@Override
	public void start(Stage myStage) throws Exception {
		System.out.println("Starting");
		//model = new GameState();
		view = new View(myStage);
	}
    
    //main class
    public static void main(String[] args) {
    	System.out.println("Hello world");
    	launch(args);    	
    }
}

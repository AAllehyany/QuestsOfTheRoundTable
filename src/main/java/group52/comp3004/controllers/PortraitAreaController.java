package group52.comp3004.controllers;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import group52.comp3004.players.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class PortraitAreaController implements Initializable {
	
	@FXML 
	private StackPane portrait;
	@FXML
	private HBox shields1;
	private int numShields;
	private int TYPES_OF_SHIELDS = 15;
	
	private Random rand;
	
	private static Logger logger = Logger.getLogger(PortraitAreaController.class);
	public PortraitAreaController() {
		numShields = 0;
	}
//create portrait area
    public void initialize(URL location, ResourceBundle resources) {
    	logger.info("PORTRAIT AREA");
    	rand = new Random();
	}
    
    public void playerInfo(Player player){
    	int currentShields = player.getShields() - player.getMinShields();
    	//need to add more shields
    	if(currentShields > numShields) {
    		logger.info("Need to add "+(currentShields-numShields)+ " shields from  " + player.getId());
    		for(int i = 0; i < currentShields-numShields; i++) {
    			Rectangle shield = new Rectangle(20, 20);
    			int index = rand.nextInt(TYPES_OF_SHIELDS) + 1;
    			ImagePattern pattern = player.getGame().getResourceManager().getShield(index);
    			shield.setFill(pattern);
    			shields1.getChildren().add(shield);
    			numShields++;
    		}
    	}
    	//need to remove shields
    	else if(currentShields < numShields) {
    		logger.info("Need to remove "+(currentShields-numShields)+ " shields from  " + player.getId());
    		for(int i = 0; i < numShields-currentShields; i++) {
    			shields1.getChildren().remove(shields1.getChildren().size()-1);
    		}
    	}
    }
}
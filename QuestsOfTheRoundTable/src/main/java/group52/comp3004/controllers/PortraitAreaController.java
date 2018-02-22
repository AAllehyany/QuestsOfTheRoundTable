package group52.comp3004.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import group52.comp3004.players.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class PortraitAreaController implements Initializable {
	
	@FXML private VBox portrait;
	
	@FXML 
	private Text name;
	@FXML 
	private Text shield ;
	@FXML 
	private Text rank;
	public PortraitAreaController() {
		
	}
//create portraitarea
    public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    	System.out.println("PORTRAIT AREA");
    	this.name.setText("");
    	this.shield.setText("");
    	this.rank.setText("");
	}
    
    public void playerInfo(Player player){
    	this.name.setText(player.getId().toString());
    	this.shield.setText(player.getShields().toString());
    	this.rank.setText(player.getRank().toString());
    }
}
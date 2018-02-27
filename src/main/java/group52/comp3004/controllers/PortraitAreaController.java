package group52.comp3004.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import group52.comp3004.players.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PortraitAreaController implements Initializable {
	
	@FXML private VBox portrait;
	
	@FXML 
	private Text name;
	@FXML 
	private Text shield ;
	@FXML 
	private Text rank;
	@FXML
	private HBox shields1;
	public PortraitAreaController() {
		
	}
//create portrait area
    public void initialize(URL location, ResourceBundle resources) {
    	System.out.println("PORTRAIT AREA");
    	this.name.setText("");
    	this.shield.setText("");
    	this.rank.setText("");
    	Rectangle shield = new Rectangle(20, 20);
    	Image img = new Image("image/Shields/shield1.png");
    	ImagePattern tex = new ImagePattern(img);
    	shields1.getChildren().add(shield);
    	shield.setFill(tex);
	}
    
    public void playerInfo(Player player){
    	this.name.setText(player.getId().toString());
    	this.shield.setText(player.getShields().toString());
    	this.rank.setText(player.getRank().toString());
    }
}
package group52.comp3004.GUI;

import group52.comp3004.cards.Card;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;


public class HandArea{
	private Pane area;
	
	//Constructor
	public HandArea(Pane parent) throws Exception{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/hand_area.fxml"));
		area = loader.load();
		area.setStyle("-fx-background-color: #00FF00");
		System.out.println("Hand area created");
	}
	
	public Pane getPane() { return area; }
}

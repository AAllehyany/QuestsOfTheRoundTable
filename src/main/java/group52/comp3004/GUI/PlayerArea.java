package group52.comp3004.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class PlayerArea {
	private GridPane root;
	
	//Constructor
	public PlayerArea(GridPane aRoot) throws Exception{
		/*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/player_area.fxml"));
		root = loader.load();*/
		root = aRoot;
		
		//Add the subfields
		//NOTE TO SELF: should probably put this in the fxml file, somehow...
		FieldArea field = new FieldArea(root);
		HandArea hand = new HandArea(root);
		PortraitArea portrait = new PortraitArea(root);
		root.add(field.getPane(), 2, 4, 2, 1); //first set is col/row location 2nd is colspan/rowspan
		root.add(hand.getPane(), 2, 5, 2, 1);
		root.add(portrait.getPane(), 1, 5);
		
		System.out.println("Player area created");
	}
}

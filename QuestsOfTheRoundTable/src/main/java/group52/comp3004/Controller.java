package group52.comp3004;

import java.net.URL;
import javax.annotation.Resources;
import javafx.fxml.FXML;

public class Controller {
	
	Model model;
	
	//Constructor
	public Controller() {}
	public Controller(Model aModel, View view) {
		model = aModel;
	}
	
	@FXML
	protected void initialize(URL location, Resources resource) {
		System.out.println("Controller working kappa");
	}
}

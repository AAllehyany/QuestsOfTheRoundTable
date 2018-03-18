package group52.comp3004;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Kappa implements Initializable {
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.kappa();
	}

	public void kappa() {
		System.out.println("Kappa");
	}
}

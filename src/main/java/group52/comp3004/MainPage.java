package group52.comp3004;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPage {
	@Autowired
	//private List<Server> newServer;

	@RequestMapping("/")
	public String mainPage() {
		return "Hello world!";
	}
}

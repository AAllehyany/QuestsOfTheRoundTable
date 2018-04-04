package comp3004.group52.Group52;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

	@Autowired
	private List<Server> newServer;
	
	@RequestMapping("/")
	public String xD() {
		return "HEY";
	}
	@RequestMapping(method = RequestMethod.POST, value ="/rooms")
	public void addPlayer(int i) {
		newServer.get(i).addPlayer();
	}
	
}

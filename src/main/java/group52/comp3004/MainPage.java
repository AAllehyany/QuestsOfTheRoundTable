package group52.comp3004;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group52.comp3004.sockets.SocketHandler;

@RestController
public class MainPage {
	@RequestMapping("/")
	public String mainPage() {
		return "Hello world!";
	}
}

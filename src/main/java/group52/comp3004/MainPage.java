package group52.comp3004;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group52.comp3004.sockets.SocketHandler;

@RestController
public class MainPage {
	@Autowired
	private CopyOnWriteArrayList<SocketHandler> newRoom;

	@RequestMapping("/")
	public String mainPage() {
		return "Hello world!";
	}
	
	@RequestMapping("/room")
	public int getNumRooms() {
		return newRoom.size();
	}
}

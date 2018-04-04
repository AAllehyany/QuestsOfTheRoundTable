package group52.comp3004;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import group52.comp3004.controllers.GameController;
import group52.comp3004.game.GameState;

@SpringBootApplication
public class Server{

	public static void main(String[] args) {
		SpringApplication.run(Server.class, args);
	}
}


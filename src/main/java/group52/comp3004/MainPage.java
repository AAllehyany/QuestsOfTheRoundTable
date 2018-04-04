package group52.comp3004;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPage {

	@RequestMapping("/")
	public String mainPage() {
		return "Hello world!";
	}
}

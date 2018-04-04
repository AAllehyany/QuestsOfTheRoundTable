package comp3004.group52.Group52;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

	@RequestMapping("/")
	public String xD() {
		return "HEY";
	}
}

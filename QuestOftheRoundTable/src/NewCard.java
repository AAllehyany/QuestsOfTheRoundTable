package group52.comp3004;

import java.util.ArrayList;
import java.util.List;

public class NewCard {
	
	//attributes that a card has
	private CardType name;
	private List addsOn=new ArrayList();
	private boolean isFacedUp;
	public NewCard(CardType name) {
		this.name= name;
		isFacedUp = false;
	}
	
	public String getName() {
		return name.printCT();
	}
	
	public String toString() {
		String str ="";
		if(isFacedUp) {
			str+= name.printCT();
		}else {
			str = "face down";
		}
		return str;
	}
}

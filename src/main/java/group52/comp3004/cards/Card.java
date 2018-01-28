package group52.comp3004.cards;

import java.util.ArrayList;
import java.util.List;
public class Card {
	
	//attributes that a card has
	private String name;
	private boolean isFacedUp;
	
	public Card(String name) {
		this.name= name;
		isFacedUp = false;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		String str ="";
		if(isFacedUp) {
			str+= name;
		}else {
			str = "face down";
		}
		return str;
	}
    
    public boolean equals(Card card2){
        return this.getName().equals(card2.getName());
    }
}
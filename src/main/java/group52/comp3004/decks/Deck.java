package group52.comp3004.decks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import group52.comp3004.ResourceManager;
import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Weapon;

public class Deck<T> {
	
	List<T> cards;
	public ArrayList<T> discard = new ArrayList<T>();
	int size;
	
	public Deck() {
		this.cards = null;
		size = 0;
	}
	
	public Deck(List<T> cards) {
		setCards(cards);
	}
	
	public void setCards(List<T> cards) {
		this.cards = cards;
		size = cards.size();
	}
	
	public int getSize() {
		return size;
	}
	
	public void addCard(T card) {
		this.cards.add(card);
	}
	
	
	public T drawCard() {
		T card = null;
		Random rand = new Random();
		while(card == null && size > 0) {
			int index = rand.nextInt(cards.size());
			card = cards.get(index);
			cards.set(index, null);
		}
		
		size -= 1;
		
		return card;
	}
	
	public T discard(T c){
		this.discard.add(c);
		return c;
   	}
	
	public static ArrayList<AdventureCard> createAdventureDeck(ResourceManager resman) {
			
		ArrayList<AdventureCard> cards = new ArrayList<>(Arrays.asList(
				new Ally("Sir_Galahad", resman, 15, 0),
				new Ally("Sir_Lancelot", resman, 15, 0),
				new Ally("King_Arthur", resman, 10, 0),
				new Ally("Sir_Tristan", resman, 10, 0),
				new Ally("King_Pellinore", resman, 10, 0),
				new Ally("Sir_Gawain", resman, 10, 0),
				new Ally("Sir_Percival", resman, 5, 0),
				new Ally("Queen_Guinevere", resman, 0, 0),
				new Ally("Queen_Iseult", resman, 0, 0),
				new Ally("Merlin", resman, 0, 0)));
		
		for(int i = 0; i < 2; i++) cards.add(new Weapon("Excalibur", resman, 30));
		for(int i = 0; i < 6; i++) cards.add(new Weapon("Lance", resman, 30));
		for(int i = 0; i < 8; i++) cards.add(new Weapon("Battle_Ax", resman, 30));
		for(int i = 0; i < 16; i++) cards.add(new Weapon("Sword", resman, 10));
		for(int i = 0; i < 11; i++) cards.add(new Weapon("Horse", resman, 10));
		for(int i = 0; i < 6; i++) cards.add(new Weapon("Dagger", resman, 5));
		
		return cards;
	}
}

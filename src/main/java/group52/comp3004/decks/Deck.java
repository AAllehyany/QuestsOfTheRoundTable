package group52.comp3004.decks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import group52.comp3004.ResourceManager;
import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.Arms;
import group52.comp3004.cards.Deed;
import group52.comp3004.cards.EventCard;
import group52.comp3004.cards.Favor;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Plague;
import group52.comp3004.cards.Pox;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.Realm;
import group52.comp3004.cards.Recognition;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.cards.Test;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.cards.Weapon;

public class Deck<T> {
	
	List<T> cards;
	int size;
	ArrayList<T> discard = new ArrayList<T>();
	
	public Deck() {
		this.cards = new ArrayList<T>();
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
		size++;
	}
	
	
	public T drawCard() {
		T card = null;
		Random rand = new Random();
		if(this.size<=0) {
			this.setCards(this.discard);
		}
		int index = rand.nextInt(cards.size());
		card = cards.remove(index);
		size--;
		return card;
	}
	
	public T discardCard(T c) {
		discard.add(c);
		return c;
	}
	
	//PURPOSE: Builds the adventure deck
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
		for(int i = 0; i < 1; i++) cards.add(new Foe("Dragon", resman, 50, 70, "Slay_the_Dragon"));
		for(int i = 0; i < 2; i++) cards.add(new Foe("Giant", resman, 40));
		for(int i = 0; i < 4; i++) cards.add(new Foe("Mordred", resman, 30));
		for(int i = 0; i < 2; i++) cards.add(new Foe("Green_Knight", resman, 25, 40, "Green_Knight"));
		for(int i = 0; i < 3; i++) cards.add(new Foe("Black_Knight", resman, 25, 35, "Rescue_Maiden"));
		for(int i = 0; i < 6; i++) cards.add(new Foe("Evil_Knight", resman, 20, 30, "Enchanted_Forest"));
		for(int i = 0; i < 8; i++) cards.add(new Foe("Saxon_Knight", resman, 15, 25, "Repel_Saxon_Raiders"));
		for(int i = 0; i < 7; i++) cards.add(new Foe("Robber_Knight", resman, 15));
		for(int i = 0; i < 5; i++) cards.add(new Foe("Saxons", resman, 10, 20, "Repel_Saxon_Raiders"));
		for(int i = 0; i < 4; i++) cards.add(new Foe("Boar", resman, 5, 15, "Boar_Hunt"));
		for(int i = 0; i < 8; i++) cards.add(new Foe("Thieves", resman, 5));
		for(int i = 0; i < 2; i++) cards.add(new Test("Valor", resman, 3));
		for(int i = 0; i < 2; i++) cards.add(new Test("Temptation", resman, 3));
		for(int i = 0; i < 2; i++) cards.add(new Test("Morgan_Le_Fey", resman, 3));
		for(int i = 0; i < 2; i++) cards.add(new Test("Questing_Beast", resman, 4));
		for(int i = 0; i < 8; i++) cards.add(new Amour("Amour", resman, 10, 2));
		
		return cards;
	}
	
	//PURPOSE: Builds the story deck
	//	->Missing: not sure how event behaviour works so events aren't added
	public static ArrayList<StoryCard> createStoryDeck(ResourceManager resman){
		ArrayList<StoryCard> cards= new ArrayList<>(Arrays.asList(
				//missing event behaviours
				new EventCard("Pox", resman, new Pox()),
				new EventCard("Plague", resman, new Plague()),
				new EventCard("Chivalrous_Deed", resman, new Deed()),
				new EventCard("Prosperity", resman, new Realm()),
				new EventCard("Call_to_Arms", resman, new Arms()),
				new QuestCard("Holy_Grail", resman, 5),
				new QuestCard("Green_Knight", resman, 4),
				new QuestCard("Questing_Beast", resman, 4),
				new QuestCard("Queens_Honor", resman, 4),
				new QuestCard("Rescue_Maiden", resman, 3),
				new QuestCard("Enchanted_Forest", resman, 3),
				new QuestCard("Slay_the_Dragon", resman, 3),
				new Tourneys("Camelot", resman, 3), 
				new Tourneys("Orkney", resman, 2),
				new Tourneys("Tintagel", resman, 1),
				new Tourneys("York", resman, 0)));
				
		for(int i = 0; i < 2; i++) cards.add(new QuestCard("Arthurs_Enemy", resman, 3));
		for(int i = 0; i < 2; i++) cards.add(new QuestCard("Boar_Hunt", resman, 2));
		for(int i = 0; i < 2; i++) cards.add(new QuestCard("Repel_Saxon_Raiders", resman, 2));
		//missing event behaviours
		for(int i = 0; i < 2; i++) cards.add(new EventCard("King's_Recognition", resman, new Recognition()));
		for(int i = 0; i < 2; i++) cards.add(new EventCard("Queen's_Favor", resman, new Favor()));
		//for(int i = 0; i < 2; i++) cards.add(new EventCard("Called_to_Camelot", resman, Ca));
		
		return cards;
	}
}
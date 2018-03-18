package group52.comp3004.decks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.Arms;
import group52.comp3004.cards.Camelot;
import group52.comp3004.cards.Card;
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
import group52.comp3004.cards.Tests;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.cards.Weapon;

public class Deck<T> {
	
	List<T> cards;
	int size;
	ArrayList<T> discard = new ArrayList<T>();
	
	static final private Logger logger = Logger.getLogger(Deck.class);
	
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
		Card card = (Card) c;
		logger.info("Discarded: " + card.getName());
		discard.add(c);
		return c;
	}
	
	public ArrayList<T> discardCard(ArrayList<T> cards){
		cards.stream().forEach(c -> logger.info("Discarded: " + ((Card) c).getName()));
		discard.addAll(cards);
		return cards;
	}
	
	//PURPOSE: Builds the adventure deck
	public static ArrayList<AdventureCard> createAdventureDeck() {
			
		ArrayList<AdventureCard> cards = new ArrayList<>(Arrays.asList(
				new Ally("Sir_Galahad", 15, 0),
				new Ally("Sir_Lancelot", 15, 0),
				new Ally("King_Arthur", 10, 0),
				new Ally("Sir_Tristan", 10, 0),
				new Ally("King_Pellinore", 10, 0),
				new Ally("Sir_Gawain", 10, 0),
				new Ally("Sir_Percival", 5, 0),
				new Ally("Queen_Guinevere", 0, 0),
				new Ally("Queen_Iseult", 0, 0),
				new Ally("Merlin", 0, 0)));
		
		for(int i = 0; i < 2; i++) cards.add(new Weapon("Excalibur", 30));
		for(int i = 0; i < 6; i++) cards.add(new Weapon("Lance", 30));
		for(int i = 0; i < 8; i++) cards.add(new Weapon("Battle_Ax", 30));
		for(int i = 0; i < 16; i++) cards.add(new Weapon("Sword", 10));
		for(int i = 0; i < 11; i++) cards.add(new Weapon("Horse", 10));
		for(int i = 0; i < 6; i++) cards.add(new Weapon("Dagger", 5));
		for(int i = 0; i < 1; i++) cards.add(new Foe("Dragon", 50, 70, "Slay_the_Dragon"));
		for(int i = 0; i < 2; i++) cards.add(new Foe("Giant", 40));
		for(int i = 0; i < 4; i++) cards.add(new Foe("Mordred", 30));
		for(int i = 0; i < 2; i++) cards.add(new Foe("Green_Knight", 25, 40, "Green_Knight"));
		for(int i = 0; i < 3; i++) cards.add(new Foe("Black_Knight", 25, 35, "Rescue_Maiden"));
		for(int i = 0; i < 6; i++) cards.add(new Foe("Evil_Knight", 20, 30, "Enchanted_Forest"));
		for(int i = 0; i < 8; i++) cards.add(new Foe("Saxon_Knight", 15, 25, "Repel_Saxon_Raiders"));
		for(int i = 0; i < 7; i++) cards.add(new Foe("Robber_Knight", 15));
		for(int i = 0; i < 5; i++) cards.add(new Foe("Saxons", 10, 20, "Repel_Saxon_Raiders"));
		for(int i = 0; i < 4; i++) cards.add(new Foe("Boar", 5, 15, "Boar_Hunt"));
		for(int i = 0; i < 8; i++) cards.add(new Foe("Thieves", 5));
		for(int i = 0; i < 2; i++) cards.add(new Tests("Valor", 3));
		for(int i = 0; i < 2; i++) cards.add(new Tests("Temptation", 3));
		for(int i = 0; i < 2; i++) cards.add(new Tests("Morgan_Le_Fey", 3));
		for(int i = 0; i < 2; i++) cards.add(new Tests("Questing_Beast", 4));
		for(int i = 0; i < 8; i++) cards.add(new Amour("Amour", 10, 2));
		
		return cards;
	}
	
	//PURPOSE: Builds the story deck
	public static ArrayList<StoryCard> createStoryDeck(){
		ArrayList<StoryCard> cards= new ArrayList<>(Arrays.asList(
				new EventCard("Pox", new Pox()),
				new EventCard("Plague", new Plague()),
				new EventCard("Chivalrous_Deed", new Deed()),
				new EventCard("Prosperity", new Realm()),
				new EventCard("Call_to_Arms", new Arms()),
				new QuestCard("Holy_Grail", 5),
				new QuestCard("Green_Knight_Quest", 4),
				new QuestCard("Questing_Beast_Search", 4),
				new QuestCard("Queens_Honor", 4),
				new QuestCard("Rescue_Maiden", 3),
				new QuestCard("Enchanted_Forest", 3),
				new QuestCard("Slay_the_Dragon", 3),
				new Tourneys("Camelot", 3), 
				new Tourneys("Orkney", 2),
				new Tourneys("Tintagel", 1),
				new Tourneys("York", 0)));
				
		for(int i = 0; i < 2; i++) cards.add(new QuestCard("Arthurs_Enemy", 3));
		for(int i = 0; i < 2; i++) cards.add(new QuestCard("Boar_Hunt", 2));
		for(int i = 0; i < 2; i++) cards.add(new QuestCard("Repel_Saxon_Raiders", 2));
		for(int i = 0; i < 2; i++) cards.add(new EventCard("King's_Recognition", new Recognition()));
		for(int i = 0; i < 2; i++) cards.add(new EventCard("Queen's_Favor", new Favor()));
		for(int i = 0; i < 2; i++) cards.add(new EventCard("Called_to_Camelot", new Camelot()));
		
		return cards;
	}
}
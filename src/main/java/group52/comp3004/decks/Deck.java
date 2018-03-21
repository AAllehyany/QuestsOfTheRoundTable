package group52.comp3004.decks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import group52.comp3004.cards.CardFactory;
import group52.comp3004.cards.AdventureCard;
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

public class Deck<T> extends CardFactory{
	
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
				createAlly("Sir_Galahad", 15, 0),
				createAlly("Sir_Lancelot", 15, 0),
				createAlly("King_Arthur", 10, 0),
				createAlly("Sir_Tristan", 10, 0),
				createAlly("King_Pellinore", 10, 0),
				createAlly("Sir_Gawain", 10, 0),
				createAlly("Sir_Percival", 5, 0),
				createAlly("Queen_Guinevere", 0, 0),
				createAlly("Queen_Iseult", 0, 0),
				createAlly("Merlin", 0, 0)));
		
		for(int i = 0; i < 2; i++) cards.add(createWeapon("Excalibur", 30));
		for(int i = 0; i < 6; i++) cards.add(createWeapon("Lance", 30));
		for(int i = 0; i < 8; i++) cards.add(createWeapon("Battle_Ax", 30));
		for(int i = 0; i < 16; i++) cards.add(createWeapon("Sword", 10));
		for(int i = 0; i < 11; i++) cards.add(createWeapon("Horse", 10));
		for(int i = 0; i < 6; i++) cards.add(createWeapon("Dagger", 5));
		for(int i = 0; i < 1; i++) cards.add(createFoe("Dragon", 50, 70, "Slay_the_Dragon"));
		for(int i = 0; i < 2; i++) cards.add(createFoe("Giant", 40));
		for(int i = 0; i < 4; i++) cards.add(createFoe("Mordred", 30));
		for(int i = 0; i < 2; i++) cards.add(createFoe("Green_Knight", 25, 40, "Green_Knight"));
		for(int i = 0; i < 3; i++) cards.add(createFoe("Black_Knight", 25, 35, "Rescue_Maiden"));
		for(int i = 0; i < 6; i++) cards.add(createFoe("Evil_Knight", 20, 30, "Enchanted_Forest"));
		for(int i = 0; i < 8; i++) cards.add(createFoe("Saxon_Knight", 15, 25, "Repel_Saxon_Raiders"));
		for(int i = 0; i < 7; i++) cards.add(createFoe("Robber_Knight", 15));
		for(int i = 0; i < 5; i++) cards.add(createFoe("Saxons", 10, 20, "Repel_Saxon_Raiders"));
		for(int i = 0; i < 4; i++) cards.add(createFoe("Boar", 5, 15, "Boar_Hunt"));
		for(int i = 0; i < 8; i++) cards.add(createFoe("Thieves", 5));
		for(int i = 0; i < 2; i++) cards.add(createTests("Valor", 3));
		for(int i = 0; i < 2; i++) cards.add(createTests("Temptation", 3));
		for(int i = 0; i < 2; i++) cards.add(createTests("Morgan_Le_Fey", 3));
		for(int i = 0; i < 2; i++) cards.add(createTests("Questing_Beast", 4));
		for(int i = 0; i < 8; i++) cards.add(createAmour("Amour", 10, 2));
		
		for(int i=0;i<cards.size();i++) cards.get(i).setID(i);
		
		return cards;
	}
	
	//PURPOSE: Builds the story deck
	public static ArrayList<StoryCard> createStoryDeck(){
		ArrayList<StoryCard> cards= new ArrayList<>(Arrays.asList(
				createEvent("Pox", new Pox()),
				createEvent("Plague", new Plague()),
				createEvent("Chivalrous_Deed", new Deed()),
				createEvent("Prosperity", new Realm()),
				createEvent("Call_to_Arms", new Arms()),
				createQuest("Holy_Grail", 5),
				createQuest("Green_Knight_Quest", 4),
				createQuest("Questing_Beast_Search", 4),
				createQuest("Queens_Honor", 4),
				createQuest("Rescue_Maiden", 3),
				createQuest("Enchanted_Forest", 3),
				createQuest("Slay_the_Dragon", 3),
				createTourney("Camelot", 3), 
				createTourney("Orkney", 2),
				createTourney("Tintagel", 1),
				createTourney("York", 0)));
				
		for(int i = 0; i < 2; i++) cards.add(createQuest("Arthurs_Enemy", 3));
		for(int i = 0; i < 2; i++) cards.add(createQuest("Boar_Hunt", 2));
		for(int i = 0; i < 2; i++) cards.add(createQuest("Repel_Saxon_Raiders", 2));
		for(int i = 0; i < 2; i++) cards.add(createEvent("King's_Recognition", new Recognition()));
		for(int i = 0; i < 2; i++) cards.add(createEvent("Queen's_Favor", new Favor()));
		for(int i = 0; i < 2; i++) cards.add(createEvent("Called_to_Camelot", new Camelot()));
		
		for(int i=0;i<cards.size();i++) cards.get(i).setID(i);
		
		return cards;
	}
}
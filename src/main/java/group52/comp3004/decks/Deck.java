package group52.comp3004.decks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Arms;
import group52.comp3004.cards.Camelot;
import group52.comp3004.cards.Card;
import group52.comp3004.cards.CardFactory;
import group52.comp3004.cards.Deed;
import group52.comp3004.cards.Favor;
import group52.comp3004.cards.Plague;
import group52.comp3004.cards.Pox;
import group52.comp3004.cards.Realm;
import group52.comp3004.cards.Recognition;
import group52.comp3004.cards.StoryCard;

public class Deck<T>{
	
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
				CardFactory.createAlly("Sir_Galahad", 15, 0),
				CardFactory.createAlly("Sir_Lancelot", 15, 0),
				CardFactory.createAlly("King_Arthur", 10, 0),
				CardFactory.createAlly("Sir_Tristan", 10, 0),
				CardFactory.createAlly("King_Pellinore", 10, 0),
				CardFactory.createAlly("Sir_Gawain", 10, 0),
				CardFactory.createAlly("Sir_Percival", 5, 0),
				CardFactory.createAlly("Queen_Guinevere", 0, 0),
				CardFactory.createAlly("Queen_Iseult", 0, 0),
				CardFactory.createAlly("Merlin", 0, 0)));
		
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createWeapon("Excalibur", 30));
		for(int i = 0; i < 6; i++) cards.add(CardFactory.createWeapon("Lance", 30));
		for(int i = 0; i < 8; i++) cards.add(CardFactory.createWeapon("Battle_Ax", 30));
		for(int i = 0; i < 16; i++) cards.add(CardFactory.createWeapon("Sword", 10));
		for(int i = 0; i < 11; i++) cards.add(CardFactory.createWeapon("Horse", 10));
		for(int i = 0; i < 6; i++) cards.add(CardFactory.createWeapon("Dagger", 5));
		for(int i = 0; i < 1; i++) cards.add(CardFactory.createFoe("Dragon", 50, 70, "Slay_the_Dragon"));
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createFoe("Giant", 40));
		for(int i = 0; i < 4; i++) cards.add(CardFactory.createFoe("Mordred", 30));
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createFoe("Green_Knight", 25, 40, "Green_Knight"));
		for(int i = 0; i < 3; i++) cards.add(CardFactory.createFoe("Black_Knight", 25, 35, "Rescue_Maiden"));
		for(int i = 0; i < 6; i++) cards.add(CardFactory.createFoe("Evil_Knight", 20, 30, "Enchanted_Forest"));
		for(int i = 0; i < 8; i++) cards.add(CardFactory.createFoe("Saxon_Knight", 15, 25, "Repel_Saxon_Raiders"));
		for(int i = 0; i < 7; i++) cards.add(CardFactory.createFoe("Robber_Knight", 15));
		for(int i = 0; i < 5; i++) cards.add(CardFactory.createFoe("Saxons", 10, 20, "Repel_Saxon_Raiders"));
		for(int i = 0; i < 4; i++) cards.add(CardFactory.createFoe("Boar", 5, 15, "Boar_Hunt"));
		for(int i = 0; i < 8; i++) cards.add(CardFactory.createFoe("Thieves", 5));
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createTests("Valor", 3));
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createTests("Temptation", 3));
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createTests("Morgan_Le_Fey", 3));
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createTests("Questing_Beast", 4));
		for(int i = 0; i < 8; i++) cards.add(CardFactory.createAmour("Amour", 10, 2));
		
		for(int i=0;i<cards.size();i++) cards.get(i).setID(i);
		
		return cards;
	}
	
	//PURPOSE: Builds the story deck
	public static ArrayList<StoryCard> createStoryDeck(){
		ArrayList<StoryCard> cards= new ArrayList<>(Arrays.asList(
				CardFactory.createEvent("Pox", new Pox()),
				CardFactory.createEvent("Plague", new Plague()),
				CardFactory.createEvent("Chivalrous_Deed", new Deed()),
				CardFactory.createEvent("Prosperity", new Realm()),
				CardFactory.createEvent("Call_to_Arms", new Arms()),
				CardFactory.createQuest("Holy_Grail", 5),
				CardFactory.createQuest("Green_Knight_Quest", 4),
				CardFactory.createQuest("Questing_Beast_Search", 4),
				CardFactory.createQuest("Queens_Honor", 4),
				CardFactory.createQuest("Rescue_Maiden", 3),
				CardFactory.createQuest("Enchanted_Forest", 3),
				CardFactory.createQuest("Slay_the_Dragon", 3),
				CardFactory.createTourney("Camelot", 3), 
				CardFactory.createTourney("Orkney", 2),
				CardFactory.createTourney("Tintagel", 1),
				CardFactory.createTourney("York", 0)));
				
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createQuest("Arthurs_Enemy", 3));
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createQuest("Boar_Hunt", 2));
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createQuest("Repel_Saxon_Raiders", 2));
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createEvent("King's_Recognition", new Recognition()));
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createEvent("Queen's_Favor", new Favor()));
		for(int i = 0; i < 2; i++) cards.add(CardFactory.createEvent("Called_to_Camelot", new Camelot()));
		
		for(int i=0;i<cards.size();i++) cards.get(i).setID(i);
		
		return cards;
	}
}
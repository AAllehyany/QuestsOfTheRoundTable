	package group52.comp3004.decks;
	
	import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
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
	
	/**
	 * Utility function for creating game decks. Will be used to handle adventure and story decks. 
	 * <p>Split into two separate lists
	 * <p>	Deck 1: the draw deck called cards</p>
	 * <p>	Deck 2: the discard pile called discard</p>
	 * @author Sandy
	 *
	 * @param <T> The type of object that the deck is made of.
	 */
	public class Deck<T>{
		static ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		List<T> draw;
		int size;
		ArrayList<T> discard = new ArrayList<T>();
		
		static final private Logger logger = Logger.getLogger(Deck.class);
		
		/**
		 * Constructor for an empty placeholder deck
		 */
		public Deck() {
			this.draw = new ArrayList<T>();
			size = 0;
		}
	
		public T discard(T c) {
			Card card = (Card) c;
			logger.info("Discarded: " + card.getName());
			discard.add(c);
			return c;
		}
	
		public ArrayList<T> discard(ArrayList<T> cards){
			cards.stream().forEach(c -> this.discard(c));
			return cards;
		}
		
		/**
		 * Constructor for a deck that contains some initial cards. Only the cards list is changed. The discard list remains empty for this constructor.
		 * @param cards The input cards that are used to initialize the cards list.
		 */
		public Deck(List<T> cards) {
			setCards(cards);
		}
		/**
		 * Replace the current deck with a new one.
		 * @param draw The deck of cards
		 */
		public void setCards(List<T> cards) {
			this.draw = cards;
			size = cards.size();
		}
		
		/**
		 * Gets the size of the current draw deck.
		 * @return
		 */
		public int getSize() {
			return size;
		}
		
		public void addCard(T card) {
			logger.info("Added " + card + " to Deck");
			this.draw.add(card);
			size++;
		}
		
		/**
		 * Draw a card from the deck. The draw is from a random position in the deck so there is no need to worry about shuffle function.
		 * <p>If there are no more cards to draw the discard list is moved back to the draw deck
		 * @return
		 */
		public T draw() {
			T obj = null;
			Random rand = new Random();
			if(this.size<=0) {
				this.setCards(this.discard);
			}
			int index = rand.nextInt(draw.size());
			obj = draw.remove(index);
			logger.info("Removed one object from deck");
			size--;
			return obj;
		}
		
		//PURPOSE: Builds the adventure deck ?stay or go?
		public static ArrayList<AdventureCard> createAdventureDeck() {
				
			ArrayList<AdventureCard> cards = new ArrayList<>(Arrays.asList(
					(Ally) context.getBean("Sir_Galahad"),
					(Ally) context.getBean("Sir_Lancelot", 15, 0),
					(Ally) context.getBean("King_Arthur", 10, 0),
					(Ally) context.getBean("Sir_Tristan", 10, 0),
					(Ally) context.getBean("King_Pellinore", 10, 0),
					(Ally) context.getBean("Sir_Gawain", 10, 0),
					(Ally) context.getBean("Sir_Percival", 5, 0),
					(Ally) context.getBean("Queen_Guinevere"),
					(Ally) context.getBean("Queen_Iseult"),
					(Ally) context.getBean("Merlin")));
			
			for(int i = 0; i < 2; i++) cards.add((Weapon)context.getBean("Excalibur", 30));
			for(int i = 0; i < 6; i++) cards.add((Weapon)context.getBean("Lance", 30));
			for(int i = 0; i < 8; i++) cards.add((Weapon)context.getBean("Battle_Ax", 30));
			for(int i = 0; i < 16; i++) cards.add((Weapon)context.getBean("Sword", 10));
			for(int i = 0; i < 11; i++) cards.add((Weapon)context.getBean("Horse", 10));
			for(int i = 0; i < 6; i++) cards.add((Weapon)context.getBean("Dagger", 5));
			for(int i = 0; i < 1; i++) cards.add((Foe)context.getBean("Dragon", 50, 70, "Slay_the_Dragon"));
			for(int i = 0; i < 2; i++) cards.add((Foe)context.getBean("Giant", 40));
			for(int i = 0; i < 4; i++) cards.add((Foe)context.getBean("Mordred", 30));
			for(int i = 0; i < 2; i++) cards.add((Foe)context.getBean("Green_Knight", 25, 40, "Green_Knight"));
			for(int i = 0; i < 3; i++) cards.add((Foe)context.getBean("Black_Knight", 25, 35, "Rescue_Maiden"));
			for(int i = 0; i < 6; i++) cards.add((Foe)context.getBean("Evil_Knight", 20, 30, "Enchanted_Forest"));
			for(int i = 0; i < 8; i++) cards.add((Foe)context.getBean("Saxon_Knight", 15, 25, "Repel_Saxon_Raiders"));
			for(int i = 0; i < 7; i++) cards.add((Foe)context.getBean("Robber_Knight", 15));
			for(int i = 0; i < 5; i++) cards.add((Foe)context.getBean("Saxons", 10, 20, "Repel_Saxon_Raiders"));
			for(int i = 0; i < 4; i++) cards.add((Foe)context.getBean("Boar", 5, 15, "Boar_Hunt"));
			for(int i = 0; i < 8; i++) cards.add((Foe)context.getBean("Thieves", 5));
			for(int i = 0; i < 2; i++) cards.add((Foe)context.getBean("Valor", 3));
			for(int i = 0; i < 2; i++) cards.add((Tests)context.getBean("Temptation", 3));
			for(int i = 0; i < 2; i++) cards.add((Tests)context.getBean("Morgan_Le_Fey", 3));
			for(int i = 0; i < 2; i++) cards.add((Tests)context.getBean("Questing_Beast", 4));
			for(int i = 0; i < 8; i++) cards.add((Tests)context.getBean("Amour", 10, 2));
			
			for(int i=0;i<cards.size();i++) cards.get(i).setID(i);
			
			return cards;
		}
		
		//PURPOSE: Builds the story deck ?stay or go?
		public static ArrayList<StoryCard> createStoryDeck(){
			ArrayList<StoryCard> cards= new ArrayList<>(Arrays.asList(
					(EventCard)context.getBean("Pox", (Pox)context.getBean("Pox")),
					(EventCard)context.getBean("Plague", new Plague()),
					(EventCard)context.getBean("Chivalrous_Deed", new Deed()),
					(EventCard)context.getBean("Prosperity", new Realm()),
					(EventCard)context.getBean("Call_to_Arms", new Arms()),
					(QuestCard)context.getBean("Holy_Grail", 5),
					(QuestCard)context.getBean("Green_Knight_Quest", 4),
					(QuestCard)context.getBean("Questing_Beast_Search", 4),
					(QuestCard)context.getBean("Queens_Honor", 4),
					(QuestCard)context.getBean("Rescue_Maiden", 3),
					(QuestCard)context.getBean("Enchanted_Forest", 3),
					(QuestCard)context.getBean("Slay_the_Dragon", 3),
					(Tourneys)context.getBean("Camelot", 3), 
					(Tourneys)context.getBean("Orkney", 2),
					(Tourneys)context.getBean("Tintagel", 1),
					(Tourneys)context.getBean("York", 0)));
					
			for(int i = 0; i < 2; i++) cards.add((QuestCard)context.getBean("Arthurs_Enemy", 3));
			for(int i = 0; i < 2; i++) cards.add((QuestCard)context.getBean("Boar_Hunt", 2));
			for(int i = 0; i < 2; i++) cards.add((QuestCard)context.getBean("Repel_Saxon_Raiders", 2));
			for(int i = 0; i < 2; i++) cards.add((EventCard)context.getBean("King's_Recognition", new Recognition()));
			for(int i = 0; i < 2; i++) cards.add((EventCard)context.getBean("Queen's_Favor", new Favor()));
			for(int i = 0; i < 2; i++) cards.add((EventCard)context.getBean("Called_to_Camelot", new Camelot()));
			
			for(int i=0;i<cards.size();i++) cards.get(i).setID(i);
			
			return cards;
		}

	}

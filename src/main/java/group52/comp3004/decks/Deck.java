	package group52.comp3004.decks;
	
	import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import group52.comp3004.cards.Card;
	
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
//		static ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
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
	}

package group52.comp3004.decks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

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

public class DeckFactory {
	
	static final private Logger logger = Logger.getLogger(DeckFactory.class);
	
	/**
	 * Create the predetermined Adventure Deck for the game
	 * @return the base Adventure Deck
	 */
	public static Deck<AdventureCard> createAdventureDeck() {
		
		Deck<AdventureCard> aDeck = new Deck<AdventureCard>();
		ArrayList<AdventureCard> cards = new ArrayList<AdventureCard>();
		cards.add(CardFactory.createAlly("Sir_Percival", 5, 0, "Holy_Grail", 20, 0));
		cards.add(CardFactory.createAlly("Sir_Galahad", 15, 0));
		cards.add(CardFactory.createAlly("Sir_Lancelot", 15, 0, "Queens_Honor", 25, 0));
		cards.add(CardFactory.createAlly("King_Arthur", 10, 2));
		cards.add(CardFactory.createAlly("Sir_Tristan", 10, 0, "Queen_Iseult", 20, 0));
		cards.add(CardFactory.createAlly("King_Pellinore", 10, 0, "Questing_Beast_Search", 10, 4));
		cards.add(CardFactory.createAlly("Sir_Gawain", 10, 0, "Green_Knight_Quest", 20, 0));
		cards.add(CardFactory.createAlly("Queen_Guinevere", 0, 3));
		cards.add(CardFactory.createAlly("Queen_Iseult", 0, 2, "Sir_Tristan", 0, 4));
		cards.add(CardFactory.createAlly("Merlin"));
		
		for(int i=0;i<2;i++) cards.add(CardFactory.createWeapon("Excalibur", 30));
		for(int i=0;i<6;i++) cards.add(CardFactory.createWeapon("Lance", 20));
		for(int i=0;i<8;i++) cards.add(CardFactory.createWeapon("Battle_Ax", 15));
		for(int i=0;i<16;i++) cards.add(CardFactory.createWeapon("Sword", 10));
		for(int i=0;i<11;i++) cards.add(CardFactory.createWeapon("Horse", 10));
		for(int i=0;i<6;i++) cards.add(CardFactory.createWeapon("Dagger", 5));
		for(int i=0;i<1;i++) cards.add(CardFactory.createFoe("Dragon",  50, 70, "Slay_the_Dragon"));
		for(int i=0;i<2;i++) cards.add(CardFactory.createFoe("Giant",  40));
		for(int i=0;i<4;i++) cards.add(CardFactory.createFoe("Mordred",  30));
		for(int i=0;i<2;i++) cards.add(CardFactory.createFoe("Green_Knight",  25, 40, "Green_Knight_Quest"));
		for(int i=0;i<3;i++) cards.add(CardFactory.createFoe("Black_Knight",  25, 35, "Rescue_Maiden"));
		for(int i=0;i<6;i++) cards.add(CardFactory.createFoe("Evil_Knight",  20, 30, "Enchanted_Forest"));
		for(int i=0;i<8;i++) cards.add(CardFactory.createFoe("Saxon_Knight",  15, 25, "Repel_Saxon_Raiders"));
		for(int i=0;i<7;i++) cards.add(CardFactory.createFoe("Robber_Knight",  15));
		for(int i=0;i<5;i++) cards.add(CardFactory.createFoe("Saxons",  10, 20, "Repel_Saxon_Raiders"));
		for(int i=0;i<4;i++) cards.add(CardFactory.createFoe("Boar",  5, 15, "Boar_Hunt"));
		for(int i=0;i<8;i++) cards.add(CardFactory.createFoe("Thieves",  5));
		for(int i=0;i<8;i++) cards.add(CardFactory.createAmour("Amour", 10, 1));
		for(int i=0;i<2;i++) cards.add(CardFactory.createTests("Valor", 3));
		for(int i=0;i<2;i++) cards.add(CardFactory.createTests("Temptation", 3));
		for(int i=0;i<2;i++) cards.add(CardFactory.createTests("Morgan_Le_Fey", 4));
		for(int i=0;i<2;i++) cards.add(CardFactory.createTests("Questing_Beast", 3));
		
		for(int i=0;i<cards.size();i++) {
			cards.get(i).setID(i);
			aDeck.addCard(cards.get(i));
		}
		
		return aDeck;
	}
	
	/**
	 * Create the predetermined Story Deck for the game
	 * @return the base Story Deck
	 */
	public static Deck<StoryCard> createStoryDeck(){
		Deck<StoryCard> sDeck = new Deck<StoryCard>();
		ArrayList<StoryCard> cards = new ArrayList<StoryCard>();
		
		/*cards.add(CardFactory.createEvent("Pox", new Pox()));
		cards.add(CardFactory.createEvent("Plague", new Plague()));
		cards.add(CardFactory.createEvent("Chivalrous_Deed", new Deed()));
		cards.add(CardFactory.createEvent("Prosperity", new Realm()));
		cards.add(CardFactory.createEvent("Call_to_Arms", new Arms()));
		cards.add(CardFactory.createQuest("Holy_Grail", 5));
		cards.add(CardFactory.createQuest("Green_Knight_Quest", 4));
		cards.add(CardFactory.createQuest("Questing_Beast_Search", 4));
		cards.add(CardFactory.createQuest("Queens_Honor", 4));
		cards.add(CardFactory.createQuest("Rescue_Maiden", 3));
		cards.add(CardFactory.createQuest("Enchanted_Forest", 3));
		cards.add(CardFactory.createQuest("Slay_the_Dragon", 3));*/
		cards.add(CardFactory.createTourney("Camelot", 3));
		cards.add(CardFactory.createTourney("Orkney", 2));
		cards.add(CardFactory.createTourney("Tintagel", 1));
		cards.add(CardFactory.createTourney("York", 0));
		
		/*for(int i=0;i<2;i++) cards.add(CardFactory.createQuest("Arthurs_Enemy", 3));
		for(int i=0;i<2;i++) cards.add(CardFactory.createQuest("Boar_Hunt", 2));
		for(int i=0;i<2;i++) cards.add(CardFactory.createQuest("Repel_Saxon_Raiders", 2));
		for(int i=0;i<2;i++) cards.add(CardFactory.createEvent("King's_Recognition", new Recognition()));
		for(int i=0;i<2;i++) cards.add(CardFactory.createEvent("Queen's_Favor", new Favor()));
		for(int i=0;i<2;i++) cards.add(CardFactory.createEvent("Called_to_Camelot", new Camelot()));*/
		
		for(int i=0;i<cards.size();i++) {
			cards.get(i).setID(i);
			sDeck.addCard(cards.get(i));
		}
		
		return sDeck;
	}
	
	/**
	 * Create the deck of cards specified by a file
	 * @param name the name of the file
	 * @return
	 */
	public static Deck<Card> createDeckFromFile(String name){
		Deck<Card> deck = new Deck<Card>();
		ArrayList<Card> cards = new ArrayList<Card>();

		String line = null;
		BufferedReader br = null;
		
		try {
			FileReader fr = new FileReader(name);
			br = new BufferedReader(fr);
			while((line = br.readLine()) != null) {
				ArrayList<String> params = new ArrayList<String>();
				String param = null;
				for(int i=0;i<line.length();i++) {
					if(line.charAt(i)==' ') {
						params.add(param);
						param = null;
					}else param += line.charAt(i);
				}
				switch(params.get(0)){
				case "Ally":
					if(params.size()==2) cards.add(CardFactory.createAlly(params.get(1)));
					else if(params.size()==4) cards.add(CardFactory.createAlly(params.get(1),
							Integer.parseInt(params.get(2)), Integer.parseInt(params.get(3))));
					else if(params.size()==7) cards.add(CardFactory.createAlly(params.get(1), 
							Integer.parseInt(params.get(2)), Integer.parseInt(params.get(3)), params.get(4), 
							Integer.parseInt(params.get(5)), Integer.parseInt(params.get(6))));
					else logger.info("Invalid input");
					break;
				case "Amour":
					if(params.size()==4) cards.add(CardFactory.createAmour(params.get(1), 
							Integer.parseInt(params.get(2)), Integer.parseInt(params.get(3))));
					else logger.info("Invalid input");
					break;
				case "Weapon":
					if(params.size()==3) cards.add(CardFactory.createWeapon(params.get(1),
							Integer.parseInt(params.get(2))));
					else logger.info("Invalid input");
					break;
				case "Foe":
					if(params.size()==3) cards.add(CardFactory.createFoe(params.get(1), 
							Integer.parseInt(params.get(2))));
					else if (params.size()==5) cards.add(CardFactory.createFoe(params.get(1), 
							Integer.parseInt(params.get(2)), Integer.parseInt(params.get(3)), params.get(4)));
					else logger.info("Invalid input");
					break;
				case "Tests":
					if(params.size()==3) cards.add(CardFactory.createTests(params.get(1), 
							Integer.parseInt(params.get(2))));
					else logger.info("Invalid input");
					break;
				case "Event":
					if(params.size()==2) {
						String event = params.get(1);
						if(event.equals("Pox")) cards.add(CardFactory.createEvent(event, new Pox()));
						else if(event.equals("Plague")) cards.add(CardFactory.createEvent(event, 
								new Plague()));
						else if(event.equals("Chivalrous_Deed")) cards.add(CardFactory.createEvent(event, 
								new Deed()));
						else if(event.equals("Prosperity")) cards.add(CardFactory.createEvent(event, 
								new Realm()));
						else if(event.equals("Call_to_Arms")) cards.add(CardFactory.createEvent(event, 
								new Arms()));
						else if(event.equals("King's_Recognition")) cards.add(CardFactory.createEvent(event, 
								new Recognition()));
						else if(event.equals("Queen's_Favor")) cards.add(CardFactory.createEvent(event, 
								new Favor()));
						else if(event.equals("Called_to_Camelot")) cards.add(CardFactory.createEvent(event, 
								new Camelot()));
						else logger.info("Invalid input");
						break;
					}else logger.info("Invalid input");
					break;
				case "Quest":
					if(params.size()==3) cards.add(CardFactory.createQuest(params.get(1), 
							Integer.parseInt(params.get(2))));
					else logger.info("Invalid input");
					break;
				case "Tourney":
					if(params.size()==3) cards.add(CardFactory.createTourney(params.get(1), 
							Integer.parseInt(params.get(2))));
					else logger.info("Invalid input");
					break;
				default: 
					logger.info("Invalid input");
					break;
				}
			}
		}
		catch(FileNotFoundException ex) {
			logger.info("File " + name + " not found");
		}
		catch(IOException ex) {
			logger.info("Error reading file " + name);
		}
		finally {
			try {
				br.close();
			} catch(IOException ex) {
				logger.info("Error closing the buffered reader");
			}
		}
		return deck;
	}
}

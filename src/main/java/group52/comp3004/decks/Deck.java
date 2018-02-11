package group52.comp3004.decks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.EventCard;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.QuestCard;
import group52.comp3004.cards.StoryCard;
import group52.comp3004.cards.Test;
import group52.comp3004.cards.Tourneys;
import group52.comp3004.cards.Weapon;

public class Deck<T> {
	
	List<T> cards;
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
	
	
	public static ArrayList<AdventureCard> createAdventureDeck() {
			
		ArrayList<AdventureCard> cards = new ArrayList<>(Arrays.asList(
				new Ally("Sir Galahad", 15, 0),
				new Ally("Sir Lancelot", 15, 0),
				new Ally("King Arthut", 10, 0),
				new Ally("Sir Tristan", 10, 0),
				new Ally("Sir Pellinore", 10, 0),
				new Ally("Sir Gawain",10, 0),
				new Ally("Sir Percival", 5, 0),
				new Ally("Queen Guinever", 0, 0),
				new Ally("Queen Iseult",0, 0),
				new Ally("Merlin",0, 0),
				new Foe("Dragon",50,70)));
		Test valor = new Test("Test of Valor",0);
		Test temptation = new Test("Test of Tempataion",0);
		Test morgan = new Test("Test of Morgan Le Fey",3);
		Test beast = new Test("Test of the Questing Beast",4);
		Foe giant = new Foe("Giant",40,0);
		Foe mordred = new Foe("Mordred",30,0);
		Foe greenKnight = new Foe("Green Knight",25,40);
		Foe blackKnight = new Foe("Black Knight",25,35);
		Foe evilKnight = new Foe("Evil Knight",20,30);
		Foe saxonKnight = new Foe("Saxon Knight",15,25);
		Foe robberKnight = new Foe("Robber Knight",15,0);
		Foe saxons = new Foe("Saxons",10,20);
		Foe boar = new Foe("Boar",5,15);
		Foe thieves = new Foe("Thieves",5,0);
		Weapon excalibur = new Weapon("Excalibur", 30);
		Weapon lance = new Weapon("Lance", 30);
		Weapon battleAx = new Weapon("Battle Ax", 30);
		Weapon horse = new Weapon("Horse", 10);
		Weapon sword = new Weapon("Sword", 10);
		Weapon dagger = new Weapon("Dagger", 5);
		
		for(int i = 0; i < 2; i++) {
			cards.add(valor);
			cards.add(temptation);
			cards.add(morgan);
			cards.add(beast);
		}
		for(int i = 0; i < 2; i++) cards.add(giant);
		for(int i = 0; i < 4; i++) cards.add(mordred);
		for(int i = 0; i < 2; i++) cards.add(greenKnight);
		for(int i = 0; i < 3; i++) cards.add(blackKnight);
		for(int i = 0; i < 6; i++) cards.add(evilKnight);
		for(int i = 0; i < 8; i++) cards.add(saxonKnight);
		for(int i = 0; i < 7; i++) cards.add(robberKnight);
		for(int i = 0; i < 5; i++) cards.add(saxons);
		for(int i = 0; i < 4; i++) cards.add(boar);
		for(int i = 0; i < 8; i++) cards.add(thieves);
		for(int i = 0; i < 2; i++) cards.add(excalibur);
		for(int i = 0; i < 6; i++) cards.add(lance);
		for(int i = 0; i < 8; i++) cards.add(battleAx);
		for(int i = 0; i < 16; i++) cards.add(sword);
		for(int i = 0; i < 11; i++) cards.add(horse);
		for(int i = 0; i < 6; i++) cards.add(dagger);
		
		return cards;
	}
	public static ArrayList<StoryCard> createStoryDeck(){
		ArrayList<StoryCard> cards = new ArrayList<>(Arrays.asList(
				new QuestCard("Search for the Holy Grail", 5,"All"),
				new QuestCard("Test of the Green Knight",4,"Green Knight"),
				new QuestCard("Search for the Questing Beast",4,""),
				new QuestCard("Defend the Queen's Honor",4,"All"),
				new QuestCard("Rescue the Fair Maiden",3,"Black Knight"),
				new QuestCard("Journey Through the Enchanted Forest",3,"Evil Knight"),
				new QuestCard("Slay the Dragon",3,"Dragon"),
				new Tourneys("Tournament at Camelot", 3),
				new Tourneys("Tournament at Orkney", 2),
				new Tourneys("Tournament at Tintagel",1 ),
				new Tourneys("Tournament at York", 0)
				/*new EventCard("Pox", );
				new EventCard("Plague", );
				new EventCard("Chivalrous Deed", );
				new EventCard("Prosperity Throughout the Realm", );
				new EventCard("Kings Call to Arms", );*/
				));
		QuestCard kingEnemies = new QuestCard("Vanquish King Arthur's Enemies",3,"");
		QuestCard boarHunt = new QuestCard("Boar Hunt",2,"Boar");
		QuestCard  Raiders= new QuestCard("Repel the Saxon Raiders",2,"All Saxons");
		/*EventCard recognition = new EventCard("King's Recognition",);
		EventCard favor = new EventCard("Queen's Favor",);
		EventCard court = new EventCard("Court Called to Camelot",);*/
		for(int i=0;i<2;i++) {
			cards.add(kingEnemies);
			cards.add(boarHunt);
			cards.add(Raiders);
			/*cards.add(recognition);
			cards.add(favor);
			cards.add(court);*/
		}
		
		return cards;
		
		
	
	}
}
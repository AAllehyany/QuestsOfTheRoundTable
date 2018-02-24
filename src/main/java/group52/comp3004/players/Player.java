package group52.comp3004.players;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Ally;
import group52.comp3004.cards.Amour;
import group52.comp3004.cards.CardComparator;
import group52.comp3004.cards.Foe;
import group52.comp3004.cards.Test;
import group52.comp3004.cards.Weapon;
import group52.comp3004.controllers.GameController;
import group52.comp3004.game.GameQuest;
import group52.comp3004.game.GameState;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Player {
	
	private Integer id;
	private Integer shields;
	private Rank rank;
	private Integer battlePoints;
	private Integer requiredShields;
	private List<Integer> weapons;
	private int minShields;
	private ArrayList<AdventureCard> hand;
	private ArrayList<AdventureCard> field;
	private ArrayList<AdventureCard> temp;
	private GameState game;
	private GameQuest quest;
	private GameController controller;
	
	//this needs to be fixed - wrecks the testing
	public Player(Integer id, GameController gc, GameState gs) {
		this.id = id;
		shields = 10;
		rank = Rank.Squire;
		battlePoints = 5;
		requiredShields = 15;
		minShields = 10;
		weapons = new ArrayList<Integer>();
		hand = new ArrayList<>();
		field = new ArrayList<>();
		temp = new ArrayList<>();
		quest = null;
		controller = gc;
		game = gs;
	}
	
	public Player(Integer id) {
		this.id = id;
		shields = 10;
		rank = Rank.Squire;
		battlePoints = 5;
		requiredShields = 15;
		minShields = 10;
		weapons = new ArrayList<Integer>();
		hand = new ArrayList<>();
		field = new ArrayList<>();
		temp = new ArrayList<>();
		quest = null;
	}
	
	public Integer getId() {
		return id;
	}
	
	public Integer getShields() {
		return shields;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public void setGame(GameState game) {
		this.game = game;
	}
	
	public GameState getGame() {
		return this.game;
	}
	
	public GameQuest getQuest() {
		return this.quest;
	}
	
	public void setQuest(GameQuest quest) {
		this.quest = quest;
		//quest.addPlayer(this);
	}
	
	public Integer getBattlePoints(GameState state) {
		return battlePoints + temp.stream().mapToInt(c -> c.getBp()).sum() + field.stream().mapToInt(c -> c.getBp(state)).sum();
	}
	
	public Integer getBattlePoints() {
		return battlePoints + temp.stream().mapToInt(c -> c.getBp()).sum() + field.stream().mapToInt(c -> c.getBp()).sum();
	}
	
	public void clearWeapons() {
		this.weapons.clear();
	}

	public Integer getRequiredShields() {
		return requiredShields;
	}
	
	public void addWeapon(Integer weapon) {
		this.weapons.add(weapon);
	}
	
	public void addShields(Integer shields) {		
		this.shields += shields;
		if(this.shields < minShields) this.shields = minShields;
		if(this.shields >= this.requiredShields) {
			updateRank();
		}
	}
	
	
	public ArrayList<AdventureCard> getHand() {
		return hand;
	}
	
	public void setHand(ArrayList<AdventureCard> hand) {
		this.hand = hand;
	}
	
	
	public void addCardToHand(AdventureCard card) {
		System.out.println("Adding "+card.getName()+" to hand");
		card.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				card.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);//isnt removing the card
				System.out.println(card.getName()+ " clicked");
				//change so card click behaviour changes based on the phase
				/*
				 if(game.getPhase() == SetupQuest){
				 	//add foes and weapons to quest
				 	hand.remove(card);
				 	controller.updateAll();	
				 }
				 else if(game.getPhase() == RunQuest){
				 	//add weapons and allies
				 	field.add(card);
					hand.remove(card);	
					controller.updateAll();	
				 }
				 else if(game.getPhase() == RunTourney){
				 	//add weapons to tourney
				 	hand.remove(card);	
					controller.updateAll();	
				 }
				 else{
				 	//do nothing
				 }
				 */
				field.add(card);
				hand.remove(card);	
				controller.updateAll();	
			}
		});
		this.hand.add(card);
	}
	
	public boolean canPlayWeapon(Weapon weapon) {
		return !this.temp.contains(weapon);
	}
	
	public boolean hasCardInHand(AdventureCard card) {
		return this.hand.contains(card);
	}
	
	public void playCardToField(Ally card) {//why only Ally
		if(!hasCardInHand(card)) return;
		this.field.add(card);
		this.hand.remove(card);
	}
	
	public void playToTemp(AdventureCard card) {//whats this for
		if(!this.hasCardInHand(card)) return;
		if(card instanceof Weapon && !canPlayWeapon((Weapon) card)) return;
		
		this.temp.add(card);
		this.hand.remove(card);
	}
	
	public void addField(Ally card) {
		this.field.add(card);
	}
	
	public void addTemp(AdventureCard card) {
		this.temp.add(card);
	}
	
	public void clearTemp() {
		final Amour amour;
		
		/*this.temp.forEach(card -> {
			if(card instanceof Ally) {
				this.field.add(card);
			}else if(card instanceof Amour) {
				amour = (Amour) card;
			}
		});
		this.temp.clear();
		if(amour!=null)this.temp.add(amour);*/
		
	}

	private void updateRank() {
		minShields = requiredShields;
		if(rank == Rank.Squire) {
			requiredShields = 22;
			rank = Rank.Knight;
			battlePoints = 10;
		}
		else if(rank == Rank.Knight) {
			requiredShields = 32;
			rank = Rank.ChampionKnight;
			battlePoints = 20;
		}
		else if(rank == Rank.ChampionKnight) {
			rank = Rank.KnightOfTheRoundTable;
		}
	}

	public ArrayList<AdventureCard> getField() {
		return this.field;
	}

	public ArrayList<AdventureCard> getTemp() {
		return this.temp;
	}
	
	//not sure what it is
	public AdventureCard removeAlly(Ally ally) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void sortHand(GameState state) {
		ArrayList<AdventureCard> foes = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> tests = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> weps = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> Am = new ArrayList<AdventureCard>();
		ArrayList<AdventureCard> Al = new ArrayList<AdventureCard>();
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Foe) foes.add(this.hand.get(i));
			else if(this.hand.get(i) instanceof Test) tests.add(this.hand.get(i));
			else if(this.hand.get(i) instanceof Weapon) weps.add(this.hand.get(i));
			else if(this.hand.get(i) instanceof Amour) Am.add(this.hand.get(i));
			else Al.add(this.hand.get(i));
		}
		foes.sort(new CardComparator(state));
		weps.sort(new CardComparator());
		Am.sort(new CardComparator());
		Al.sort(new CardComparator(state));
		this.hand.clear();
		this.hand.addAll(Al);
		this.hand.addAll(Am);
		this.hand.addAll(weps);
		this.hand.addAll(foes);
		this.hand.addAll(tests);
	}
	
	public ArrayList<AdventureCard> getDuplicates(){
		ArrayList<AdventureCard> dupes = new ArrayList<AdventureCard>();
		HashSet<AdventureCard> cards = new HashSet<AdventureCard>();
		for(int i=0;i<this.hand.size();i++) {
			if(!cards.add(this.hand.get(i))) dupes.add(this.hand.get(i));
		}
		return dupes;
	}
	
	public boolean hasTest() {
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Test) return true;
		}
		return false;
	}
	
	public int countFoes() {
		int count = 0;
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Foe) count++;
		}
		return count;
	}
	
	public int countFoes(int bp) {
		int count =0;
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Foe && this.hand.get(i).getBp()<bp) count++;
		}
		return count;
	}
	
	public ArrayList<AdventureCard> getFoes(int bp){
		ArrayList<AdventureCard> foes = new ArrayList<AdventureCard>();
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Foe && this.hand.get(i).getBp()<bp) {
				foes.add(this.hand.get(i));
			}
		}
		return foes;
	}
	
	public ArrayList<AdventureCard> getUniqueFoes(GameState state){
		ArrayList<AdventureCard> uFoes = new ArrayList<AdventureCard>();
		HashSet<Integer> bps = new HashSet<Integer>();
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Foe) {
				Foe f;
				f = (Foe) this.hand.get(i);
				if(bps.add(f.getBp(state))) uFoes.add(this.hand.get(i));
			}
		}
		return uFoes;
	}
	
	public int numUniqueFoes(GameState state) {
		int numUFoes = 0;
		HashSet<Integer> bps = new HashSet<Integer>();
		for(int i=0;i<this.hand.size();i++) {
			if(this.hand.get(i) instanceof Foe) {
				Foe f = (Foe) this.hand.get(i);
				if(bps.add(f.getBp(state))) numUFoes++;
			}
		}
		return numUFoes;
	}
	
	public int getBPInHand(GameState state) {
		int total = 0;
		for(int i=0;i<this.hand.size();i++) {
			if(!(this.hand.get(i) instanceof Test || this.hand.get(i) instanceof Foe)) {
				if(this.hand.get(i) instanceof Ally) total += this.hand.get(i).getBp(state);
				else total += this.hand.get(i).getBp();
			}
		}
		return total;
	}
	
	public boolean hasAmour() {
		for(int i=0;i<this.temp.size();i++) {
			if (this.temp.get(i) instanceof Amour) return true;
		}
		return false;
	}
}

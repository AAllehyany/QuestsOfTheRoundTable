package group52.comp3004.players;

import java.util.ArrayList;
import java.util.List;

import group52.comp3004.cards.AdventureCard;
import group52.comp3004.cards.Weapon;

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
	
	public Integer getBattlePoints() {
		return battlePoints + weapons.stream().mapToInt(Integer::intValue).sum();
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
		this.hand.add(card);
	}
	
	public boolean canPlayWeapon(Weapon weapon) {
		return !this.field.contains(weapon);
	}
	
	public boolean hasCardInHand(AdventureCard card) {
		return this.hand.contains(card);
	}
	
	public void playCardToField(AdventureCard card) {
		if(card instanceof Weapon && !canPlayWeapon((Weapon) card)) return;
		//if(!hasCardInHand(card)) return;
		this.field.add(card);
		this.hand.remove(card);
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
		// TODO Auto-generated method stub
		return this.field;
	}
}

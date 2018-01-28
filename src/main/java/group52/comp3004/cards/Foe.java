package group52.comp3004.cards;

import java.util.HashSet;

public class Foe extends AdventureCard{

	private final int bp;
	private final int highBp;
    private HashSet<Weapon> weapons;
	
	public Foe(String name, int bp, int highBp) {
		super(name);
		this.bp = bp;
		this.highBp = highBp;
        weapons = new HashSet<Weapon>();
		// TODO Auto-generated constructor stub
	}
	
	public int getBP() {
        Iterator<Weapon> itr = this.weapons.iterator();
        int weaponsBP = 0;
        while(itr.hasNext()){
            weaponsBP += this.weapons.next().getBP();
        }
		return this.bp + weaponsBP;
	}

    public Weapon addWeapon(Weapon wep){
		boolean added = this.weapons.add(wep);
		if(!added){
			System.out.println("FOE ALREADY HAS THIS WEAPON!\n");
			return wep;
		}else{
			return null;
		}
    }

    public void clearWeapons(){
        Iterator<Weapon> = this.weapons.iterator();
        while(itr.hasNext()){
            adventureDeck.discard(itr.next());
        }
        this.weapons.clear();
    }
}
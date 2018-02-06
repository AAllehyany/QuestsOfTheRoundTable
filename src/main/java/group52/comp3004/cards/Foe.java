package group52.comp3004.cards;

import java.util.HashSet;
import java.util.Iterator;

public class Foe extends AdventureCard{

	private final int bp;
	private final int highBp;
	private HashSet<Weapon> weapons;
	
	public Foe(String name, int bp, int highBp) {
		super(name);
		this.bp = bp;
		this.highBp = highBp;
		this.weapons = new HashSet<Weapon>();
		// TODO Auto-generated constructor stub
	}
	
	public int getBP() {
        Iterator<Weapon> itr = this.weapons.iterator();
        int weaponsBP = 0;
        while(itr.hasNext()){
            weaponsBP += itr.next().getBP();
        }
		return this.bp + weaponsBP;
	}

    public boolean hasWeapon(Weapon wep){
        return this.weapons.contains(wep);
    }

    public boolean addWeapon(Weapon wep){
		return this.weapons.add(wep);
    }

    public void clearWeapons(){
        this.weapons.clear();
    }
}

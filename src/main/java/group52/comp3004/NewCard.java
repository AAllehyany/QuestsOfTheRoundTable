package group52.comp3004;

import java.util.ArrayList;
import java.util.List;

public class NewCard {
	
	private int battlePoint,highBattlePoint,bid,totalBattlePoint;
	private CardType name;
	private String specialPower;
	private List addsOn= new ArrayList();
	private  String type;
	boolean isFacedUp;
	
	public NewCard(CardType name,int highBattlePoint,int battlePoint,int bid,List addsOn, String type,String specialPower) {
		this.name= name;
		this.highBattlePoint= highBattlePoint;
		this.battlePoint= battlePoint;
		this.specialPower= specialPower;
		this.type =type;
		isFacedUp = false;
	}
	public String getPower(){
		return this.specialPower;
	}
	
	public String getName() {
		return name.printCT();
	}
	
	public int getBattlePoint() {
		return this.battlePoint;
	}
	public int getHighBattlePoint() {
		return this.highBattlePoint;
	}
	
	public String getType() {
		return this.type;
	}
	
	public int getBid() {
		return this.bid;
	}
	
	public int getTotalBP() {
		return this.totalBattlePoint;
	}
	
	public String toString() {
		String str ="";
		if(isFacedUp) {
			str+= name.printCT();
		}else {
			str = "face down";
		}
		return str;
	}
}

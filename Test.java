package group52.comp3004;

public class Test extends AdventureCard {

	private int bid, minimalBid;
	public Test(CardType name) {
		super(name);
		bid=0;
		minimalBid=0;
		// TODO Auto-generated constructor stub
	}
	public void setBid(int bid) {
		this.bid=bid;
	}
	public void setMinBid(int minimalBid) {
		this.minimalBid=minimalBid;
	}
	public int getBid() {
		return this.bid;
	}
	public int getMinBid() {
		return this.minimalBid;
	}
}

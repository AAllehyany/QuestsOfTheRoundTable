package group52.comp3004.cards;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import group52.comp3004.game.*;
import group52.comp3004.players.Player;

/**
 * Adds functionally for ally type adventure cards.
 * @author Sandy
 *
 */
public class Ally extends AdventureCard
{
	private String prereq;
	private int bonusbp;
	private int bonusbid;
	private boolean merlin = false;
	
	final static Logger logger = Logger.getLogger(Ally.class);
	// TODO special abilities

	/**
	 * Constructor for ally without a special case. <p>Should not be used for Merlin</p>
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 * @param bp Battle point value of the ally
	 * @param bids The additional points that the ally adds to a test.
	 */
	public Ally(String name, int bp, int bids) {
		super(name);
		this.bp = bp;
		this.bids = bids;
		this.type = "ally";
		if(name.equals("Merlin")) this.merlin = true;	
	}
	
	/**
	 * Constructor for ally with a special case. <p>Should not be used for Merlin</p>
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 * @param bp Battle point value of the ally
	 * @param bids The additional points that the ally adds to a test.
	 * @param prereq The required card(s) that need to be in play in order for a special ability to be used.
	 * @param bonusbp The amount of bonus battle points the ally gives when its special ability is used.
	 * @param bonusbid The amount of bonus bids that the ally gives with its special ability.
	 */
	public Ally(String name, int bp, int bids, String prereq, int bonusbp, int bonusbid) {
		super(name);
		this.bp = bp;
		this.bids = bids;
		this.prereq = prereq;
		this.bonusbp = bonusbp;
		this.bonusbid = bonusbid;
		this.type = "ally";
		if(name.equals("Merlin")) this.merlin = true;
	}
	
	/**
	 * Constructor for Merlin ally cards. Set Merlin boolean to true if name is Merlin. <p>Should only be used for Merlin</p>
	 * @param name The type of card. Needs to match a image file in order to load the correct face.
	 */
	public Ally(String name) {
		super(name);
		this.type = "ally";
		if(name.equals("Merlin")) this.merlin = true;
	}
	
	/**
	 * Determine if an ally's special ability can be used
	 * @param state the current conditions of the game
	 * @return True: Can use the ally's special ability
	 * 		   <p>False: Unable to use special ability
	 */
	public boolean bonusSatisfied(GameState state) {
		if(this.prereq==null) return false;
		if(state.getCurrentQuest()!=null && 
				this.prereq.equals(state.getCurrentQuest().getQuest().getName())) {
			return true;
		}
		ArrayList<Player> players = new ArrayList<Player>(state.getAllPlayers());
		for(int i=0;i<players.size();i++) {
			ArrayList<AdventureCard> field = players.get(i).getField();
			for(int j=0;j<field.size();j++) {
				if(field.get(j).getName().equals(this.prereq)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Merlin's special ability is to see a single stage in a quest. This function flips the stage selected by the parameter stage.
	 * <p>Should be followed by an EndMerlinSpecial flip the card back over when player is done.
	 * @param state current conditions of the game
	 * @param stage The stage of the quest that the player has selected. Returns error if stage number is greater than numbers of stages in quest..
	 * @return list of all the cards in the selected stage
	 */
	public ArrayList<AdventureCard> StartMerlinSpecial(GameState state, int stage) {
		ArrayList<AdventureCard> cards = new ArrayList<AdventureCard>();
		if(!this.merlin) {
			logger.info("NOT MERLIN");
			return null;
		}
		GameQuest q = state.getCurrentQuest();
		if(q==null) return null;
		if(stage>=q.getNumStages()) {
			logger.info("INVALID STAGE");
			return null;
		}
		Stage s = q.getStage(stage);
		if(s.isTestStage()) {
			return cards;
		}else {
			Foe f = s.getFoe();
			cards.add(f);
			cards.addAll(f.getWeapons());
			for(int i=0;i<cards.size();i++) {
				cards.get(i).flipUp();
			}
			logger.info("Merlin's effect has been used");
			return cards;
		}
	}
	
	/**
	 * Executed when player has finished looking at a selected stage. Flips the selected stage cards back over.
	 * <p>Needs to be used after a StartMerlinSpecial call</p>
	 * If boolean returns false then a card that is not Merlin has used his ability and an error has occured.
	 * @param cards the list of stage cards from 
	 * @return True: Successfully turned down all cards
	 * 		   <p>False: Card to that is not Merlin tried to use his special ability
	 */
	public boolean EndMerlinSpecial(ArrayList<AdventureCard> cards) {
		if(!this.merlin) return false;
		for(int i=0;i<cards.size();i++) {
			cards.get(i).flipDown();
		}
		logger.info("Merlin's effect has ended");
		return true;
	}

	/**
	 * Get the total battle power provided by an ally. Total power is base bp plus what is provided by the bonus if the special ability is active.
	 */
	public int getBp(GameState state) {
		if (bonusSatisfied(state)) {
			logger.info(super.getName() + " adds " + (bonusbp) + " battle points");
			return bonusbp;
		}
		logger.info(super.getName() + " adds " + bp + " battle points");
		return bp;
	}
	
	/**
	 * Get the total bid amount provided by an ally. Total power is base bid plus what is provided by the bonus if the special ability is active.
	 */
	public int getBids(GameState state) {
		if (bonusSatisfied(state)) {
			logger.info(super.getName() + " adds " + (bonusbid) + " bids");
			return bonusbid;
		}
		logger.info(super.getName() + " adds " + bids + " bids");
		return bids;
	}
	

}

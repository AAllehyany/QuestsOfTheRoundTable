package group52.comp3004.game;

/**
 * Stores the various states that the game can exist in.
 * <p>State diagram image included in image resources folder.
 * @author Sandy
 * <p>Intro -> Intro screen for the game</p>
 * <p>TurnStart -> Start of a new round</p>
 * <p>RevealStory -> story is added to field</p>
 * <p>TurnEnd ->Resets the board for the next round</p>
 * <p>SponsorQuest -> Players choose to sponser the quest</p>
 * <p>SetupQuest -> The sponsering player sets up the quest</p>
 * <p>RunQuest -> Players interact witht the quest</p>
 * <p>EndQuest -> Shields are dealt out</p>
 * <p>HandleEvent -> Handle a story event</p>
 * <p>SponsorTourney -> Player choose to sponsor the quest</p>
 * <p>JoinTourney -> Players play their adventure cards for tourneys</p>
 * <p>RunTourney -> Compare bids and hand out shields to the winner</p>
 * <p>SetUpTourney -> ?</p>
 * <p>Broken -> The game is broken, despair</p>
 * <p>PlayQuest -> Playing in the quest</p>
 * <p>SecondRound -> ?</p>
 * <p>Arms -> ?</p>
 * <p>DiscardForTest -> ?</p>
 */
public enum Phase {
	Intro, //Intro screen for the game
	
	TurnStart, //Start of a new round
	RevealStory, //story is added to field
	TurnEnd, //Resets the board for the next round
	
	SponsorQuest, //Players choose to sponser the quest
	SetupQuest, //The sponsering player sets up the quest
	RunQuest, //Players interact witht the quest
	EndQuest, //Shields are dealt out
	
	HandleEvent, //Handle a story event
	
	SponsorTourney, //Player choose to sponsor the quest
	JoinTourney, //Players play their adventure cards for tourneys
	RunTourney, //Compare bids and hand out shields to the winner
	SetUpTourney,
	Broken, //The game is broken, despair
	PlayQuest, // Playing in the quest
	SecondRound, //?
	Arms, //?
	DiscardForTest //?
}

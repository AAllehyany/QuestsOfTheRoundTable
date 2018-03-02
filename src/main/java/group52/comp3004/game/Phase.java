package group52.comp3004.game;

//PURPOSE: Stores the various states that the game can exist in
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
	Broken //The game is broken, despair
, PlayQuest // Playing in the quest
, SecondRound,
Arms, DiscardForTest
}

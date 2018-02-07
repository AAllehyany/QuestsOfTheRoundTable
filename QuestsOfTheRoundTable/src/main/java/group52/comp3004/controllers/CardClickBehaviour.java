package group52.comp3004.controllers;

import group52.comp3004.cards.AdventureCard;

@FunctionalInterface
public interface CardClickBehaviour {

	void handClick(AdventureCard card);
}

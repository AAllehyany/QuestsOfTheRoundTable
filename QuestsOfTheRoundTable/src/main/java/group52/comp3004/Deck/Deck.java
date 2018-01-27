package group52.comp3004.Deck;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

public class Deck<Card>{

    public DeckType type; // denote if the deck is the story deck or adventure deck
    private int size = 0; // store the number of cards in the deck
    public ArrayList<Card> deck = new ArrayList<Card>();    // store cards in the deck
    public ArrayList<Card> discard = new ArrayList<Card>(); // store cards in a discard pile ready to be shuffled back into the deck
    
    public Deck(DeckType t){
        this.type = t;
        if(t == DeckType.Adventure){
            System.out.println("ADVENTURE DECK CREATED\n");
        }else if(t == DeckType.Story){
            System.out.println("STORY DECK CREATED\n");
        }else{
            System.out.println("INVALID DECK TYPE\n");
        }
    }

    public void add(Card c){
        this.deck.add(c);
        this.size++;
    }

    // draw a number of cards from the top of the deck and returns the card that is drawn
    public ArrayList<Card> draw(int num){
        ArrayList<Card> cards = new ArrayList<Card>();
        Random rnd = new Random();
        if(this.size<num){
            while(this.size>0){
                cards.add(this.deck.remove(rnd.nextInt(this.size)));
                this.size--;
                num--;
            }
            this.deck = new ArrayList<Card>(this.discard);
            this.discard.clear();
            while(num>0){
                cards.add(this.deck.remove(rnd.nextInt(this.size)));
                this.size--;
                num--;
            }
        }else{
            while(num>0){
                cards.add(this.deck.remove(rnd.nextInt(this.size)));
                this.size--;
                num--;
            }
        }
        return cards;
    }

    /* play a card and place it in a deck's discard pile. Also to be used at the beginning of the game to
    create the deck */
    public Card discard(Card c){
        this.discard.add(c);
        return c;
    }
}

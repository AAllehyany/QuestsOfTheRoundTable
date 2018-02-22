package group52.comp3004.Deck;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

public class Deck<T>{

    public DeckType type; // denote if the deck is the story deck or adventure deck
    private int size = 0; // store the number of cards in the deck
    public ArrayList<T> deck = new ArrayList<T>();    // store cards in the deck
    public ArrayList<T> discard = new ArrayList<T>(); // store cards in a discard pile ready to be shuffled back into the deck
    
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

    public void add(T c){
        this.deck.add(c);
        this.size++;
    }

    // draw a number of cards from the top of the deck and returns the card that is drawn
    public ArrayList<T> draw(int num){
        ArrayList<T> cards = new ArrayList<T>();
        Random rnd = new Random();
        if(this.size<num){
            while(this.size>0){
                cards.add(this.deck.remove(rnd.nextInt(this.size)));
                this.size--;
                num--;
            }
            this.deck = new ArrayList<T>(this.discard);
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
    public T discard(T c){
        this.discard.add(c);
        return c;
    }
}

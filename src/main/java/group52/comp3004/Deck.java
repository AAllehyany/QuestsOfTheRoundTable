import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

public class Deck<Card>{

    public String type; // denote if the deck is the story deck or adventure deck
    private int size = 0;
    private ArrayList<Card> contents = new ArrayList<Card>(); // store contents while initially creating the deck
    public Stack<Card> deck = new Stack<Card>();    // store cards in the deck
    public ArrayList<Card> discard = new ArrayList<Card>(); // store cards in a discard pile ready to be shuffled back into the deck
    
    public Deck(String t){
        this.type = t;
    }

    // used solely for the purpose of building the deck at the beginning of the game
    public add(Card c){
        this.contents.add(c);
    }

    // internal function to shufffle cards either at the beginning of the game or when the deck is empty.
    private shuffle(ArrayList<Card> cards){
        Random rnd = new Random();
        Stack<Card> d;
        while(!cards.isEmpty()){
            this.deck.push(cards.remove(rnd.nextInt(cards.size())));
            this.size++;
        }
    }

    // draw a card from the top of the deck and returns the card that is drawn
    public Card draw(){
        if(this.deck.empty()){
            shuffle(this.discard);
        }
        this.size--;
        this.discard.add(this.deck.peek());
        return this.deck.pop();
    }

    // play a card and place it in a deck's discard pile
    public Card discard(Card c){
        this.discard.add(c);
        return c;
    }
}

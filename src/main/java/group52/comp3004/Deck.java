import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

public class Deck{
    private static String[] types = {"ADVENTURE", "STORY"};
    private static int[] sizes = {125, 28};
    
    // need to create these two arrays for use later
    private ArrayList<Card> adventure_deck;
    private ArrayList<Card> story_deck;

    public String type;
    private int size;
    public Stack<Card> deck;
    public ArrayList<Card> discard;

    private shuffle(ArrayList<Card> cards){
        Random rnd = new Random();
        Stack<Card> d;
        while(!cards.isEmpty()){
            d.push(cards[rnd.nextInt(cards.size())];
        }
        return d;
    }
    
    public Deck(String t){
        if t.compareToIgnoreCase(types[0]){
            this.type = types[0];
            this.size = sizes[0];
            this.deck = shuffle(adventure_deck);
        }else if t.compareToIgnoreCase(types[1]){
            this.type = types[1];
            this.size = sizes[1];
            this.deck = shuffle(story_deck);
        }else{
            System.out.println("Wrong type\n");
        }
    }

    public Card draw(){
        if(this.deck.empty()){
            this.deck = shuffle(this.discard);
            this.discard.clear();
            this.size = this.discard.size();
        }
        this.size--;
        this.discard.add(this.deck.peek());
        return this.deck.pop();
    }
}
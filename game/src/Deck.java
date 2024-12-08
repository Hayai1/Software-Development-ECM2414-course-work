import java.util.LinkedList;
import java.util.Queue;

// may not actually need to be threaded after all
// will need to lock the getCard and addCard methods though
public class Deck{
    int ID;
    int maxCards;
    Queue<Card> deck;
    public Deck(int ID, int maxCards) {
        this.ID = ID;
        this.maxCards = maxCards;
        deck = new LinkedList<Card>();
    }
    public int getID() {
        return ID; 
    }
    public int count() { 
        return deck.size(); 
    }
    public synchronized void addCard(Card card) {
        while (deck.size() == maxCards){
            try {
                wait();
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
        }
        deck.add(card);
        notifyAll();
    }
    public synchronized Card getCard() {
        notifyAll();
        return deck.remove();
    }

}

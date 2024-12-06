import java.util.Queue;

// may not actually need to be threaded after all
// will need to lock the getCard and addCard methods though
public class Deck{
    int ID;
    int maxCards;
    Queue<Card> Deck;
    public Deck(int ID, int maxCards) {
        this.ID = ID;
        this.maxCards = maxCards;
    }
    public int getID() {
        return ID; 
    }
    public int count() { 
        return 0; 
    }
    public void addCard(Card card) {
        Deck.add(card);
    }
    public Card getCard() {
        return Deck.remove(); 
    }
}

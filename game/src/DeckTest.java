import static org.junit.Assert.assertEquals;

import java.util.Queue;

import org.junit.Test;



public class DeckTest {

    private Deck testDeck;
    
    @Test
    public void getIDTest(){
        testDeck = new Deck(1, 4);
        assertEquals(1, testDeck.getID());
    }

   


    @Test
    public void addAndRemoveCardTest(){
        testDeck = new Deck(1, 4);

        Card card1 = new Card(0);
        Card card2 = new Card(1);
        Card card3 = new Card(2);
        Card card4 = new Card(3);

        //add card1 to deck
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);
        testDeck.addCard(card4);

        assertEquals(card1,testDeck.getCard());
        assertEquals(card2,testDeck.getCard());
        assertEquals(card3,testDeck.getCard());
        assertEquals(card4,testDeck.getCard());
    }

     @Test
    public void countTest(){
        testDeck = new Deck(1, 4);
        Card card1 = new Card(0);
        Card card2 = new Card(1);
        Card card3 = new Card(2);
        Card card4 = new Card(3);

        //add card1 to deck
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);
        testDeck.addCard(card4);
        assertEquals(4,testDeck.count());
    }
    
    @Test
    public void getDeckTest(){
        testDeck = new Deck(1, 4);
        Card card1 = new Card(0);
        Card card2 = new Card(1);
        Card card3 = new Card(2);
        Card card4 = new Card(3);
        testDeck.addCard(card1);
        testDeck.addCard(card2);
        testDeck.addCard(card3);
        testDeck.addCard(card4);
        assertEquals(card1, testDeck.getDeck().remove());
        assertEquals(card2, testDeck.getDeck().remove());
        assertEquals(card3, testDeck.getDeck().remove());
        assertEquals(card4, testDeck.getDeck().remove());
    }
}

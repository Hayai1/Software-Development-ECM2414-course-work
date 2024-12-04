import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.junit.Before;


public class DeckTest {

    private Deck testDeck;

    @Before
    void setUp() throws Exception{
        int ID = 1;
        int maxCards = 4;
        Deck testDeck = new Deck(ID, maxCards);
    }
    
    @Test
    void getIDTest(){
        assertEquals(1, testDeck.getID());
    }

    @Test
    void countTest(){
        assertEquals(4,testDeck.count());
    }


    @Test
    void addAndRemoveCardTest(){
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
    
}

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    
    int Id = 1;
    Deck LDeck = new Deck(0, 5);
    Deck RDeck = new Deck(2, 5);
    Player TestSubject = new Player(Id,LDeck,RDeck);
    Player Winner = new Player(2,LDeck,RDeck);
    Card First = new Card(1);
    Card Second = new Card(2);
    Card Third = new Card(3);
    Card Fourth = new Card(4);
    Card[] handExp = {First,Second,Third,Fourth};

    @Before
    public void CreateHand(){
        for (Card card : handExp) {
            TestSubject.addCard(card);
        }
        for(int i = 0; i < 4; i ++){
            Winner.addCard(First);
        }
    }

    @Test
    public void CreatePlayer(){
        Player myPlayerTest = new Player(Id,LDeck,RDeck);
    }

    @Test
    public void ChooseRemoveReturnsCard(){
        assert (TestSubject.ChooseCardToRemove().getClass().getSimpleName() == "Card");
    }

    @Test
    public void getHandTest(){
        assertEquals(handExp, TestSubject.getHand());
    }

    @Test
    public void addTest(){
        Card[] FirstHand = {First};
        TestSubject.addCard(First);
        assertEquals(FirstHand, TestSubject.getHand());
    }

    @Test
    public void CheckWinTestTrue(){
        assertEquals(true, Winner.CheckWin());
    }

    @Test
    public void CheckWinTestFalse(){
        assertEquals(false, TestSubject.CheckWin());
    }

    @Test
    public void TestID(){
        assertEquals(1, TestSubject.getID());
    }
}

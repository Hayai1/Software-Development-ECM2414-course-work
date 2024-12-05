import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    
    int Id = 1;
    int LDeck = 0;
    int RDeck = 2;
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
            TestSubject.add(card);
        }
        for(int i = 0; i < 4; i ++){
            Winner.add(First);
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
    public void GetHandTest(){
        assertEquals(handExp, TestSubject.GetHand());
    }

    @Test
    public void addTest(){
        Card[] FirstHand = {First};
        TestSubject.add(First);
        assertEquals(FirstHand, TestSubject.GetHand);
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

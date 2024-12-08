import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    String[] finishedCheckingForWin;
    boolean[] flag; 
    StringBuffer playerWin = null;
    int Id = 1;
    Card testCard = new Card(2);
    Deck LDeck = new Deck(0, 5);
    Deck RDeck = new Deck(2, 5);
    Player TestSubject = new Player(Id, LDeck, RDeck, playerWin, finishedCheckingForWin, flag);
    Player Winner = new Player(Id, LDeck, RDeck, playerWin, finishedCheckingForWin, flag);
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
        Player myPlayerTest = new Player(Id, LDeck, RDeck, playerWin, finishedCheckingForWin, flag);
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
        assertEquals(FirstHand[0], TestSubject.getHand()[0]);
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

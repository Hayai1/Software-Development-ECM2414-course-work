import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CyclicBarrier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    CyclicBarrier barrier = new CyclicBarrier(1);
    Player TestSubject = new Player(Id, LDeck, RDeck, playerWin,barrier);
    Player Winner = new Player(Id, LDeck, RDeck, playerWin,barrier);
    Card First = new Card(1);
    Card Second = new Card(2);
    Card Third = new Card(3);
    Card Fourth = new Card(4);
    Card[] handExp = {First,Second,Third,Fourth};

    @Test
    public void CreatePlayerTest(){
        try{
            Player myPlayerTest = new Player(Id, LDeck, RDeck, playerWin,barrier);
        } 
        catch(Exception e){
            System.err.println(e);
        }
    }
    @Test
    public void addTest(){
        Card[] FirstHand = {First};
        TestSubject.addCard(First);
        assertEquals(FirstHand[0], TestSubject.getHand()[0]);
    }

    @Test
    public void ChooseCardToRemoveTest(){
        //nullpointer
        Player myPlayerTest = new Player(Id, LDeck, RDeck, playerWin,barrier);
        myPlayerTest.addCard(First);
        myPlayerTest.addCard(Second);
        myPlayerTest.addCard(Third);
        myPlayerTest.addCard(Fourth);
        assertEquals(myPlayerTest.ChooseCardToRemove().getValue(), 2);
    }

    @Test
    public void getHandTest(){
        Player myPlayerTest = new Player(Id, LDeck, RDeck, playerWin,barrier);
        myPlayerTest.addCard(First);
        myPlayerTest.addCard(Second);
        myPlayerTest.addCard(Third);
        myPlayerTest.addCard(Fourth);
        assertEquals(myPlayerTest.getHand(), handExp);
    }

    @Test
    public void TestID(){
        assertEquals(1, TestSubject.getID());
    }
    

    @Test
    public void CheckWinTestTrue(){
        Player myPlayerTest = new Player(Id, LDeck, RDeck, playerWin,barrier);
        myPlayerTest.addCard(new Card(1));
        myPlayerTest.addCard(new Card(1));
        myPlayerTest.addCard(new Card(1));
        myPlayerTest.addCard(new Card(1));
        assertEquals(true, myPlayerTest.CheckWin());
    }

    @Test
    public void CheckWinTestFalse(){
        Player myPlayerTest = new Player(Id, LDeck, RDeck, playerWin,barrier);
        myPlayerTest.addCard(new Card(1));
        myPlayerTest.addCard(new Card(2));
        myPlayerTest.addCard(new Card(1));
        myPlayerTest.addCard(new Card(1));
        assertEquals(false, myPlayerTest.CheckWin());
    }
    @Test
    public void DrawNewCardTest(){
        Player myPlayerTest = new Player(Id, LDeck, RDeck, playerWin,barrier);
        myPlayerTest.addCard(new Card(1));
        myPlayerTest.addCard(new Card(2));
        myPlayerTest.addCard(new Card(1));
        myPlayerTest.addCard(new Card(1));
        LDeck.addCard(new Card(40));
        assertEquals(40, myPlayerTest.DrawNewCard().getValue());
    }

    
    @Test
    public void DiscardTest(){
        Player myPlayerTest = new Player(Id, LDeck, RDeck, playerWin,barrier);
        Card card = new Card(50);
        myPlayerTest.addCard(card);
        myPlayerTest.addCard(new Card(2));
        myPlayerTest.addCard(new Card(3));
        myPlayerTest.addCard(new Card(4));
        myPlayerTest.Discard(card);
        assertEquals(card.getValue(), RDeck.getCard().getValue());
    }
    @Test
    public void CreateFileTest(){
        Player myPlayerTest = new Player(50, LDeck, RDeck, playerWin,barrier);
        Card card = new Card(50);
        myPlayerTest.addCard(card);
        myPlayerTest.addCard(new Card(2));
        myPlayerTest.addCard(new Card(3));
        myPlayerTest.addCard(new Card(4));
        try {
            myPlayerTest.CreateFile();
        } catch (Exception e) {
            System.err.println(e);
        }
        try {
            myPlayerTest.CreateFile();

        } catch (Exception e) {
            System.err.println(e);
        }
    }
    @Test
    public void writeToFileTest() throws FileNotFoundException, IOException{
        Player myPlayerTest = new Player(50, LDeck, RDeck, playerWin,barrier);
        Card card = new Card(50);
        myPlayerTest.addCard(card);
        myPlayerTest.addCard(new Card(2));
        myPlayerTest.addCard(new Card(3));
        myPlayerTest.addCard(new Card(4));
        try {
            myPlayerTest.CreateFile();
        } catch (Exception e) {
            System.err.println(e);
        }
        myPlayerTest.writeToFile("testing 1 2 3");
         try (BufferedReader reader = new BufferedReader(new FileReader("resources\\player50_output.txt"))) {
            String line = reader.readLine();
            assertTrue(line.toString().equals("testing 1 2 3"));
        }
    }
    @Test
    public void addAndGetTest(){
        Player myPlayerTest = new Player(50, LDeck, RDeck, playerWin,barrier);
        Card card = new Card(50);
        myPlayerTest.addCard(card);
        myPlayerTest.addCard(new Card(2));
        myPlayerTest.addCard(new Card(3));
        myPlayerTest.addCard(new Card(4));
        try{
            myPlayerTest.addAndGet(card);
        }
        catch (Exception e){
            System.out.println(e);
        }
        
    }
    
}


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.Stack;

public class PackTest {
    private String TestPath = "resources\\cardsTest.txt";
    private Pack TestPack;
    @Test
    public void testCreatePack(){
        // tests creating a pack object
        try {
            Pack TestPack = new Pack(TestPath);
        } catch (FileNotFoundException e) {
            System.out.println("path not found, try another! path you used: " + TestPath);
        }
    }

    @Test
    public void testGetCard(){
        Stack<Card> TestCards = new Stack<Card>();
        
        for (int cardNum : new int[]{7,1,10,4,2,8,12,1}){
            TestCards.add(new Card(cardNum));
        }
        try{
            Pack TestPack = new Pack(TestPath);
            TestPack.createCards(TestPath);
            assertEquals(TestCards.pop().getValue(), TestPack.getCard().getValue());
        }
        catch(FileNotFoundException exception){
            System.out.println("path not found, try another! path you used: " + TestPath);
        }  
    }
    
    @Test
    public void testCreateCards(){
        Stack<Card> TestCards = new Stack<Card>();
        
        for (int cardNum : new int[]{7,1,10,4,2,8,12,1}){
            TestCards.add(new Card(cardNum));
        }
        System.out.println(TestCards);
        try {
            Pack TestPack = new Pack(TestPath);
            TestPack.createCards(TestPath);
            Stack<Card> ActualTestCards = TestPack.getPack();
            assertEquals(TestPack.getCard().getValue(), TestCards.pop().getValue());//1
            assertEquals(TestPack.getCard().getValue(), TestCards.pop().getValue());//12
            assertEquals(TestPack.getCard().getValue(), TestCards.pop().getValue());//8
            assertEquals(TestPack.getCard().getValue(), TestCards.pop().getValue());//2
            assertEquals(TestPack.getCard().getValue(), TestCards.pop().getValue());//4
            assertEquals(TestPack.getCard().getValue(), TestCards.pop().getValue());//10
            assertEquals(TestPack.getCard().getValue(), TestCards.pop().getValue());//1
            assertEquals(TestPack.getCard().getValue(), TestCards.pop().getValue());
        } catch (FileNotFoundException e) {
            System.out.println("path not found, try another! path you used: " + TestPath);
        }
    }

    @Test
    public void testInvalidPath(){
        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            try{
                Pack invalidPack = new Pack("not a filename");
            }
            catch(FileNotFoundException notFound){
                System.out.println("exception thrown");
                throw new FileNotFoundException();
            }
        });
    }
}


import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CardGameTest {

    @Test
    public void createDecksTest(){
        Deck[] testdecks = CardGame.createDecks(4);
        assertTrue(testdecks instanceof Deck[]);
        assertTrue(testdecks.length == 4);

    }

    @Test
    public void createPlayersTest(){
        Deck[] testdecks = CardGame.createDecks(4);
        Player[] testPlayers = CardGame.createPlayers(4, testdecks);
        assertTrue(testPlayers instanceof Player[]);
        assertTrue(testPlayers.length == 4);
    }
    @Test
    public void getPlayersTest(){
        Deck[] testdecks = CardGame.createDecks(4);
        CardGame.createPlayers(4, testdecks);
        assertTrue(CardGame.getPlayers() instanceof Player[]);
        assertTrue(CardGame.getPlayers().length == 4);
    }

    /*@Test
    public void createPackTest(){
        assertTrue(CardGame.createPack(2) instanceof Pack);
    } */
    
    @Test
    public void ValidPackTest(){
        try{
            Pack testPack1 = new Pack("32CardPack.txt");
            Pack testPack2 = new Pack("24CardPack.txt");
            assertTrue(CardGame.validPack(4, testPack1));
            assertTrue(!CardGame.validPack(3, testPack1));
            assertTrue(CardGame.validPack(3, testPack2));
            assertTrue(!CardGame.validPack(4, testPack2));
        }
        catch (Exception e){
            System.out.println("pack not found");
        }
        
 
    }
    @Test
    public void getPackTest(){
        CardGame.getPack();
    }
}

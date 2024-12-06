
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import org.junit.Before;

public class CardGameTest {
    private CardGame testCardGame; 
    @Before
    public void setUp() throws Exception{
        testCardGame = new CardGame();
    }

    @Test
    public void createPlayersTest(){
        assertTrue(testCardGame.createPlayers() instanceof Player[4]);
        assertTrue(testCardGame.createPlayers().length == 4);
    }
    @Test
    public void getPlayersTest(){
        assertTrue(testCardGame.getPlayers() instanceof Player[4]);
        assertTrue(testCardGame.getPlayers().length == 4);
    }
    @Test
    public void createPackTest(){
        var pack = testCardGame.createPack();
        assertTrue(pack instanceof Pack);
    }
    @Test
    public void ValidPackTest(){  
        assertTrue(testCardGame.validPack(new Pack("32CardPack.txt"), 4));
        assertTrue(testCardGame.validPack(new Pack("24CardPack.txt"), 3));
        assertTrue(testCardGame.validPack(new Pack("16CardPack.txt"), 2));
    }
    public void getPackTest(){
        assertTrue(testCardGame.getPack() instanceof Pack);
    }
}

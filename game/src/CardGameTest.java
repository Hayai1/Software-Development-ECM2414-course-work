
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class CardGameTest {

    @Test
    public void createDecksTest() throws FileNotFoundException{
        Deck[] testdecks = CardGame.createDecks(4);
        assertTrue(testdecks instanceof Deck[]);
        assertTrue(testdecks.length == 4);

    }
    @Test
    public void writeDecksToFilesTest(){
        Deck[] testdecks = new Deck[]{ new Deck(0,4),new Deck(1,4) };
        testdecks[0].addCard(new Card(1));
        testdecks[0].addCard(new Card(2));
        testdecks[0].addCard(new Card(3));
        testdecks[0].addCard(new Card(4));
        
        CardGame.writeDecksToFiles("resources\\testFiles\\", testdecks);
        try (BufferedReader reader = new BufferedReader(new FileReader("resources\\testFiles\\deck0_output.txt"))) {
            String line = reader.readLine();
            assertTrue(line.toString().equals("deck0 contents: 1 2 3 4"));
        }
        catch (Exception e){}
        CardGame.writeDecksToFiles("resources\\testFiles\\",testdecks);
        try (BufferedReader reader = new BufferedReader(new FileReader("resources\\testFiles\\deck0_output.txt"))) {
            String line = reader.readLine();
            assertTrue(line.toString().equals("deck0 contents: 1 2 3 4")); 
        }
        catch (Exception e){}
    }

    @Test
    public void testGetPlayerInput(){
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("4".getBytes());
        System.setIn(in);
        assert(CardGame.getPlayerInput() == 4);
        System.setIn(sysInBackup);
    }

    @Test
    public void testCreatePack() throws IOException{
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("resources\\32CardPack.txt".getBytes());
        System.setIn(in);
   
        CardGame.createPack(4);
        
        
        System.setIn(sysInBackup);
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
    @Test
    public void dealCardsToPlayersTest() throws IOException{
        InputStream sysInBackup = System.in; // backup System.in to restore it later
        ByteArrayInputStream in = new ByteArrayInputStream("resources\\32CardPack.txt".getBytes());
        System.setIn(in);
        CardGame.createPack(4);
        CardGame.createPlayers(4, new Deck[]{new Deck(0,4),new Deck(1,4),new Deck(2,4),new Deck(3,4)});
        CardGame.dealCardsToPlayers(4);
        System.setIn(sysInBackup);
    }
    @Test
    public void dealCardsToDeckTest(){
        try {
            CardGame.dealCardsToDecks(4,new Deck[]{new Deck(0,4),new Deck(1,4),new Deck(2,4),new Deck(3,4)}, new Pack("resources\\32CardPack.txt"));

        } catch (Exception e) {
            System.out.println("skipped test");
        }
    }
}

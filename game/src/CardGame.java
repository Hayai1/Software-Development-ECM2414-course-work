import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class CardGame {
    private static Player[] players;
    private static Deck[] decks;
    private static Pack pack;
    private static StringBuffer playerWin; 
    private static Scanner scanner = new Scanner(System.in);
    private static Thread[] threads;

    public static void main(String[] args) throws InterruptedException {
      playerWin = new StringBuffer("player ");
      int numberOfPlayers = getPlayerInput();
      threads = new Thread[numberOfPlayers];
      
      decks = createDecks(numberOfPlayers);
      System.out.println("decks made");
      players = createPlayers(numberOfPlayers, decks);
      System.out.println("players made");
      pack = createPack(players.length);
      System.out.println("pack made");
      int count = pack.getPack().size();
      dealCardsToPlayers(count);
      dealCardsToDecks(count);
      scanner.close();
      
      for (int i = 0; i< players.length; i++) {
        Thread playerThread = new Thread(players[i]);
        playerThread.start();
        threads[i] = playerThread;
      }
      for (Thread player : threads){
        player.join();
      }
      writeDecksToFiles();
    }

    public static void writeDecksToFiles(){
      for (Deck deck : decks){
        try {
            File myObj = new File("resources\\deck" + deck.ID + "_output.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } 
            else {
                //System.out.println("File already exists.");
                FileWriter fileWriter = new FileWriter("resources\\deck" + deck.ID + "_output.txt");
                fileWriter.write("");
            }
        try (FileOutputStream fos = new FileOutputStream("resources\\deck" + deck.ID + "_output.txt", true)) {
            String text = "deck" + deck.ID + " contents: " + deck.getCard().getValue() + " " + deck.getCard().getValue() + " " + deck.getCard().getValue() + " " + deck.getCard().getValue();  
            fos.write(text.getBytes(StandardCharsets.UTF_8));
            
        }
        catch (Exception e) {
            System.out.println(e);
        }
            
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
        }
      }
  }


    public static int getPlayerInput(){
      System.out.println("Please enter the number of players:");
      boolean validNumberOfPlayers = false;
      int numberOfPlayers = 0;
      while (!validNumberOfPlayers){
        try{
            numberOfPlayers = Integer.parseInt(scanner.nextLine());
        }
        catch (Exception e){
          System.out.println("Please enter an integer!");
          continue;
        }
          if (!(numberOfPlayers > 1)){
            continue;
          }
          else{
            validNumberOfPlayers = true;
          }
      }
      return numberOfPlayers;
    }
    
    public static Player[] createPlayers(int numberOfPlayers, Deck[] decks){
      players = new Player[numberOfPlayers];
      CyclicBarrier barrier = new CyclicBarrier(numberOfPlayers);
        
      for (int i = 0; i < numberOfPlayers; i++){
        if(i == 0){
          players[i] = new Player(i + 1, decks[i], decks[decks.length-1],playerWin,barrier);
        }
        else{
          players[i] = new Player(i + 1, decks[i], decks[i-1],playerWin,barrier);
        }
      }
      return players;
    }

    public static Deck[] createDecks(int numberOfdecks){
      // like createPlayer
      Deck[] decks = new Deck[numberOfdecks];
      for (int i=0; i < numberOfdecks; i++){
        decks[i] = new Deck(i + 1, 5);
      }
      return decks;
    }

    public static Boolean validPack(int numberOfPlayers, Pack pack){
      Boolean valid = false;
      if (pack.getPack().capacity() == numberOfPlayers*8){
        valid = true;
      }
      return valid;
    }

    public static Pack createPack(int numberOfPlayers){
      // at least part of this needs to be separated out into a method to check if a pack is valid
      // and then the other part needs to create the pack
      System.out.println("Please enter location of pack to load");
      boolean validPath = false;
      String packPath = "";
      while (!validPath){
        packPath = scanner.nextLine();
        try{
          pack = new Pack(packPath);
          if (validPack(numberOfPlayers, pack)){
            System.out.println("pack should contain 8n cards where n is the number of players");
            continue;
          }
          validPath = true;
        }
        catch(FileNotFoundException notFound){
          System.out.println("Please enter a valid file!");
          continue;
        }
      }
      return pack;
    }

  

    public static void dealCardsToPlayers(int count){
      
      for (int i=0; i < 4; i++){
        for (Player player : players) {
          player.addCard(pack.getCard());
        } 
      }
    }

    
    public static void dealCardsToDecks(int count){
      for (int i=0; i < 4; i++){
        for (Deck deck : decks) {
          deck.addCard(pack.getCard());
        }
      }
    }

    public static Pack getPack(){
      return pack;
    }

    public static Player[] getPlayers(){
      return players;
    }
}

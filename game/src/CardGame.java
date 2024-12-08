import java.io.FileNotFoundException;
import java.util.Scanner;

public class CardGame {
    private static Player[] players;
    private static Deck[] decks;
    private static Pack pack;
    private static StringBuffer playerWin; 
    private static Scanner scanner = new Scanner(System.in);
    private static Thread[] threads;

    public static void main(String[] args) {
      playerWin = new StringBuffer("null");
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
        threads[i] = playerThread;
        playerThread.start();
        
      }
      try {
        threads[0].join();
      }
      catch (InterruptedException e){
        System.out.println("nice");
      }
      for (Thread thread : threads){
        try{
          thread.join();
          System.out.println("ඞ3");
        }
        catch (InterruptedException e){
          System.out.println("ඞ");
        }
      }
      System.out.println("end of game");
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
      String[] finishedCheckingForWin = new String[numberOfPlayers];
      for (int i = 0; i < numberOfPlayers; i++){
        if(i == 0){
          players[i] = new Player(i, decks[i], decks[decks.length-1],playerWin,finishedCheckingForWin);
        }
        else{
          players[i] = new Player(i, decks[i], decks[i-1],playerWin,finishedCheckingForWin);
        }
      }
      return players;
    }

    public static Deck[] createDecks(int numberOfdecks){
      // like createPlayer
      Deck[] decks = new Deck[numberOfdecks];
      for (int i=0; i < numberOfdecks; i++){
        decks[i] = new Deck(i, 4);
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
      
      for (int i=0; i < count/8; i++){
        for (Player player : players) {
          player.addCard(pack.getCard());
        } 
      }
    }

    
    public static void dealCardsToDecks(int count){
      for (int i=0; i < count/8; i++){
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

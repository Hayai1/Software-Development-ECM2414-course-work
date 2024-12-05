import java.io.FileNotFoundException;
import java.util.Scanner;

public class CardGame {
    private static Player[] players;
    private static Deck[] decks;
    private static Pack pack;
    

    public static void main(String[] args) {
      //need a list of players
      //need a list of decks
      //need a pack
      //need to ask user for number of players

      players = createPlayers();
      decks = createDecks(players.length);
      pack = createPack(players.length);
      
      int count = pack.getPack().capacity();
      dealCardsToPlayers(count);
      dealCardsToDecks(count);
      
      //deals out players
      

      //deals out decks
      
      //run all the players
      //run all the decks
      
      //need to ask user for location of pack and repeat request until a valid pack is chosen
      //need to create pack 
      //need to deal cards out <-
      //need to run game
      //need to know when to exit?
    }
    public static Player[] createPlayers(){
      Scanner scanner = new Scanner(System.in);
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
      players = new Player[numberOfPlayers];
      for (int i = 0; i < numberOfPlayers; i++){
        players[i] = new Player(i);
      }
      scanner.close();
      return players;
    }

    public static Deck[] createDecks(int numberOfdecks){
      Deck[] decks = new Deck[numberOfdecks];
      for (int i=0; i > numberOfdecks; i++){
        decks[i] = new Deck(i, 4);
      }
      return decks;
    }
    
    public static Pack createPack(int numberOfPlayers){
      Scanner scanner = new Scanner(System.in);
      System.out.println("Please enter location of pack to load");
      boolean validPath = false;
      while (!validPath){
        String packPath = scanner.nextLine();
        try{
          pack = new Pack(packPath);
          if (pack.getPack().capacity() != numberOfPlayers*8){
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
      scanner.close();
      return pack;
    }

  

    public static void dealCardsToPlayers(int count){
      
      for (int i=0; i < count/4; i++){
        for (Player player : players) {
          player.addCard(pack.getCard());
        } 
      }
    }

    
    public static void dealCardsToDecks(int count){
      for (int i=0; i < count/4; i++){
        for (Deck deck : decks) {
          deck.addCard(pack.getCard());
        }
      }
    }
}

import java.io.File;
import java.io.FileOutputStream;  // Import the File class
import java.io.FileWriter;  // Import the IOException class to handle errors
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Player implements Runnable{ 
    private int ID;
    Deck LDeck;
    Deck RDeck;

    public Deck LDeckBack;
    public Deck RDeckBack;
    public Card[] handBack = new Card[4];
    public final CyclicBarrier barrier;
    int turn;
    StringBuffer playerWin;
    private Card[] hand = new Card[4];
    
    public Player(int ID, Deck LDeck, Deck RDeck, StringBuffer playerWin, CyclicBarrier barrier) { 
        this.ID = ID; 
        this.LDeck = LDeck;
        this.RDeck = RDeck;
        this.playerWin = playerWin;
        this.LDeckBack = LDeck;
        this.RDeckBack = RDeck;
        this.handBack = hand;
        this.barrier = barrier;
    }

    public void addCard(Card card) {
        // adds a card to the first empty position in the players hand
        // maybe hand could be an arraylist instead of an array?
        // depends if this code breaks or not
        int i = 0;
        for(Card count : hand){
            if(count == null){
                hand[i] = card;
                break;
            }
            i++;
        }
    }

    public Card ChooseCardToRemove(){
        // need some actual logic in here
        for (Card card : hand) {
            if (card.getValue() != this.ID){
                // remove the card from the hand
                return card;
            }
        }
        return hand[0];
    }

    public Card[] getHand(){
        return this.hand;
    }

    public int getID(){
        return this.ID;
    }

    public Boolean CheckWin(){
        if(hand[0].getValue() == hand[1].getValue() && hand[1].getValue() == hand[2].getValue() && hand[2].getValue() == hand[3].getValue()){
            return true;
        }
        else{
            return false;
        }
    }

    public Card DrawNewCard(){
        return LDeck.getCard();
    }

    public void Discard(Card card){
        int pos = java.util.Arrays.asList(hand).indexOf(card);
        hand[pos] = null;
        RDeck.addCard(card);
    }

    public void CreateFile() {
        // should clear the current files if they exist
        try {
            File myObj = new File("resources\\player" + ID + ".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } 
            else {
                //System.out.println("File already exists.");
                FileWriter fileWriter = new FileWriter("resources\\player" + ID + ".txt");
                fileWriter.write("");

            }
        } 
        catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

    public void writeToFile(String text){
    
        try (FileOutputStream fos = new FileOutputStream("resources\\player" + ID + ".txt", true)) {
            fos.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
            fos.write(text.getBytes(StandardCharsets.UTF_8));
            
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public synchronized Card addAndGet(Card cardToRemove){
        Discard(cardToRemove);
        Card newCard = DrawNewCard();
        addCard(newCard);
        return newCard;
    }
  

    @Override
    public void run() {
        
        String handAsString = hand[0].getValue() + " " + hand[1].getValue() + " " + hand[2].getValue() + " " + hand[3].getValue();
        boolean won = false;
        Card newCard;
        Card cardToRemove;

        CreateFile();

        writeToFile("Player " + ID + " initial hand: " + handAsString);
        while (!won) {
            turn++;

            //a game turn (picks up a card and deposits a card)
            cardToRemove = ChooseCardToRemove();
            newCard = addAndGet(cardToRemove);
            writeToFile("Player " + ID + " draws a " + newCard.getValue() + " from deck " + LDeck.getID());
            writeToFile("Player " + ID + " discards a " + cardToRemove.getValue() + " to deck " + RDeck.getID());
            won = CheckWin();
            //turn is finished so wait until all threads finished there turn
            if (won){ playerWin.append(ID); }
            try { barrier.await(); } 
            catch (InterruptedException | BrokenBarrierException e) {}
            //--------------------------------------------------->
            if (!playerWin.toString().equals("player ")){
                handAsString = hand[0].getValue() + " " + hand[1].getValue() + " " + hand[2].getValue() + " " + hand[3].getValue();
                if (playerWin.toString().equals("player " + ID)){
                    writeToFile("Player " + ID + " wins!");
                    writeToFile("Player " + ID + " exits");
                    writeToFile("player " + ID + " hand: " + handAsString);
                }
                else{
                    writeToFile(playerWin.toString() + " has informed player " + ID + " that " + playerWin.toString() + " has won");
                    writeToFile("Player " + ID + " exits");
                    writeToFile("player " + ID + " hand: " + handAsString);
                }
                break;
            }
        }
    }
}
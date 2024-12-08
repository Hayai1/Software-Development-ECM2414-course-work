import java.io.File;
import java.io.FileOutputStream;  // Import the File class
import java.io.FileWriter;  // Import the IOException class to handle errors
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
public class Player implements Runnable{ 
    private int ID;
    Deck LDeck;
    Deck RDeck;
    StringBuffer playerWin;
    String[] finishedCheckingForWin;
    boolean[] flag; 
    private Card[] hand = new Card[4];
    
    public Player(int ID, Deck LDeck, Deck RDeck, StringBuffer playerWin, String[] finishedCheckingForWin, boolean[] flag) { 
        this.ID = ID; 
        this.LDeck = LDeck;
        this.RDeck = RDeck;
        this.playerWin = playerWin;
        this.finishedCheckingForWin = finishedCheckingForWin;
        this.flag = flag;
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
            e.printStackTrace();
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
       
    public synchronized void finishedCheckingForWin(){
        finishedCheckingForWin[ID] = Integer.toString(ID);
    }

    public Boolean allCheckedWin(){
        boolean flag = true;
        for (String ID : finishedCheckingForWin) {
            if(ID == null){
            flag = false;
            break;
            }
            else{
                for (String string : finishedCheckingForWin) {
                    string = null;
                }
            }
        }
        return flag;
    }
    

    public synchronized void BroadCast(){
        this.playerWin.append("player " + ID);
    }

    
    
    @Override
    public void run() {
        CreateFile();
        Card cardToRemove;
        Card newCard;
        boolean won = false;
    
        // Log the initial hand
        writeToFile("Player " + ID + " initial hand: " 
                    + hand[0].getValue() + " " + hand[1].getValue() + " "
                    + hand[2].getValue() + " " + hand[3].getValue());
    
        while (!won) {
            // Draw a new card and discard one
            synchronized (LDeck) {
                newCard = DrawNewCard();
            }
            writeToFile("Player " + ID + " draws a " + newCard.getValue() 
                        + " from deck " + LDeck.getID());
    
            cardToRemove = ChooseCardToRemove();
            synchronized (RDeck) {
                Discard(cardToRemove);
            }
            writeToFile("Player " + ID + " discards a " + cardToRemove.getValue() 
                        + " to deck " + RDeck.getID());
            addCard(newCard);
    
            // Check for a win
            won = CheckWin();
            if (won) {
                synchronized (flag) {
                    // Append the winner and set the flag
                    playerWin.append("Player " + ID);
                    flag[0] = true; // Signal all threads that the game is over
                    flag.notifyAll(); // Wake all threads
                }
                writeToFile("Player " + ID + " wins!");
                writeToFile("Player " + ID + " exits");
                writeToFile("Player " + ID + " final hand: " 
                            + hand[0].getValue() + " " + hand[1].getValue() + " "
                            + hand[2].getValue() + " " + hand[3].getValue());
                System.out.println("Player " + ID + " has exited");
                break;
            } else {
                synchronized (flag) {
                    // Mark that this player has checked their win status
                    finishedCheckingForWin[ID] = Integer.toString(ID);
    
                    // If all players have reached the hold point, proceed
                    if (allPlayersReachedHoldPoint()) {
                        flag[0] = false; // Reset flag for the next round
                        flag.notifyAll(); // Wake all threads
                    } else {
                        // Wait until all players have reached the hold point or the game ends
                        while (!flag[0] && playerWin.length() == 0) {
                            try {
                                flag.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }
            }
    
            // If another player has won, exit
            if (!playerWin.toString().equals("null")) {
                writeToFile("Player " + ID + " notices the game is finished. Winner: " + playerWin.toString());
                System.out.println("Player " + ID + " has exited");
                break;
            }
        }
    
        // Ensure all threads log game completion
        if (!won && !playerWin.toString().equals("null")) {
            writeToFile("Player " + ID + " exits because the game is finished. Winner: " + playerWin.toString());
        }
    }
    
    /**
     * Helper method to check if all players have reached the hold point.
     */
    private boolean allPlayersReachedHoldPoint() {
        for (String status : finishedCheckingForWin) {
            if (status == null) {
                return false;
            }
        }
        // Reset the array for the next round
        Arrays.fill(finishedCheckingForWin, null);
        return true;
    }
}
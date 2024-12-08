import javax.naming.ldap.Rdn;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.nio.charset.StandardCharsets;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.time.LocalTime;
public class Player implements Runnable{ 
    private int ID;
    Deck LDeck;
    Deck RDeck;
    StringBuffer playerWin;
    String[] finishedCheckingForWin;
    private Card[] hand = new Card[4];
    
    public Player(int ID, Deck LDeck, Deck RDeck, StringBuffer playerWin,String[] finishedCheckingForWin) { 
        this.ID = ID; 
        this.LDeck = LDeck;
        this.RDeck = RDeck;
        this.playerWin = playerWin;
        this.finishedCheckingForWin = finishedCheckingForWin;
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
                System.out.println("File already exists.");
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
    public synchronized void run(){
        CreateFile();
        Card cardToRemove;
        Card newCard;
        Boolean won = false;
        writeToFile("player " + ID + " inital hand is " + hand[0].getValue() + " " + hand[1].getValue() + " " + hand[2].getValue() + " " + hand[3].getValue());
        while(CheckWin() == false){
            newCard = DrawNewCard();
            writeToFile("player " + ID + " draws a " + newCard.getValue() + " from deck " + RDeck.getID());
            cardToRemove = ChooseCardToRemove();
            writeToFile("player " + ID + " discards a " + cardToRemove.getValue() + " to deck " + LDeck.getID());
            // threads are getting fucked trying to remove from deck
            Discard(cardToRemove);
            addCard(newCard);
            won = CheckWin();
            finishedCheckingForWin[ID] = null;
            if (won){
                BroadCast();
                writeToFile("player " + ID + " wins");
                writeToFile("player " + ID + " exits");
                writeToFile("player " + ID + " final hand " + hand[0].getValue() + " " + hand[1].getValue() + " " + hand[2].getValue() + " " + hand[3].getValue());
                finishedCheckingForWin();
                System.out.println("player + " + ID + "has exited");
                finishedCheckingForWin[ID] = null;
                break;
                }
            else{
                finishedCheckingForWin();
                if (allCheckedWin() == false){
                    try {
                        wait(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }                
                }
                else{
                    notifyAll();
                }
            }
            // this if statement is busted but if it wasn't this would work :D
            //LocalTime myObj = LocalTime.now();
            System.out.println(ID);
            if(!playerWin.toString().equals("null")){
                System.out.println("player + " + ID + "has exited");
                finishedCheckingForWin[ID] = null;
                break;
            }
        }    
    }
}
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Stack;

public class Pack 
{
    
    private Stack<Card> Pack = new Stack<Card>();

    public Pack(String Path) throws FileNotFoundException{
        try {
            createCards(Path);
        } catch (FileNotFoundException notFound) {
            throw new FileNotFoundException();
        }
    }

    public void createCards(String Path) throws FileNotFoundException{
        int cardInt;
        // insert a filereader here i guess
        // read file, try to make cards
        // if card can't be made throw exception
        try {
            File cardFile = new File(Path);
            Scanner cardReader = new Scanner(cardFile);
            while (cardReader.hasNextInt()) {
                cardInt = cardReader.nextInt();
                Pack.add(new Card(cardInt));
            }
            cardReader.close();
        } catch (FileNotFoundException notFound) {
            throw new FileNotFoundException("File was unfound");
        }
    }

    public Stack<Card> getPack(){
        return Pack;
    }

    public Card getCard(){
        return Pack.pop();
    }
}

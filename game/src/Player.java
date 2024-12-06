import javax.naming.ldap.Rdn;

public class Player implements Runnable{ 
    private int ID;
    Deck LDeck;
    Deck RDeck;
    private Card[] hand = new Card[4];
    public Player(int ID, Deck LDeck, Deck RDeck) { 
        this.ID = ID; 
        this.LDeck = LDeck;
        this.RDeck = RDeck;
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
            i ++;
        }
    }

    public Card ChooseCardToRemove(){
        // need some actual logic in here
        Card myCard = new Card(1);
        return myCard;
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

    public void DrawNewCard(){
        // need to make sure Decks get locked before this fucks things up
        addCard(LDeck.getCard());
    }

    public void Discard(){
        // also need to make sure this gets locked
        RDeck.addCard(ChooseCardToRemove());
    }

    @Override
    public void run(){
        while(CheckWin() == false){
            DrawNewCard();
        if(CheckWin() == true){
            // broadcast win to all other players
            // exit game
            BroadCast();
        }
        }
    }
    }
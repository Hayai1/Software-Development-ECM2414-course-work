
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import junit.*;

public class CardTest {
    @Test
    public void CheckingValueSetCardTest(){
        Card myCard = new Card(12);
        assertEquals(12, myCard.GetValue());
    }

    @Test
    public void CreateCard(){
        Card myCard = new Card(1);
    }
    
    @Test
    public void InvalidValueCard(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Card myCard = new Card(-1);
        });
    }
}

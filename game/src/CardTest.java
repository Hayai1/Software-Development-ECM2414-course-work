
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import junit.*;

public class CardTest {
    @Test
    public void CreateCardTest(){
        Card myCard = new Card(12);
        assertEquals(12, myCard.GetValue());
    }
}

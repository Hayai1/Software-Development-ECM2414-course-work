
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Test;
import junit.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PackTest {
    @Test
    public void TestCreateCards(){
        ArrayList<Card> TestCards = new ArrayList<Card>();
        String TestPath = "TestPath";
        Pack TestPack = new Pack(TestPath);
        TestPack.CreateCards(TestPath);
        assertEquals(TestCards, TestPack.GetPack());
    }

    @Test
    public void TestCreatePack(){
        String TestPath = "TestPath";
        Pack TestPack = new Pack(TestPath);
    }

    @Test
    public void TestGetPack(){
        ArrayList<Card> TestCards = new ArrayList<Card>();
        String TestPath = "TestPath";
        Pack TestPack = new Pack(TestPath);
        TestPack.CreateCards(TestPath);
        assertEquals(TestCards, TestPack.GetPack());
    }

    @Test
    public void TestInvalidPath(){
        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            Pack invalidPack = new Pack("not a filename");
        });
    }
}

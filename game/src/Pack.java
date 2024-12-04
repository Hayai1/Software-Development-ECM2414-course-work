import java.util.List;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Pack {
    
    private List<Card> Pack = new ArrayList<Card>();

    public Pack(String Path){
        try {
            CreateCards(Path);
        } catch (FileNotFoundException notFound) {
            throw new FileNotFoundException();
        }
    }

    public void CreateCards(String Path){
        // insert a filereader here i guess
    }

    public List<Card> GetPack(){
        return Pack;
    }
}

public class Card {
    int value;
    public Card(int FaceValue){
            if(FaceValue > -1){
                this.value = FaceValue;
            }
            else{
                throw new IllegalArgumentException("Value must be nonnegative");
            }
        
    }
    public int GetValue(){
        return this.value;
    }
}

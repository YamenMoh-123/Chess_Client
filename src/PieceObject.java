import java.awt.*;
import java.util.ArrayList;

public class PieceObject  {
    int value;
    String name;
    Color color;

    public PieceObject(int value, String name, Color color){
        this.value = value;
        this.name = name;
        this.color = color;
    }

    public ArrayList<String> validMoves(String startingPos){
        return new ArrayList<String>();
    }

}

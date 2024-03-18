import java.awt.*;
import java.util.ArrayList;

public class RookObject extends PieceObject{
    // keep mutual
    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};

    public RookObject(int value, String name, Color color) {
        super(value, name, color);
    }

    @Override
    public ArrayList<String> validMoves(String startingPos){
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;
        for(int i = 0; i < 8; i++){
            if(i != x){
                validMoves.add(colNames[i] + " " + (y+1));
            }
            if(i != y){
                validMoves.add(colNames[x] + " " + (i+1));
            }
        }
        return validMoves;
    }


}

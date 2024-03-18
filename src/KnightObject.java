import java.awt.*;
import java.util.ArrayList;

public class KnightObject extends PieceObject{
    public KnightObject(int value, String name, Color color, int x, int y) {
        super(value, name, color,   x, y);
    }
    // keep mutual
    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};

    @Override
    public ArrayList<String> validMoves(String startingPos){
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;
        int[] xMoves = {x+2, x+2, x-2, x-2, x+1, x+1, x-1, x-1};
        int[] yMoves = {y+1, y-1, y+1, y-1, y+2, y-2, y+2, y-2};
        for(int i = 0; i < 8; i++){
            if(xMoves[i] >= 0 && xMoves[i] < 8 && yMoves[i] >= 0 && yMoves[i] < 8){
                validMoves.add(colNames[xMoves[i]] + " " + (yMoves[i]+1));
            }
        }
        return validMoves;
    }

}

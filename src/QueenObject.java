import java.awt.*;
import java.util.ArrayList;

public class QueenObject extends PieceObject{
    public QueenObject(int value, String name, Color color) {
        super(value, name, color);
    }

    @Override
    public ArrayList<String> validMoves(String startingPos){
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;
        int[] xMoves = {x+1, x+1, x-1, x-1, x+1, x-1, x, x};
        int[] yMoves = {y+1, y-1, y+1, y-1, y, y, y+1, y-1};
        for(int i = 0; i < 8; i++){
            int xTemp = xMoves[i];
            int yTemp = yMoves[i];
            while(xTemp >= 0 && xTemp < 8 && yTemp >= 0 && yTemp < 8){
                validMoves.add((char)(xTemp+97) + " " + (yTemp+1));
                xTemp = xTemp + xMoves[i];
                yTemp = yTemp + yMoves[i];
            }
        }
        return validMoves;
    }
}

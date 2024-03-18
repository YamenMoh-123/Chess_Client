import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PieceObject {
    int value;
    String name;
    Color color;
    int x, y;
    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};
    static BufferedImage[] PieceSprite = Resources.pieceSheet.getImagesFrom(0, 5);  // make white and black to switch? or 2

    public PieceObject(int value, String name, Color color, int x, int y) {
        this.name = name;
        this.color = color;
        this.x = x;
        this.y = y;
    }


    public void tick() {
    }



    public ArrayList<String> validMoves(String startingPos) {
        return new ArrayList<String>();
    }


    public ArrayList<String> moveBishop(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;
        int[] xMoves = {x + 1, x + 1, x - 1, x - 1};
        int[] yMoves = {y + 1, y - 1, y + 1, y - 1};
        for (int i = 0; i < 4; i++) {
            int xTemp = xMoves[i];
            int yTemp = yMoves[i];
            while (xTemp >= 0 && xTemp < 8 && yTemp >= 0 && yTemp < 8) {
                validMoves.add((char) (xTemp + 97) + " " + (yTemp + 1));
                xTemp = xTemp + xMoves[i];
                yTemp = yTemp + yMoves[i];
            }
        }
        return validMoves;
    }
    public ArrayList<String> moveRook(String startingPos){
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


    public ArrayList<String> moveQueen(String startingPos){
        ArrayList<String> validMoves = new ArrayList<String>();
        validMoves.addAll(moveRook(startingPos));
        validMoves.addAll(moveBishop(startingPos));
        return validMoves;
    }

    /*
    public ArrayList<String> movePawn(String startingPos){
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;
        if(this.color == Color.WHITE){
            if(y == 1){
                validMoves.add((char)(x+97) + " " + (y+2));
            }
            if(y+1 < 8){
                validMoves.add((char)(x+97) + " " + (y+2));
            }
        }else{
            if(y == 6){
                validMoves.add((char)(x+97) + " " + (y-2));
            }
            if(y-1 >= 0){
                validMoves.add((char)(x+97) + " " + (y-2));
            }
        }
        return validMoves;
    }

     */

    public ArrayList<String> moveKnight(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;
        int[] xMoves = {x + 2, x + 2, x - 2, x - 2, x + 1, x + 1, x - 1, x - 1};
        int[] yMoves = {y + 1, y - 1, y + 1, y - 1, y + 2, y - 2, y + 2, y - 2};
        for (int i = 0; i < 8; i++) {
            if (xMoves[i] >= 0 && xMoves[i] < 8 && yMoves[i] >= 0 && yMoves[i] < 8) {
                validMoves.add((char) (xMoves[i] + 97) + " " + (yMoves[i] + 1));
            }
        }
        return validMoves;
    }


    public ArrayList<String> moveKing(String startingPos){
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;
        int[] xMoves = {x+1, x+1, x-1, x-1, x+1, x-1, x, x};
        int[] yMoves = {y+1, y-1, y+1, y-1, y, y, y+1, y-1};
        for(int i = 0; i < 8; i++){
            if(xMoves[i] >= 0 && xMoves[i] < 8 && yMoves[i] >= 0 && yMoves[i] < 8){
                validMoves.add((char)(xMoves[i]+97) + " " + (yMoves[i]+1));
            }
        }
        return validMoves;
    }

    public void render(Graphics2D g2d) {
        switch (this.name){
            case "King":
                g2d.drawImage(PieceSprite[0], this.x, this.y, 100, 100, null);
                break;
            case "Queen":
                g2d.drawImage(PieceSprite[1], this.x, this.y, 100, 100, null);
                break;
            case "Rook":
                g2d.drawImage(PieceSprite[2], this.x, this.y, 100, 100, null);
                break;
            case "Bishop":
                g2d.drawImage(PieceSprite[3], this.x, this.y, 100, 100, null);
                break;
            case "Knight":
                g2d.drawImage(PieceSprite[4], this.x, this.y, 100, 100, null);
                break;
            case "Pawn":
                g2d.drawImage(PieceSprite[5], this.x, this.y, 100, 100, null);
                break;
        }

    }


}
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

    public PieceObject(String name, Color color, int x, int y) {
        this.name = name;
        this.color = color;
        this.x = x+5;
        this.y = y-10;
    }


    public void tick() {
    }



    public ArrayList<String> validMoves(String startingPos, String type) {
        switch (type) {
            case "Bishop":
                return moveBishop(startingPos);
            case "Rook":
                return moveRook(startingPos);
            case "Queen":
                return moveQueen(startingPos);
            case "Knight":
                return moveKnight(startingPos);
            case "King":
                return moveKing(startingPos);
            case "Pawn":
                return movePawn(startingPos);
        }
        return null;
    }


    public ArrayList<String> moveBishop(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;

        for(int i =1; i < 8; i ++){
           // if(ChessBoard.chessBoard[x-i][y+i].getPiece().color != this.color){  // if there is an enemy piece in the way
              //  validMoves.add(colNames[x-i] + " " + (y+i+1));
              //  break;
           // }
           // if(ChessBoard.chessBoard[x+i][y+i].getPiece() != null){  // if there is a piece in the way
            //    break;
            //}
            if(x+i < 8 && y+i < 8){
                validMoves.add(colNames[x+i] + " " + (y+i+1));
            }
        }
        for(int i =1; i < 8; i ++){

            if(x+i < 8 && y-i >= 0){
                validMoves.add(colNames[x+i] + " " + (y-i+1));
            }
        }

        for(int i =1; i < 8; i ++){
            if(x-i >= 0 && y+i < 8){
                validMoves.add(colNames[x-i] + " " + (y+i+1));
            }
        }

        for(int i =1; i < 8; i ++){
            if(x-i >= 0 && y-i >= 0){
                validMoves.add(colNames[x-i] + " " + (y-i+1));
            }
        }

        return validMoves;
    }

    public ArrayList<String> moveRook(String startingPos){
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = (startingPos.charAt(2) - 48);

        System.out.println("Location: " + x + " " + y);

        //right

        for(int i = 1; i < 8; i++){
            if (x + i < 8) {
                validMoves.add(colNames[x+i] + " " + y);
                if(ChessBoard.chessBoard[y][x+i].getPiece() != null){
                    break;
                }
            }
        }

        // left
        for(int i = 1; i < 8; i++){
            if(x-i >= 0){
                validMoves.add(colNames[x-i] + " " + y);
                if(ChessBoard.chessBoard[y][x-i].getPiece() != null) {
                    break;
                }
            }
        }


        // up
        for(int i = 1; i < 8; i++){
            if(y+i <= 8){
                //  validMoves.add(colNames[x] + " " + (y+i));
            }
        }

        // down
        for(int i = 1; i < 8; i++){
            if(y-i > 0){
                // validMoves.add(colNames[x] + " " + (y-i));
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
                g2d.drawImage(PieceSprite[4], this.x, this.y, 100, 100, null);
                break;
            case "Bishop":
                g2d.drawImage(PieceSprite[2], this.x, this.y, 100, 100, null);
                break;
            case "Knight":
                g2d.drawImage(PieceSprite[3], this.x, this.y, 100, 100, null);
                break;
            case "Pawn":
                g2d.drawImage(PieceSprite[5], this.x, this.y, 100, 100, null);
                break;
        }

    }


}
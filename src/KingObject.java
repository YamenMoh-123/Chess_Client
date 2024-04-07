import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class KingObject extends PieceObject {
    private int boardX, boardY;

    static BufferedImage[] PieceSprite = Resources.pieceSheet.getImagesFrom(0, 0);
    static BufferedImage[] PieceSpriteBlack = Resources.pieceSheet.getImagesFrom(6, 6);

    public KingObject(int x, int y, int boardX, int boardY, Color color){
        super("King", color, x, y, false);
        this.boardX = boardX;
        this.boardY = boardY;
    }

    @Override
    public int[] getPos(){
        return new int[]{boardX, boardY};
    }

    @Override
    public ArrayList<String> validMoves(String startingPos, String type) {
        System.out.println(startingPos + " " + type);
        if ("King".equals(type)) {
            return moveKing(startingPos);
        }
        return super.validMoves(startingPos, type);
    }


    @Override
    public ArrayList<String> moveKing(String startingPos){
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;
        int[] xMoves = {x+1, x+1, x-1, x-1, x+1, x-1, x, x};
        int[] yMoves = {y+1, y-1, y+1, y-1, y, y, y+1, y-1};
        int tempX = boardX;
        int tempY = boardY;
        PieceObject temp = ChessBoard.blackKing;
        ChessBoard.chessBoard[boardX][boardY].setPiece(null);

        for(int i = 0; i < 8; i++){
            if(xMoves[i] >= 0 && xMoves[i] < 8 && yMoves[i] >= 0 && yMoves[i] < 8){
                boardX = 7-yMoves[i];
                boardY = xMoves[i];

               // System.out.println("TEMP: " + boardX + " " + boardY);
               // ChessBoard.chessBoard[boardX][boardY].setBackground(Color.YELLOW);
                if(!isKingChecked()){
                    validMoves.add((char) (xMoves[i] + 97) + " " + (yMoves[i] + 1));
                }
            }
        }
        boardX = tempX;
        boardY = tempY;
        ChessBoard.chessBoard[boardX][boardY].setPiece(temp);
        return validMoves;
    }


    // using boardx and y
    @Override
    public boolean isKingChecked() {
       // System.out.println("Checking Check: " + boardX + " " + boardY);

        // checks pieces to the bottom of the king until a piece is encountered, if its a rook or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardX + i < 8 && ChessBoard.chessBoard[boardX + i][boardY].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX + i][boardY].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX + i][boardY].getPiece().name.equals("Rook") || ChessBoard.chessBoard[boardX + i][boardY].getPiece().name.equals("Queen")) {
                       System.out.println("Found  "  + ChessBoard.chessBoard[boardX+i][boardY].getPiece().name + (boardX + i) + " " + boardY);
                        return true;
                    }
                    break;
                }
                else{
                    break;
                }
            }
        }
        // checks pieces to the top of the king until a piece is encountered, if its a rook or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardX - i >= 0 && ChessBoard.chessBoard[boardX - i][boardY].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX - i][boardY].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX - i][boardY].getPiece().name.equals("Rook") || ChessBoard.chessBoard[boardX - i][boardY].getPiece().name.equals("Queen")) {
                        System.out.println("Found  "  + ChessBoard.chessBoard[boardX-i][boardY].getPiece().name + (boardX - i) + " " + boardY);
                        return true;
                    }
                    break;
                }
                else{
                    break;
                }
            }
        }
        // checks pieces to the right of the king until a piece is encountered, if its a rook or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardY + i < 8 && ChessBoard.chessBoard[boardX][boardY + i].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX][boardY + i].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX][boardY + i].getPiece().name.equals("Rook") || ChessBoard.chessBoard[boardX][boardY + i].getPiece().name.equals("Queen")) {
                        System.out.println("Found  "  + ChessBoard.chessBoard[boardX][boardY+i].getPiece().name + boardX + " " + (boardY + i));
                        return true;
                    }
                    break;
                }
                else{
                    break;
                }
            }
        }
        // checks pieces to the left of the king until a piece is encountered, if its a rook or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardY - i >= 0 && ChessBoard.chessBoard[boardX][boardY - i].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX][boardY - i].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX][boardY - i].getPiece().name.equals("Rook") || ChessBoard.chessBoard[boardX][boardY - i].getPiece().name.equals("Queen")) {
                        System.out.println("Found  "  + ChessBoard.chessBoard[boardX][boardY-i].getPiece().name + boardX + " " + (boardY - i));
                        return true;
                    }
                    break;
                }
                else {
                    break;
                }
            }
        }


        // DIAGONAL --------------------------------
        // checks pieces to the bottom right of the king until a piece is encountered, if its a bishop or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardX + i < 8 && boardY + i < 8 && ChessBoard.chessBoard[boardX + i][boardY + i].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX + i][boardY + i].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX + i][boardY + i].getPiece().name.equals("Bishop") || ChessBoard.chessBoard[boardX + i][boardY + i].getPiece().name.equals("Queen")) {
                        System.out.println("Found  "  + ChessBoard.chessBoard[boardX+i][boardY+i].getPiece().name + (boardX + i) + " " + (boardY + i));
                        return true;
                    }
                    break;
                }
                else{
                    break;
                }
            }
        }

        // checks pieces to the bottom left of the king until a piece is encountered, if its a bishop or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardX + i < 8 && boardY - i >= 0 && ChessBoard.chessBoard[boardX + i][boardY - i].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX + i][boardY - i].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX + i][boardY - i].getPiece().name.equals("Bishop") || ChessBoard.chessBoard[boardX + i][boardY - i].getPiece().name.equals("Queen")) {
                        System.out.println("Found  "  + ChessBoard.chessBoard[boardX+i][boardY-i].getPiece().name + (boardX + i) + " " + (boardY - i));
                        return true;
                    }
                    break;
                }
                else{
                    break;
                }
            }
        }

        // checks pieces to the top right of the king until a piece is encountered, if its a bishop or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardX - i >= 0 && boardY + i < 8 && ChessBoard.chessBoard[boardX - i][boardY + i].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX - i][boardY + i].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX - i][boardY + i].getPiece().name.equals("Bishop") || ChessBoard.chessBoard[boardX - i][boardY + i].getPiece().name.equals("Queen")) {
                        System.out.println("Found  "  + ChessBoard.chessBoard[boardX-i][boardY+i].getPiece().name + (boardX - i) + " " + (boardY + i));
                        return true;
                    }
                    break;
                }
                else{
                    break;
                }
            }
        }

        // checks pieces to the top left of the king until a piece is encountered, if its a bishop or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardX - i >= 0 && boardY - i >= 0 && ChessBoard.chessBoard[boardX - i][boardY - i].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX - i][boardY - i].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX - i][boardY - i].getPiece().name.equals("Bishop") || ChessBoard.chessBoard[boardX - i][boardY - i].getPiece().name.equals("Queen")) {
                        System.out.println("Found  "  + ChessBoard.chessBoard[boardX-i][boardY-i].getPiece().name + (boardX - i) + " " + (boardY - i));
                        return true;
                    }
                    break;
                }
                else{
                    break;
                }
            }
        }



        // checks if there is a knight in any of the 8 possible positions
        int[] xKnight = {2, 1, -1, -2, -2, -1, 1, 2};
        int[] yKnight = {1, 2, 2, 1, -1, -2, -2, -1};
        for (int i = 0; i < 8; i++) {
            if (boardX + xKnight[i] >= 0 && boardX + xKnight[i] < 8 && boardY + yKnight[i] >= 0 && boardY + yKnight[i] < 8) {
                if (ChessBoard.chessBoard[boardX + xKnight[i]][boardY + yKnight[i]].getPiece() != null) {
                    if (ChessBoard.chessBoard[boardX + xKnight[i]][boardY + yKnight[i]].getPiece().name.equals("Knight") && ChessBoard.chessBoard[boardX + xKnight[i]][boardY + yKnight[i]].getPiece().color != this.color) {
                        System.out.println("Found  "  + ChessBoard.chessBoard[boardX+xKnight[i]][boardY+yKnight[i]].getPiece().name + (boardX + xKnight[i]) + " " + (boardY + yKnight[i]));
                        return true;
                    }
                }
            }
        }


       return false;
    }





}

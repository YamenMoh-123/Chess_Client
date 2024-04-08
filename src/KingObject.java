import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class KingObject extends PieceObject {
    private int boardX, boardY;
    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};

    static BufferedImage[] PieceSprite = Resources.pieceSheet.getImagesFrom(0, 0);
    static BufferedImage[] PieceSpriteBlack = Resources.pieceSheet.getImagesFrom(6, 6);

    public KingObject(int x, int y, int boardX, int boardY, Color color) {
        super("King", color, x, y, false);
        this.boardX = boardX;
        this.boardY = boardY;
    }

    @Override
    public int[] getPos() {
        return new int[]{boardX, boardY};
    }

    @Override
    public ArrayList<String> validMoves(String startingPos, String type) {
        if ("King".equals(type)) {
            return moveKing(startingPos);
        }
        return super.validMoves(startingPos, type);
    }


    @Override
    public ArrayList<String> moveKing(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;
        int[] xMoves = {x + 1, x + 1, x - 1, x - 1, x + 1, x - 1, x, x};
        int[] yMoves = {y + 1, y - 1, y + 1, y - 1, y, y, y + 1, y - 1};
        int tempX = boardX;
        int tempY = boardY;
        PieceObject temp = ChessBoard.blackKing;
        ChessBoard.chessBoard[boardX][boardY].setPiece(null);

        for (int i = 0; i < 8; i++) {
            if (xMoves[i] >= 0 && xMoves[i] < 8 && yMoves[i] >= 0 && yMoves[i] < 8) {
                boardX = 7 - yMoves[i];
                boardY = xMoves[i];

                if (!isKingChecked()) {
                    if (ChessBoard.chessBoard[boardX][boardY].getPiece() == null || ChessBoard.chessBoard[boardX][boardY].getPiece().color != this.color) {
                        validMoves.add((char) (xMoves[i] + 97) + " " + (yMoves[i] + 1));
                    }
                }
            }
        }
        boardX = tempX;
        boardY = tempY;
        ChessBoard.chessBoard[boardX][boardY].setPiece(temp);
        validMoves.addAll(getMoves());
        return validMoves;
    }


    public ArrayList<String> getMoves() {
        int tempX = boardX;
        int tempY = boardY;
        PieceObject temp = ChessBoard.blackKing;
        ChessBoard.chessBoard[boardX][boardY].setPiece(null);
        ArrayList<String> validMoves = new ArrayList<String>();
        if (!hasMoved && ChessBoard.chessBoard[0][0].getPiece().name.equals("Rook") && !ChessBoard.chessBoard[0][0].getPiece().hasMoved) {
            if (ChessBoard.chessBoard[0][1].getPiece() == null && ChessBoard.chessBoard[0][2].getPiece() == null && ChessBoard.chessBoard[0][3].getPiece() == null) {
                if (!isKingChecked()) {
                    boardX = 0;
                    boardY = 1;
                    if (!isKingChecked()) {
                        boardX = 0;
                        boardY = 2;
                        if (!isKingChecked()) {
                            boardX = 0;
                            boardY = 3;
                            if (!isKingChecked()) {
                                validMoves.add("c 8");
                            }
                        }
                    }
                }
            }
        }
        boardX = tempX;
        boardY = tempY;
        if (!hasMoved && ChessBoard.chessBoard[0][7].getPiece().name.equals("Rook") && !ChessBoard.chessBoard[0][7].getPiece().hasMoved) {
            if (ChessBoard.chessBoard[0][5].getPiece() == null && ChessBoard.chessBoard[0][6].getPiece() == null) {
                if (!isKingChecked()) {
                    boardX = 0;
                    boardY = 5;
                    if (!isKingChecked()) {
                        boardX = 0;
                        boardY = 6;
                        if (!isKingChecked()) {
                            validMoves.add("g 8");
                        }
                    }
                }
            }
        }
        boardX = tempX;
        boardY = tempY;
        ChessBoard.chessBoard[boardX][boardY].setPiece(temp);
        return validMoves;
    }


    @Override
    public boolean hasAvailableMoves() {

        for(int row =0; row <8; row ++){
            for(int col=0; col<8; col++){
                ChessSquare currSquare = ChessBoard.chessBoard[row][col];
                ChessBoard.previousClickedTile = currSquare;
                if(currSquare.getPiece()!=null && currSquare.getPiece().color == Color.BLACK){
                    String tempPos = (char) (col + 97) + " " + (8 - row);
                    if(!currSquare.getPiece().validMoves(tempPos, currSquare.getPiece().name).isEmpty()){
                        return true;
                    }
                }
            }
        }
        ChessBoard.previousClickedTile = null;
        return false;
    }


    @Override
    public boolean isKingChecked() {

        // checks pieces to the bottom of the king until a piece is encountered, if its a rook or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardX + i < 8 && ChessBoard.chessBoard[boardX + i][boardY].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX + i][boardY].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX + i][boardY].getPiece().name.equals("Rook") || ChessBoard.chessBoard[boardX + i][boardY].getPiece().name.equals("Queen")) {
                        return true;
                    }
                    break;
                } else {
                    break;
                }
            }
        }
        // checks pieces to the top of the king until a piece is encountered, if its a rook or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardX - i >= 0 && ChessBoard.chessBoard[boardX - i][boardY].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX - i][boardY].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX - i][boardY].getPiece().name.equals("Rook") || ChessBoard.chessBoard[boardX - i][boardY].getPiece().name.equals("Queen")) {
                        return true;
                    }
                    break;
                } else {
                    break;
                }
            }
        }
        // checks pieces to the right of the king until a piece is encountered, if its a rook or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardY + i < 8 && ChessBoard.chessBoard[boardX][boardY + i].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX][boardY + i].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX][boardY + i].getPiece().name.equals("Rook") || ChessBoard.chessBoard[boardX][boardY + i].getPiece().name.equals("Queen")) {
                        return true;
                    }
                    break;
                } else {
                    break;
                }
            }
        }
        // checks pieces to the left of the king until a piece is encountered, if its a rook or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardY - i >= 0 && ChessBoard.chessBoard[boardX][boardY - i].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX][boardY - i].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX][boardY - i].getPiece().name.equals("Rook") || ChessBoard.chessBoard[boardX][boardY - i].getPiece().name.equals("Queen")) {
                        return true;
                    }
                    break;
                } else {
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
                        return true;
                    }
                    break;
                } else {
                    break;
                }
            }
        }

        // checks pieces to the bottom left of the king until a piece is encountered, if its a bishop or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardX + i < 8 && boardY - i >= 0 && ChessBoard.chessBoard[boardX + i][boardY - i].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX + i][boardY - i].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX + i][boardY - i].getPiece().name.equals("Bishop") || ChessBoard.chessBoard[boardX + i][boardY - i].getPiece().name.equals("Queen")) {
                        return true;
                    }
                    break;
                } else {
                    break;
                }
            }
        }

        // checks pieces to the top right of the king until a piece is encountered, if its a bishop or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardX - i >= 0 && boardY + i < 8 && ChessBoard.chessBoard[boardX - i][boardY + i].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX - i][boardY + i].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX - i][boardY + i].getPiece().name.equals("Bishop") || ChessBoard.chessBoard[boardX - i][boardY + i].getPiece().name.equals("Queen")) {
                        return true;
                    }
                    break;
                } else {
                    break;
                }
            }
        }

        // checks pieces to the top left of the king until a piece is encountered, if its a bishop or queen, return true if its the opposite color
        for (int i = 1; i < 8; i++) {
            if (boardX - i >= 0 && boardY - i >= 0 && ChessBoard.chessBoard[boardX - i][boardY - i].getPiece() != null) {
                if (ChessBoard.chessBoard[boardX - i][boardY - i].getPiece().color != this.color) {
                    if (ChessBoard.chessBoard[boardX - i][boardY - i].getPiece().name.equals("Bishop") || ChessBoard.chessBoard[boardX - i][boardY - i].getPiece().name.equals("Queen")) {
                        return true;
                    }
                    break;
                } else {
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

                        return true;
                    }
                }
            }
        }


        if (boardX + 1 < 8 && boardY - 1 >= 0) { // Southwest
            if (ChessBoard.chessBoard[boardX + 1][boardY - 1].getPiece() != null &&
                    ChessBoard.chessBoard[boardX + 1][boardY - 1].getPiece().name.equals("Pawn") &&
                    ChessBoard.chessBoard[boardX + 1][boardY - 1].getPiece().color == Color.WHITE) {
                return true;
            }
        }
        if (boardX + 1 < 8 && boardY + 1 < 8) { // Southeast
            if (ChessBoard.chessBoard[boardX + 1][boardY + 1].getPiece() != null &&
                    ChessBoard.chessBoard[boardX + 1][boardY + 1].getPiece().name.equals("Pawn") &&
                    ChessBoard.chessBoard[boardX + 1][boardY + 1].getPiece().color == Color.WHITE) {
                return true;
            }
        }


        int[] xKing = {1, 1, 1, 0, 0, -1, -1, -1};
        int[] yKing = {1, 0, -1, 1, -1, 1, 0, -1};
        for (int i = 0; i < 8; i++) {
            if (boardX + xKing[i] >= 0 && boardX + xKing[i] < 8 && boardY + yKing[i] >= 0 && boardY + yKing[i] < 8) {
                if (ChessBoard.chessBoard[boardX + xKing[i]][boardY + yKing[i]].getPiece() != null) {
                    if (ChessBoard.chessBoard[boardX + xKing[i]][boardY + yKing[i]].getPiece().name.equals("King") && ChessBoard.chessBoard[boardX + xKing[i]][boardY + yKing[i]].getPiece().color != this.color) {
                        return true;
                    }
                }
            }
        }



        return false;
    }


}

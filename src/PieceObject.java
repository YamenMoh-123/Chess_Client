import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PieceObject {
    int value;
    String name;
    Color color;
    int x, y;

    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};
    static BufferedImage[] PieceSprite = Resources.pieceSheet.getImagesFrom(0, 5);  // make white and black to switch? or 2\
    static BufferedImage[] PieceSpriteBlack = Resources.pieceSheet.getImagesFrom(6, 11);  // make white and black to switch? or 2
    public boolean EnPassantAble;

    public PieceObject(String name, Color color, int x, int y, boolean EnPassantAble) {
        this.name = name;
        this.color = color;
        this.x = x + 5;
        this.y = y - 10;
        this.EnPassantAble = EnPassantAble;
    }


    public void tick() {
    }


    public ArrayList<String> validMoves(String startingPos, String type) {
        switch (type) {
            case "King":
                return moveKing(startingPos);
            case "Bishop":
                return moveBishop(startingPos);
            case "Rook":
                return moveRook(startingPos);
            case "Queen":
                return moveQueen(startingPos);
            case "Knight":
                return moveKnight(startingPos);
            case "Pawn":
                return movePawn(startingPos);
        }
        return null;
    }

    boolean isOpponentPiece(int x, int y) {
        return ChessBoard.chessBoard[y][x].getPiece().color != this.color;
    }

    public ArrayList<String> moveKing(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<String>();
        return validMoves;
    }

    public ArrayList<String> moveBishop(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;

        PieceObject temp = ChessBoard.previousClickedTile.getPiece();
        ChessBoard.previousClickedTile.setPiece(null);


        // top right
        for (int i = 1; i < 8; i++) {
            if (x + i < 8 && y + i < 8) {
                validMoves.add(colNames[x + i] + " " + (y + i + 1));

                if (ChessBoard.chessBoard[7 - y - i][x + i].getPiece() == null) {
                    ChessBoard.chessBoard[7 - y - i][x + i].setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    ChessBoard.chessBoard[7 - y - i][x + i].setPiece(null);
                }

                if (ChessBoard.chessBoard[7 - y - i][x + i].getPiece() != null) {
                    if (!isOpponentPiece(x + i, 7 - y - i)) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    break;
                }
            }
        }

        // bottom right
        for (int i = 1; i < 8; i++) {
            if (x + i < 8 && y - i >= 0) {
                validMoves.add(colNames[x + i] + " " + (y - i + 1));
                if (ChessBoard.chessBoard[7 - y + i][x + i].getPiece() == null) {
                    ChessBoard.chessBoard[7 - y + i][x + i].setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    ChessBoard.chessBoard[7 - y + i][x + i].setPiece(null);
                }


                if (ChessBoard.chessBoard[7 - y + i][x + i].getPiece() != null) {
                    if (!isOpponentPiece(x + i, 7 - y + i)) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    break;
                }
            }
        }

        // top left
        for (int i = 1; i < 8; i++) {
            if (x - i >= 0 && y + i < 8) {
                validMoves.add(colNames[x - i] + " " + (y + i + 1));
                if (ChessBoard.chessBoard[7 - y - i][x - i].getPiece() == null) {
                    ChessBoard.chessBoard[7 - y - i][x - i].setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    ChessBoard.chessBoard[7 - y - i][x - i].setPiece(null);
                }

                if (ChessBoard.chessBoard[7 - y - i][x - i].getPiece() != null) {
                    if (!isOpponentPiece(x - i, 7 - y - i)) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    break;
                }
            }
        }

        // top right
        for (int i = 1; i < 8; i++) {
            if (x - i >= 0 && y - i >= 0) {
                validMoves.add(colNames[x - i] + " " + (y - i + 1));
                if (ChessBoard.chessBoard[7 - y + i][x - i].getPiece() == null) {
                    ChessBoard.chessBoard[7 - y + i][x - i].setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    ChessBoard.chessBoard[7 - y + i][x - i].setPiece(null);
                }


                if (ChessBoard.chessBoard[7 - y + i][x - i].getPiece() != null) {
                    if (!isOpponentPiece(x - i, 7 - y + i)) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    break;
                }
            }
        }

        ChessBoard.previousClickedTile.setPiece(temp);
        return validMoves;
    }

    public ArrayList<String> moveRook(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = (startingPos.charAt(2) - 48);

        System.out.println("Rook tile = " + ChessBoard.previousClickedTile);
        PieceObject temp = ChessBoard.previousClickedTile.getPiece();
        ChessBoard.previousClickedTile.setPiece(null);


        //right
        for (int i = 1; i < 8; i++) {
            if (x + i < 8) {

                validMoves.add(colNames[x + i] + " " + y);
                if (ChessBoard.chessBoard[8 - y][x + i].getPiece() == null) {

                    ChessBoard.chessBoard[8 - y][x + i].setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    ChessBoard.chessBoard[8 - y][x + i].setPiece(null);
                }

                if (ChessBoard.chessBoard[8 - y][x + i].getPiece() != null) {
                    if (!isOpponentPiece(x + i, 8 - y)) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    else{
                        PieceObject piece = ChessBoard.chessBoard[8 - y][x + i].getPiece();
                        // set the piece to null
                        ChessBoard.chessBoard[8 - y][x + i].setPiece(null);
                        // move current peice there
                        ChessBoard.chessBoard[8 - y][x + i].setPiece(this);
// check if the king is checked
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                        // set the piece back
                        ChessBoard.chessBoard[8 - y][x + i].setPiece(piece);
                    }
                    break;
                }
            }
        }

        // left
        for (int i = 1; i < 8; i++) {
            if (x - i >= 0) {
                validMoves.add(colNames[x - i] + " " + y);

                System.out.println("HERE " + x + " " + y);
                if (ChessBoard.chessBoard[8 - y][x - i].getPiece() == null) {
                    ChessBoard.chessBoard[8 - y][x - i].setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    ChessBoard.chessBoard[8 - y][x - i].setPiece(null);
                }

                if (ChessBoard.chessBoard[8 - y][x - i].getPiece() != null) {
                    if (!isOpponentPiece(x - i, 8 - y)) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    else {
                        PieceObject piece = ChessBoard.chessBoard[8 - y][x - i].getPiece();
                        // set the piece to null
                        ChessBoard.chessBoard[8 - y][x - i].setPiece(null);
                        // move current peice there
                        ChessBoard.chessBoard[8 - y][x - i].setPiece(this);
// check if the king is checked
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                        // set the piece back
                        ChessBoard.chessBoard[8 - y][x - i].setPiece(piece);
                    }
                    break;
                }
            }
        }


        // up
        for (int i = 1; i < 8; i++) {
            if (y + i <= 8) {

                validMoves.add(colNames[x] + " " + (y + i));
                if (ChessBoard.chessBoard[8 - (y + i)][x].getPiece() == null) {
                    ChessBoard.chessBoard[8 - (y + i)][x].setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    ChessBoard.chessBoard[8 - (y + i)][x].setPiece(null);
                }


                if (ChessBoard.chessBoard[8 - (y + i)][x].getPiece() != null) {
                    if (!isOpponentPiece(x, 8 - (y + i))) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    else{
                        PieceObject piece = ChessBoard.chessBoard[8 - (y + i)][x].getPiece();
                        ChessBoard.chessBoard[8 - (y + i)][x].setPiece(null);
                        ChessBoard.chessBoard[8 - (y + i)][x].setPiece(this);
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                        ChessBoard.chessBoard[8 - (y + i)][x].setPiece(piece);
                    }
                    break;
                }
            }
        }

        // down
        for (int i = 1; i < 8; i++) {
            if (y - i > 0) {


                validMoves.add(colNames[x] + " " + (y - i));
                if (ChessBoard.chessBoard[8 - (y - i)][x].getPiece() == null) {
                    ChessBoard.chessBoard[8 - (y - i)][x].setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }

                    ChessBoard.chessBoard[8 - (y - i)][x].setPiece(null);
                }

                if (ChessBoard.chessBoard[8 - (y - i)][x].getPiece() != null) {
                    if (!isOpponentPiece(x, 8 - (y - i))) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    else{
                        PieceObject piece = ChessBoard.chessBoard[8 - (y - i)][x].getPiece();
                        ChessBoard.chessBoard[8 - (y - i)][x].setPiece(null);
                        ChessBoard.chessBoard[8 - (y - i)][x].setPiece(this);
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                        ChessBoard.chessBoard[8 - (y - i)][x].setPiece(piece);
                    }
                    break;
                }
            }
        }

        ChessBoard.previousClickedTile.setPiece(temp);
        return validMoves;
    }


    public ArrayList<String> moveQueen(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<String>();
        validMoves.addAll(moveRook(startingPos));
        validMoves.addAll(moveBishop(startingPos));
        return validMoves;
    }


    public ArrayList<String> movePawn(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;

        if (y == 6) {
            if (ChessBoard.chessBoard[2][x].getPiece() == null) {
                validMoves.add(colNames[x] + " 6");
                if (ChessBoard.chessBoard[3][x].getPiece() == null) {
                    validMoves.add(colNames[x] + " 5");
                    EnPassantAble = true;
                }
            }
        } else if (y < 6 && y > 0) {
            if (ChessBoard.chessBoard[8 - y][x].getPiece() == null) {
                validMoves.add(colNames[x] + " " + (y));
            }
        }
        if (x + 1 < 8 && y - 1 >= 0) {
            if (ChessBoard.chessBoard[7 - y + 1][x + 1].getPiece() != null) {
                if (isOpponentPiece(x + 1, 7 - y + 1)) {
                    validMoves.add(colNames[x + 1] + " " + (y));
                }
            }
        }
        if (x - 1 >= 0 && y - 1 >= 0) {
            if (ChessBoard.chessBoard[7 - y + 1][x - 1].getPiece() != null) {
                if (isOpponentPiece(x - 1, 7 - y + 1)) {
                    validMoves.add(colNames[x - 1] + " " + (y));
                }
            }
        }
        if (y == 3) {
            if (x + 1 < 8) {
                ChessBoard.chessBoard[7 - y][x + 1].setBackground(Color.YELLOW);
                if (ChessBoard.chessBoard[7 - y][x + 1].getPiece() != null) {
                    if (ChessBoard.chessBoard[7 - y][x + 1].getPiece().EnPassantAble) {
                        System.out.println("En Passant");
                        validMoves.add(colNames[x + 1] + " " + (y));
                    }
                }
            }
            if (x - 1 > 0) {
                if (ChessBoard.chessBoard[7 - y][x - 1].getPiece() != null) {
                    if (ChessBoard.chessBoard[7 - y][x - 1].getPiece().EnPassantAble) {
                        System.out.println("En Passant");
                        validMoves.add(colNames[x - 1] + " " + (y));
                    }
                }
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
                char charValX = (char) (xMoves[i] + 97);
                int inValX = letterToNumber(charValX);
                if (ChessBoard.chessBoard[7 - yMoves[i]][charValX - 97].getPiece() == null || isOpponentPiece(charValX - 97, 7 - yMoves[i])) {
                    validMoves.add(charValX + " " + (yMoves[i] + 1));
                }
            }
        }
        return validMoves;
    }


    public boolean isKingChecked() {
        return false;
    }

    public static int letterToNumber(char letter) {
        return Character.toLowerCase(letter) - 'a';
    }


    public int[] getPos() {
        return new int[]{};
    }

    public void render(Graphics2D g2d) {
        if (this.color == Color.WHITE) {
            switch (this.name) {
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
        } else {
            switch (this.name) {
                case "King":
                    g2d.drawImage(PieceSpriteBlack[0], this.x, this.y, 100, 100, null);
                    break;
                case "Queen":
                    g2d.drawImage(PieceSpriteBlack[1], this.x, this.y, 100, 100, null);
                    break;
                case "Rook":
                    g2d.drawImage(PieceSpriteBlack[4], this.x, this.y, 100, 100, null);
                    break;
                case "Bishop":
                    g2d.drawImage(PieceSpriteBlack[2], this.x, this.y, 100, 100, null);
                    break;
                case "Knight":
                    g2d.drawImage(PieceSpriteBlack[3], this.x, this.y, 100, 100, null);
                    break;
                case "Pawn":
                    g2d.drawImage(PieceSpriteBlack[5], this.x, this.y, 100, 100, null);
                    break;
            }
        }
        if (ChessBoard.movesShown) {
            for (String move : ChessBoard.previousMoves) {
                if (ChessBoard.chessBoard[7 - (move.charAt(2) - 49)][(move.charAt(0) - 97)].getPiece() == null) {
                    int centerX = ChessBoard.chessBoard[7 - (move.charAt(2) - 49)][(move.charAt(0) - 97)].getX();
                    int centerY = ChessBoard.chessBoard[7 - (move.charAt(2) - 49)][(move.charAt(0) - 97)].getY();
                    g2d.setColor(new Color(129, 150, 105));
                    g2d.fillOval(centerX + 43, centerY + 35, 30, 30);
                }
            }
        }
    }


}
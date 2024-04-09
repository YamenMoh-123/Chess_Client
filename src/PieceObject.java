import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PieceObject {
    public boolean hasMoved;
    String name;
    Color color;
    int x, y;

    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};
    static BufferedImage[] PieceSprite = Resources.pieceSheet.getImagesFrom(0, 5);
    static BufferedImage[] PieceSpriteBlack = Resources.pieceSheet.getImagesFrom(6, 11);
    public boolean EnPassantAble;

    public PieceObject(String name, Color color, int x, int y, boolean EnPassantAble) {
        this.name = name;
        this.color = color;

        this.x = x ;
        this.y = y ;

        this.EnPassantAble = EnPassantAble;
    }


    public void tick() {
    }


    // can use this.name refactor later
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

    // to override in kingobject
    public ArrayList<String> moveKing(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<String>();
        return validMoves;
    }

    public ArrayList<String> moveBishop(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;

        // save the current piece
        PieceObject temp = ChessBoard.previousClickedTile.getPiece();
        // set the current piece to null
        ChessBoard.previousClickedTile.setPiece(null);



        // top right (implementations for other directions are similar to this one)
        for (int i = 1; i < 8; i++) {
            if (x + i < 8 && y + i < 8) {

                ChessSquare currentSquare = ChessBoard.chessBoard[7 - y - i][x + i];
                validMoves.add(colNames[x + i] + " " + (y + i + 1));

                if (currentSquare.getPiece() == null) {
                  currentSquare.setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                   currentSquare.setPiece(null);
                }

                if (currentSquare.getPiece() != null) {
                    if (!isOpponentPiece(x + i, 7 - y - i)) {
                        validMoves.remove(validMoves.size() - 1);
                    } else {
                        PieceObject piece = currentSquare.getPiece();
                        currentSquare.setPiece(this);
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                        currentSquare.setPiece(piece);
                    }
                    break;
                }
            }
        }

        // bottom right
        for (int i = 1; i < 8; i++) {
            if (x + i < 8 && y - i >= 0) {

                ChessSquare currentSquare = ChessBoard.chessBoard[7 - y + i][x + i];
                validMoves.add(colNames[x + i] + " " + (y - i + 1));
                if (currentSquare.getPiece() == null) {
                    currentSquare.setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    currentSquare.setPiece(null);
                }


                if (currentSquare.getPiece() != null) {
                    if (!isOpponentPiece(x + i, 7 - y + i)) {
                        validMoves.remove(validMoves.size() - 1);
                    } else {
                        PieceObject piece = currentSquare.getPiece();
                        currentSquare.setPiece(this);
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                        currentSquare.setPiece(piece);
                    }
                    break;
                }
            }
        }

        // top left
        for (int i = 1; i < 8; i++) {
            if (x - i >= 0 && y + i < 8) {
                ChessSquare currentSquare = ChessBoard.chessBoard[7 - y - i][x - i];
                validMoves.add(colNames[x - i] + " " + (y + i + 1));
                if (currentSquare.getPiece() == null) {
                    currentSquare.setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    currentSquare.setPiece(null);
                }

                if (currentSquare.getPiece() != null) {
                    if (!isOpponentPiece(x - i, 7 - y - i)) {
                        validMoves.remove(validMoves.size() - 1);
                    } else {
                        PieceObject piece = currentSquare.getPiece();
                        currentSquare.setPiece(this);
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                        currentSquare.setPiece(piece);
                    }
                    break;
                }
            }
        }

        // top right
        for (int i = 1; i < 8; i++) {
            if (x - i >= 0 && y - i >= 0) {
                ChessSquare currentSquare = ChessBoard.chessBoard[7 - y + i][x - i];
                validMoves.add(colNames[x - i] + " " + (y - i + 1));
                if (currentSquare.getPiece() == null) {
                    currentSquare.setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    currentSquare.setPiece(null);
                }


                if (currentSquare.getPiece() != null) {
                    if (!isOpponentPiece(x - i, 7 - y + i)) {
                        validMoves.remove(validMoves.size() - 1);
                    } else {
                        PieceObject piece = currentSquare.getPiece();
                        currentSquare.setPiece(this);
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                        currentSquare.setPiece(piece);
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


        // save the current piece
        PieceObject temp = ChessBoard.previousClickedTile.getPiece();
        // set the current piece to null
        ChessBoard.previousClickedTile.setPiece(null);


        //right (logic is same for rest of function in different directions)
        for (int i = 1; i < 8; i++) {
            if (x + i < 8) {
                ChessSquare currentSquare = ChessBoard.chessBoard[8 - y][x + i];

                // add the current move to the valid moves
                validMoves.add(colNames[x + i] + " " + y);
                if (currentSquare.getPiece() == null) {
                    // if moving puts the king in check, remove it
                    currentSquare.setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    // set the piece back to null
                    currentSquare.setPiece(null);
                }

                // if there is a piece where current piece can move
               else{
                    // if its not an opponent piece, remove the move
                    if (!isOpponentPiece(x + i, 8 - y)) {
                        validMoves.remove(validMoves.size() - 1);
                    } else {
                        // attempt to take the piece
                        // if the king is not in check afterwards, add the move, put the pieces back
                        PieceObject piece =currentSquare.getPiece();
                        currentSquare.setPiece(this);
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                       currentSquare.setPiece(piece);
                    }
                    break;
                }
            }
        }

        // left
        for (int i = 1; i < 8; i++) {
            if (x - i >= 0) {
                ChessSquare currentSquare = ChessBoard.chessBoard[8 - y][x - i];

                validMoves.add(colNames[x - i] + " " + y);
                if (currentSquare.getPiece() == null) {
                    currentSquare.setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    currentSquare.setPiece(null);
                }

               else{
                    if (!isOpponentPiece(x - i, 8 - y)) {
                        validMoves.remove(validMoves.size() - 1);
                    } else {
                        PieceObject piece = currentSquare.getPiece();
                       currentSquare.setPiece(this);
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                        // set the piece back
                       currentSquare.setPiece(piece);
                    }
                    break;
                }
            }
        }


        // up
        for (int i = 1; i < 8; i++) {
            if (y + i <= 8) {

                ChessSquare currentSquare = ChessBoard.chessBoard[8 - (y + i)][x];
                validMoves.add(colNames[x] + " " + (y + i));
                if (currentSquare.getPiece() == null) {
                    currentSquare.setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                   currentSquare.setPiece(null);
                }


             else{
                    if (!isOpponentPiece(x, 8 - (y + i))) {
                        validMoves.remove(validMoves.size() - 1);
                    } else {
                        PieceObject piece = currentSquare.getPiece();
                      currentSquare.setPiece(this);
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                     currentSquare.setPiece(piece);
                    }
                    break;
                }
            }
        }

        // down
        for (int i = 1; i < 8; i++) {
            if (y - i > 0) {

                ChessSquare currentSquare = ChessBoard.chessBoard[8 - (y - i)][x];
                validMoves.add(colNames[x] + " " + (y - i));
                if (currentSquare.getPiece() == null) {
                    currentSquare.setPiece(this);
                    if (ChessBoard.blackKing.isKingChecked()) {
                        validMoves.remove(validMoves.size() - 1);
                    }
                    currentSquare.setPiece(null);
                }

              else{
                    if (!isOpponentPiece(x, 8 - (y - i))) {
                        validMoves.remove(validMoves.size() - 1);
                    } else {
                        PieceObject piece = currentSquare.getPiece();
                       currentSquare.setPiece(this);
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                       currentSquare.setPiece(piece);
                    }
                    break;
                }
            }
        }

        ChessBoard.previousClickedTile.setPiece(temp);
        return validMoves;
    }


    // a combination of rook and bishop moves
    public ArrayList<String> moveQueen(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<String>();
        validMoves.addAll(moveRook(startingPos));
        validMoves.addAll(moveBishop(startingPos));
        return validMoves;
    }


    public ArrayList<String> movePawn(String startingPos){
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;

        PieceObject temp = ChessBoard.previousClickedTile.getPiece();
        ChessBoard.previousClickedTile.setPiece(null);


        // Checking for one move forward
        if(ChessBoard.chessBoard[8-y][x].getPiece() == null) {
            ChessSquare currentSquare = ChessBoard.chessBoard[8-y][x];
            // same implementation of king check as in rook
            currentSquare.setPiece(this);
            if (!ChessBoard.blackKing.isKingChecked()) {
                validMoves.add(colNames[x] + " " + (y));
            }
            currentSquare.setPiece(null);


            // check for two moves forward
            if(ChessBoard.chessBoard[3][x].getPiece() == null && y == 6) {
                ChessBoard.chessBoard[3][x].setPiece(this);
                if (!ChessBoard.blackKing.isKingChecked()) {
                    validMoves.add(colNames[x] + " 5");
                }
                ChessBoard.chessBoard[3][x].setPiece(null);
                EnPassantAble = true;
            }
        }


        // Checking for diagonal moves
        if (x + 1 < 8 && y - 1 >= 0) {
            ChessSquare currentSquare = ChessBoard.chessBoard[7 - y + 1][x + 1];
            if (currentSquare.getPiece() != null) {
                PieceObject piece = currentSquare.getPiece();
                if (isOpponentPiece(x + 1, 7 - y + 1)) {
                    currentSquare.setPiece(this);
                    if (!ChessBoard.blackKing.isKingChecked()) {
                        validMoves.add(colNames[x + 1] + " " + (y));
                    }
                    currentSquare.setPiece(piece);
                }
            }
        }
        if (x - 1 >= 0 && y - 1 >= 0) {
            ChessSquare currentSquare = ChessBoard.chessBoard[7 - y + 1][x - 1];
            if (currentSquare.getPiece() != null) {
                PieceObject piece = currentSquare.getPiece();
                if (isOpponentPiece(x - 1, 7 - y + 1)) {
                    currentSquare.setPiece(this);
                    if (!ChessBoard.blackKing.isKingChecked()) {
                        validMoves.add(colNames[x - 1] + " " + (y) );
                    }
                    currentSquare.setPiece(piece);
                }
            }
        }

        if(y == 3){
            if(x + 1 < 8){
                if(ChessBoard.chessBoard[7-y][x+1].getPiece() != null){
                    if(ChessBoard.chessBoard[7-y][x+1].getPiece().EnPassantAble){
                        validMoves.add(colNames[x+1] + " " + y + " wr");
                    }
                }
            }
            if(x - 1 > -1){
                if(ChessBoard.chessBoard[7-y][x-1].getPiece() != null){
                    if(ChessBoard.chessBoard[7-y][x-1].getPiece().EnPassantAble){
                        validMoves.add(colNames[x-1] + " " + y + " wl");
                    }
                }
            }
        }

        ChessBoard.previousClickedTile.setPiece(temp);
        return validMoves;
    }


    public ArrayList<String> moveKnight(String startingPos) {
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;

        PieceObject temp = ChessBoard.previousClickedTile.getPiece();
        ChessBoard.previousClickedTile.setPiece(null);

        int[] xMoves = {x + 2, x + 2, x - 2, x - 2, x + 1, x + 1, x - 1, x - 1};
        int[] yMoves = {y + 1, y - 1, y + 1, y - 1, y + 2, y - 2, y + 2, y - 2};

        for (int i = 0; i < 8; i++) {
            if (xMoves[i] >= 0 && xMoves[i] < 8 && yMoves[i] >= 0 && yMoves[i] < 8) {

                ChessSquare currentSquare = ChessBoard.chessBoard[7 - yMoves[i]][xMoves[i]];

                if (currentSquare.getPiece() == null || isOpponentPiece(xMoves[i], 7 - yMoves[i])) {
                    validMoves.add((char) (xMoves[i] + 97) + " " + (yMoves[i] + 1));

                    // if the move puts the king in check, remove it
                    if (currentSquare.getPiece() == null) {
                        currentSquare.setPiece(this);
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                        currentSquare.setPiece(null);
                    } else {
                        // if the move puts the king in check, remove it
                        PieceObject piece =currentSquare.getPiece();
                        currentSquare.setPiece(this);
                        if (ChessBoard.blackKing.isKingChecked()) {
                            validMoves.remove(validMoves.size() - 1);
                        }
                        currentSquare.setPiece(piece);
                    }
                }
            }
        }
        ChessBoard.previousClickedTile.setPiece(temp);
        return validMoves;
    }


    public boolean isKingChecked() {
        return false;
    }

    public boolean hasAvailableMoves(){
        return true;
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
                    int centerX = ChessBoard.chessBoard[7 - (move.charAt(2) - 49)][(move.charAt(0) - 97)].getX() ;
                    int centerY = ChessBoard.chessBoard[7 - (move.charAt(2) - 49)][(move.charAt(0) - 97)].getY() ;
                    g2d.setColor(new Color(129, 150, 105));
                    g2d.fillOval(centerX + 45, centerY + 35, 30, 30);
                }
            }
        }
    }

    public int[] getPos(){
        return new int[]{};
    }


}

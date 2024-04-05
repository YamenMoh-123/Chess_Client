import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class KingObject extends PieceObject {
    private int x;
    private int y;
    private int boardX, boardY;
    private Color color;

    static BufferedImage[] PieceSprite = Resources.pieceSheet.getImagesFrom(0, 0);
    static BufferedImage[] PieceSpriteBlack = Resources.pieceSheet.getImagesFrom(6, 6);

    public KingObject(int x, int y, int boardX, int boardY, Color color){
        super("King", color, x, y);
        this.boardX = boardX;
        this.boardY = boardY;
    }




    @Override
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


    public boolean isKingChecked() {
        // Check if there is a rook or queen in any 4 directions
        for (int i = 1; i < 8; i++) {
            if (this.x + i < 8 && ChessBoard.chessBoard[this.y][this.x + i] != null) {
                if (ChessBoard.chessBoard[this.y][this.x + i].getPiece() != null) {
                    if (ChessBoard.chessBoard[this.y][this.x + i].getPiece().name.equals("Rook") || ChessBoard.chessBoard[this.y][this.x + i].getPiece().name.equals("Queen")) {
                        return true;
                    }
                    break;
                }
            }
        }
        for (int i = 1; i < 8; i++) {
            if (this.x - i >= 0 && ChessBoard.chessBoard[this.y][this.x - i] != null) {
                if (ChessBoard.chessBoard[this.y][this.x - i].getPiece() != null) {
                    if (ChessBoard.chessBoard[this.y][this.x - i].getPiece().name.equals("Rook") || ChessBoard.chessBoard[this.y][this.x - i].getPiece().name.equals("Queen")) {
                        return true;
                    }
                    break;
                }
            }
        }
        for (int i = 1; i < 8; i++) {
            if (this.y + i < 8 && ChessBoard.chessBoard[this.y + i][this.x] != null) {
                if (ChessBoard.chessBoard[this.y + i][this.x].getPiece() != null) {
                    if (ChessBoard.chessBoard[this.y + i][this.x].getPiece().name.equals("Rook") || ChessBoard.chessBoard[this.y + i][this.x].getPiece().name.equals("Queen")) {
                        return true;
                    }
                    break;
                }
            }
        }
        for (int i = 1; i < 8; i++) {
            if (this.y - i >= 0 && ChessBoard.chessBoard[this.y - i][this.x] != null) {
                if (ChessBoard.chessBoard[this.y - i][this.x].getPiece() != null) {
                    if (ChessBoard.chessBoard[this.y - i][this.x].getPiece().name.equals("Rook") || ChessBoard.chessBoard[this.y - i][this.x].getPiece().name.equals("Queen")) {
                        return true;
                    }
                    break;
                }
            }
        }

        return false;
    }


}

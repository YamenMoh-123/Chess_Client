import java.awt.*;
import java.util.ArrayList;

public class KingObject extends PieceObject {
    private int boardX, boardY;
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

        // Possible moves for king
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

                // Check if the king is not in check after moving
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

        // add castling moves
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


        // loops through the board and returns true if this color has a valid move
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
        if (checkLines()) return true;

        // Diagonal checks for Bishop or Queen

        if (checkDiagonals()) return true;

        // Knight checks
        if (checkKnights()) return true;

        // Pawn checks
        if (checkPawns()) return true;

        // King checks
        if (checkKing()) return true;

        return false;
    }

    private boolean checkLines() {
        int[][] directions = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                int dx = boardX + i * dir[0];
                int dy = boardY + i * dir[1];
                if (dx >= 0 && dx < 8 && dy >= 0 && dy < 8 && ChessBoard.chessBoard[dx][dy].getPiece() != null) {
                    if (ChessBoard.chessBoard[dx][dy].getPiece().color != this.color) {
                        if ("Rook".equals(ChessBoard.chessBoard[dx][dy].getPiece().name) || "Queen".equals(ChessBoard.chessBoard[dx][dy].getPiece().name)) {
                            return true;
                        }
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkDiagonals() {
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                int dx = boardX + i * dir[0];
                int dy = boardY + i * dir[1];
                if (dx >= 0 && dx < 8 && dy >= 0 && dy < 8 && ChessBoard.chessBoard[dx][dy].getPiece() != null) {
                    if (ChessBoard.chessBoard[dx][dy].getPiece().color != this.color) {
                        if ("Bishop".equals(ChessBoard.chessBoard[dx][dy].getPiece().name) || "Queen".equals(ChessBoard.chessBoard[dx][dy].getPiece().name)) {
                            return true;
                        }
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkKnights() {
        int[] xKnight = {2, 1, -1, -2, -2, -1, 1, 2};
        int[] yKnight = {1, 2, 2, 1, -1, -2, -2, -1};
        for (int i = 0; i < 8; i++) {
            int dx = boardX + xKnight[i];
            int dy = boardY + yKnight[i];
            if (dx >= 0 && dx < 8 && dy >= 0 && dy < 8 && ChessBoard.chessBoard[dx][dy].getPiece() != null) {
                if ("Knight".equals(ChessBoard.chessBoard[dx][dy].getPiece().name) && ChessBoard.chessBoard[dx][dy].getPiece().color != this.color) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkPawns() {
        if (boardX + 1 < 8 && boardY - 1 >= 0 && checkPawn(boardX + 1, boardY - 1)) return true;
        if (boardX + 1 < 8 && boardY + 1 < 8 && checkPawn(boardX + 1, boardY + 1)) return true;
        return false;
    }

    private boolean checkPawn(int x, int y) {
        return ChessBoard.chessBoard[x][y].getPiece() != null && "Pawn".equals(ChessBoard.chessBoard[x][y].getPiece().name) && ChessBoard.chessBoard[x][y].getPiece().color == Color.WHITE;
    }

    private boolean checkKing() {
        int[] xKing = {1, 1, 1, 0, 0, -1, -1, -1};
        int[] yKing = {1, 0, -1, 1, -1, 1, 0, -1};
        for (int i = 0; i < 8; i++) {
            int dx = boardX + xKing[i];
            int dy = boardY + yKing[i];
            if (dx >= 0 && dx < 8 && dy >= 0 && dy < 8 && ChessBoard.chessBoard[dx][dy].getPiece() != null) {
                if ("King".equals(ChessBoard.chessBoard[dx][dy].getPiece().name) && ChessBoard.chessBoard[dx][dy].getPiece().color != this.color) {
                    return true;
                }
            }
        }
        return false;
    }




}

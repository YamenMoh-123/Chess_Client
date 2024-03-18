import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.Objects;

public class ChessBoard extends JPanel {
    private Thread thread;
    private BufferStrategy bs;
    private static final int ROWS = 8;
    private static final int COLS = 8;
    private static int Game_Width = 800;
    private static int Game_Height = 800;

    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};

   /* private String[][] boardInit = {
            {"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"},
            {"Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn"},
            {"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"},
    };

    */

    private String[][] boardInit = {
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Pawn", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"}
    };

    private ChessSquare[][] chessBoard = new ChessSquare[ROWS][COLS];

    private ChessSquare previousClickedTile = null;
    private Color previousTileColor = null;
 //   public static GameManager gameManager = new GameManager();

    // action listener for the buttons
     private ActionListener pieceListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(((JButton) e.getSource()).getName());

            if(previousClickedTile == null){
                previousClickedTile = (ChessSquare) e.getSource();
                previousTileColor = previousClickedTile.getBackground();
                previousClickedTile.setBackground(Color.RED);
            }
            else {
                previousClickedTile.setBackground(previousTileColor);
                previousClickedTile = (ChessSquare) e.getSource();
                previousTileColor = previousClickedTile.getBackground();
                previousClickedTile.setBackground(Color.RED);
                //System.out.println(previousClickedTile.getName());
            }


            System.out.println(previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name));


            // set piece function takes in current button position
            // call piece.validateMove()
            // piece calls its own validateMove function, returns a list of possible moves (taking / vs movement for pawn).
            // this checks if the move is valid. obstructed, can take
            // for checks. the piece is allowed to move on the backend, isCheck is called and returns true, the move is invalid
        }
    };


    public ChessBoard() {
        setLayout(new GridLayout(ROWS, COLS));

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                ChessSquare currButton = new ChessSquare();
                currButton.setName(colNames[col] + " " + (ROWS-row));
                currButton.setBackground(Color.WHITE);
                currButton.setBorder(new EmptyBorder(0, 0, 0, 0));
                currButton.setPos(col * (Game_Width/COLS), row * (Game_Height/ROWS));


                if ((row + col) % 2 == 0) {
                    currButton.setBackground(Color.BLACK);
                }

                currButton.addActionListener(pieceListener);
                chessBoard[row][col] = currButton;
                add(chessBoard[row][col]);
            }
        }
            initBoard();

    }

    public void initBoard(){
        for(int row = 0; row < COLS; row ++){
            for(int col = 0; col < ROWS; col++){
                if(!boardInit[row][col].equals("Empty")){
                    int []pos = chessBoard[row][col].getPos();
                    PieceObject piece = new PieceObject(boardInit[row][col], Color.BLACK, pos[0], pos[1]);
                    chessBoard[row][col].setPiece(piece);
                    GameCanvas.gameManager.addGameObject(piece);
            }
            }

        }
    }

}

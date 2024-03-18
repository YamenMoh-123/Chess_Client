import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

public class ChessBoard extends JPanel {
    private Thread thread;
    private BufferStrategy bs;
    private static final int ROWS = 8;
    private static final int COLS = 8;

    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};

    private JButton[][] chessBoard = new JButton[ROWS][COLS];

    private ChessSquare previousClickedTile = null;
    private Color previousTileColor = null;
    public static GameManager gameManager = new GameManager();

    // action listener for the buttons
     private ActionListener pieceListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
           // System.out.println(((JButton) e.getSource()).getName());

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

            System.out.println(previousClickedTile.getPiece().validMoves(previousClickedTile.getName()));
            //System.out.println("run");


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

                //currButton.setPiece(new QueenObject(5, "Rook", Color.BLACK));

                if ((row + col) % 2 == 0) {
                    currButton.setBackground(Color.BLACK);
                }

                currButton.addActionListener(pieceListener);
                chessBoard[row][col] = currButton;
                add(chessBoard[row][col]);
            }
        }

        QueenObject piece = new QueenObject(5, "Queen", Color.BLACK);
        System.out.println("made a queen");
        gameManager.addGameObject(piece);

    }
}

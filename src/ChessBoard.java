import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

public class ChessBoard extends JPanel {
    private static final int ROWS = 8;
    private static final int COLS = 8;
    private static final int BOARD_SIZE = 800;
    private static final int FONT_SIZE = 16;
    private static final int PADDING_RIGHT = 10;
    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};
    private ChessSquare[][] chessBoard = new ChessSquare[ROWS][COLS];

    private ChessSquare previousClickedTile = null;
    private Color previousTileColor = null;
 //   public static GameManager gameManager = new GameManager();

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


            // set piece function takes in current button position
            // call piece.validateMove()
            // piece calls its own validateMove function, returns a list of possible moves (taking / vs movement for pawn).
            // this checks if the move is valid. obstructed, can take
            // for checks. the piece is allowed to move on the backend, isCheck is called and returns true, the move is invalid
        }
    };


    public ChessBoard() {
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        JPanel bottomLabels = new JPanel(new GridLayout(1, COLS));
        JPanel sideLabels = new JPanel(new GridLayout(ROWS, 1));

        Font labelFont = new Font("SansSerif", Font.BOLD, FONT_SIZE);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                ChessSquare currButton = new ChessSquare();
                currButton.setName(colNames[col] + " " + (ROWS - row));
                currButton.setBackground(Color.WHITE);
                currButton.setBorder(new EmptyBorder(0, 0, 0, 0));
                currButton.setPos(col * (BOARD_SIZE / COLS), row * (BOARD_SIZE / ROWS));

                if ((row + col) % 2 == 0) {
                    currButton.setBackground(Color.BLACK);
                }

                currButton.addActionListener(pieceListener);
                chessBoard[row][col] = currButton;
                boardPanel.add(chessBoard[row][col]);
            }
            JLabel sideLabel = new JLabel(String.valueOf(ROWS - row));
            sideLabel.setHorizontalAlignment(JLabel.CENTER);
            sideLabel.setFont(labelFont);
            sideLabel.setBorder(new EmptyBorder(0, 0, PADDING_RIGHT, 0));
            sideLabels.add(sideLabel);
        }

        for (String colName : colNames) {
            JLabel bottomLabel = new JLabel(colName);
            bottomLabel.setHorizontalAlignment(JLabel.CENTER);
            bottomLabel.setFont(labelFont);
            bottomLabels.add(bottomLabel);
        }

        add(boardPanel, BorderLayout.CENTER);
        add(bottomLabels, BorderLayout.SOUTH);
        add(sideLabels, BorderLayout.WEST);
    }
}

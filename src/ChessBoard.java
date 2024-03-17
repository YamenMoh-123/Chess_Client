import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessBoard extends JPanel {
    private static final int ROWS = 8;
    private static final int COLS = 8;

    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};

    private JButton[][] chessBoard = new JButton[ROWS][COLS];

    private JButton previousClickedTile = null;
    private Color previousTileColor = null;

    // action listener for the buttons
     private ActionListener pieceListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
           // System.out.println(((JButton) e.getSource()).getName());

            if(previousClickedTile == null){
                previousClickedTile = (JButton) e.getSource();
                previousTileColor = previousClickedTile.getBackground();
                previousClickedTile.setBackground(Color.RED);
            }
            else {
                previousClickedTile.setBackground(previousTileColor);
                previousClickedTile = (JButton) e.getSource();
                previousTileColor = previousClickedTile.getBackground();
                previousClickedTile.setBackground(Color.RED);
                System.out.println(previousClickedTile.getName());
            }


        }
    };


    public ChessBoard() {
        setLayout(new GridLayout(ROWS, COLS));


        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                JButton currButton = new JButton();
                currButton.setName(colNames[col] + " " + (ROWS-row));
                currButton.setBackground(Color.WHITE);
                currButton.setBorder(new EmptyBorder(0, 0, 0, 0));

                if ((row + col) % 2 == 0) {
                    currButton.setBackground(Color.BLACK);
                }

                currButton.addActionListener(pieceListener);
                chessBoard[row][col] = currButton;
                add(chessBoard[row][col]);
            }
        }
    }




}

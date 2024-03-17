import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChessBoard extends JPanel {
    private static final int ROWS = 8;
    private static final int COLS = 8;

    private JButton[][] chessBoard = new JButton[ROWS][COLS];

    public ChessBoard() {
        setLayout(new GridLayout(ROWS, COLS));


        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                JButton currButton = new JButton();
                currButton.setName(row + " " + col);
                currButton.setBackground(Color.WHITE);
                currButton.setBorder(new EmptyBorder(0, 0, 0, 0));

                if ((row + col) % 2 == 0) {
                    currButton.setBackground(Color.BLACK);
                }


                chessBoard[row][col] = currButton;
                add(chessBoard[row][col]);
            }
        }
    }
}

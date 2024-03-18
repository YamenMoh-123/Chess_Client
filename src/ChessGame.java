import javax.swing.*;
import java.awt.*;

public class ChessGame extends JPanel{
    private static int Game_Width = 800;
    private static int Game_Height = 800;


    public ChessGame(){
        JPanel mainPanel = new JPanel();
        mainPanel.setSize(650, 650);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess Game");
        frame.setLayout(new BorderLayout());

        ChessBoard chessBoard = new ChessBoard();
        GameCanvas gameCanvas = new GameCanvas();

        frame.add(chessBoard, BorderLayout.CENTER);
        frame.add(gameCanvas, BorderLayout.SOUTH); // or another layout arrangement

        frame.pack();
        frame.setVisible(true);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gameCanvas.start();
            }
        });



    }

}
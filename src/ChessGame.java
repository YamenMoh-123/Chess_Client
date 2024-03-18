import javax.swing.*;
import java.awt.*;

public class ChessGame extends JPanel {
    private static int Game_Width = 800;
    private static int Game_Height = 800;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessBoard chessBoard = new ChessBoard();
            GameCanvas gameCanvas = new GameCanvas();

            gameCanvas.setOpaque(false);


            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(Game_Width, Game_Height));


            chessBoard.setBounds(0, 0, Game_Width, Game_Height);
            gameCanvas.setBounds(0, 0, Game_Width, Game_Height);


            layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
            layeredPane.add(gameCanvas, JLayeredPane.PALETTE_LAYER);

            JFrame frame = new JFrame("Chess Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setContentPane(layeredPane);

            frame.pack();
            frame.setSize(Game_Width, Game_Height);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            gameCanvas.start();
        });
    }
}

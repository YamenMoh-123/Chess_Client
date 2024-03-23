import javax.swing.*;
import java.awt.*;

public class ChessGame extends JPanel {
    static int Game_Width = 800;
    static int Game_Height = 80 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chess Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            LaunchScreen homeScreen = new LaunchScreen(frame);
            frame.setContentPane(homeScreen);

            frame.pack();
            frame.setSize(Game_Width, Game_Height);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
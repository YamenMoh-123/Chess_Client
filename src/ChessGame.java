import javax.swing.*;
import java.awt.*;

public class ChessGame extends JPanel {
     static int Game_Width = 800;
     static int Game_Height = 800;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chess Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            LaunchScreen homeScreen = new LaunchScreen(frame);
            frame.setContentPane(homeScreen);

            frame.pack();
            frame.setSize(Game_Width + 50, Game_Height + 50);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

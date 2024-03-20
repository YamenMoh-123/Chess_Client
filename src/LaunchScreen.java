import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class LaunchScreen extends JPanel {
    private JFrame frame;
    private JComboBox<String> gameTimeDropdown;
    private JComboBox<String> gameThemeDropdown;

    public LaunchScreen(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());

        // Game Time Dropdown
        String[] gameTimes = {"1 min", "3 min", "10 min", "30 min"};
        String[] gameThemes = {"Classic", "Blue", "Purple", "Green"};
        gameTimeDropdown = new JComboBox<>(gameTimes);
        gameThemeDropdown = new JComboBox<>(gameThemes);
        gameTimeDropdown.setSelectedIndex(0); // Default selection
        gameThemeDropdown.setSelectedIndex(0); // Default selection

        // Start Button
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to the chess game screen
                ChessBoard chessBoard = new ChessBoard();
                GameCanvas gameCanvas = new GameCanvas();

                gameCanvas.setOpaque(false);

                JLayeredPane layeredPane = new JLayeredPane();
                layeredPane.setPreferredSize(new Dimension(ChessGame.Game_Width, ChessGame.Game_Height));

                chessBoard.setBounds(0, 0, ChessGame.Game_Width, ChessGame.Game_Height);
                gameCanvas.setBounds(0, 0, ChessGame.Game_Width, ChessGame.Game_Height);

                layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
                layeredPane.add(gameCanvas, JLayeredPane.PALETTE_LAYER);

                frame.setContentPane(layeredPane);
                frame.revalidate();
                frame.repaint();

                gameCanvas.start();
            }
        });


        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(gameTimeDropdown);
        buttonPanel.add(gameThemeDropdown);
        buttonPanel.add(startButton);


        add(buttonPanel, BorderLayout.CENTER);
    }
}
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
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
        setBackground(Color.LIGHT_GRAY); // Set background color to grey for the entire panel

        // Title Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.LIGHT_GRAY); // Set background color to grey for the title panel
        JLabel logoLabel = new JLabel(new ImageIcon("Resources/Sprites/img.png")); // Chess piece symbol
        logoLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        logoPanel.add(logoLabel);

        // Game Time Dropdown
        String[] gameTimes = {"1 min", "3 min", "10 min", "30 min"};
        gameTimeDropdown = createRoundedComboBox(gameTimes, 10, new Dimension(200, 60), Color.WHITE, Color.BLACK, 20);
        gameTimeDropdown.setSelectedIndex(0); // Default selection

        // Game Theme Dropdown
        String[] gameThemes = {"Classic", "Blue", "Purple", "Green"};
        gameThemeDropdown = createRoundedComboBox(gameThemes, 10, new Dimension(200, 60), Color.WHITE, Color.BLACK, 20);
        gameThemeDropdown.setSelectedIndex(0); // Default selection

        // Start Button
        JButton startButton = createRoundedButton("Start", 10, new Dimension(200, 60), new Color(72, 131, 180), Color.WHITE, 20);
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
        buttonPanel.setBackground(Color.LIGHT_GRAY); // Set background color to grey for the button panel
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.add(gameTimeDropdown);
        buttonPanel.add(startButton);
        buttonPanel.add(gameThemeDropdown);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 50, 0)); // Set bottom margin

        // Add vertical space between logo panel and button panel
        add(Box.createVerticalStrut(50), BorderLayout.CENTER);

        // Main Layout
        add(logoPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public static JButton createRoundedButton(String text, int radius, Dimension size, Color background, Color foreground, int fontSize) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setPreferredSize(size); // Set preferred size
        button.setFont(new Font(button.getFont().getName(), Font.PLAIN, fontSize)); // Set font size

        // Store the original background color
        Color originalBackground = background;

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLUE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalBackground);
            }
        });

        return button;
    }

    public static JComboBox<String> createRoundedComboBox(String[] items, int radius, Dimension size, Color background, Color foreground, int fontSize) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBackground(background);
        comboBox.setForeground(foreground);
        comboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                return super.createArrowButton();
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(comboBox.getBackground());
                g2d.fillRoundRect(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1, radius, radius); // Fill with rounded rectangle
                g2d.dispose();
            }

            @Override
            protected ComboPopup createPopup() {
                BasicComboPopup popup = new BasicComboPopup(comboBox) {
                    @Override
                    protected JScrollPane createScroller() {
                        JScrollPane scroller = super.createScroller();
                        scroller.setBorder(BorderFactory.createEmptyBorder()); // Remove border from the scroll pane
                        return scroller;
                    }
                };
                popup.setBorder(BorderFactory.createEmptyBorder()); // Remove border from the popup
                return popup;
            }
        });

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setOpaque(false); // Make the renderer transparent
                return label;
            }
        });

        // Store the original background color
        Color originalBackground = background;

        // Add hover effect
        comboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                comboBox.setBackground(Color.LIGHT_GRAY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                comboBox.setBackground(originalBackground);
            }
        });


        comboBox.setPreferredSize(size); // Set preferred size
        comboBox.setFont(new Font(comboBox.getFont().getName(), Font.PLAIN, fontSize)); // Set font size
        return comboBox;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Launch Screen Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new LaunchScreen(frame));
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true);
    }
}
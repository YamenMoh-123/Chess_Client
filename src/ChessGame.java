import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.net.InetAddress;

public class ChessGame extends JPanel {
    static int Game_Width = 800;
    static int Game_Height = 800;
    public static Socket socket;
    public static BufferedReader fromServer;
    public static PrintWriter toServer;
    public static String notification;
    public static void handleServerNotifications() {
        new Thread(() -> {
            try {
                while (true) {
                    // Listen for notifications from the server
                    notification = ChessGame.fromServer.readLine();
                    if (notification != null) {
                        // Logic to handle the notification
                        // For example, you can update the game state, display messages to the user, etc.
                        System.out.println("Received notification from server: " + notification.substring(8));
                        if (notification.length() > 10) {
                            System.out.println("this" + notification.charAt(4) + " " + notification.charAt(6));
                            int oldX = notification.charAt(0) - 97;
                            int oldY = 7 - (notification.charAt(2) - 49);
                            int x = notification.charAt(4) - 97;
                            int y = 7 - (notification.charAt(6) - 49);
                            ChessBoard.moveResponse(oldX, oldY, x, y);
                            Resources.playSound("Resources/Sounds/move-self.wav");
                        }
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Opponent disconnected.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        try{
            JTextField textField = new JTextField(20); // Create a text field
            JPanel panel = new JPanel();
            panel.add(new JLabel("Enter the address:"));
            panel.add(textField);
            int result = JOptionPane.showConfirmDialog(null, panel, "Enter Name", JOptionPane.OK_CANCEL_OPTION);
            String userInput = null;
            if (result == JOptionPane.OK_OPTION) {
                userInput = textField.getText(); // Get text from the text field
                if (!userInput.isEmpty()) {
                    InetAddress serverAddress = InetAddress.getByName(userInput);
                }
            }
            int serverPort = 1234;
            socket = new Socket(InetAddress.getByName(userInput), serverPort);
            fromServer = new BufferedReader(new InputStreamReader(ChessGame.socket.getInputStream()));
        } catch (
                IOException ioException) {
            ioException.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("CLIENT");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            LaunchScreen homeScreen = new LaunchScreen(frame);
            frame.setContentPane(homeScreen);

            handleServerNotifications(); // Add this line to start listening for server notifications

            frame.pack();
            frame.setSize(Game_Width, Game_Height);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }
}
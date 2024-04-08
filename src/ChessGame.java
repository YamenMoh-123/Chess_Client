import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.net.InetAddress;

public class ChessGame extends JPanel {
    static int Game_Width = 930;
    static int Game_Height = 830;
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
                        // check if the notifcation is a mate or draw, if so end the game
                        System.out.println("Received notification from server: " + notification);
                        if(notification.equals("Mate")) {
                            JOptionPane.showMessageDialog(null, "Checkmate! You win!", "Notice", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0); // Close the application
                        }
                        else if(notification.equals("Draw")) {
                            JOptionPane.showMessageDialog(null, "Draw! No winner.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0); // Close the application
                        }
                        else {
                            // Parse the notification and update the game state
                            String[] parts = notification.split(" ");
                            if (notification.length() > 10) {
                                int oldX = parts[0].charAt(0) - 97;
                                int oldY = 7 - (parts[1].charAt(0) - 49);
                                int x = parts[2].charAt(0) - 97;
                                int y = 7 - (parts[3].charAt(0) - 49);
                                String piece = parts[4];
                                boolean enPassant = Boolean.parseBoolean(parts[5]);
                                boolean enPassantHappened = Boolean.parseBoolean(parts[6]);
                                ChessBoard.moveResponse(oldX, oldY, x, y, piece, enPassant, enPassantHappened);
                                Resources.playSound("Resources/Sounds/move-self.wav");

                            }
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
            // Create a text field to get the server IP address
            JTextField textField = new JTextField(20);
            JPanel panel = new JPanel();
            panel.add(new JLabel("Enter the Serer IP Address:"));
            panel.add(textField);
            int result = JOptionPane.showConfirmDialog(null, panel, "Enter Name", JOptionPane.OK_CANCEL_OPTION);
            String userInput = null;
            if (result == JOptionPane.OK_OPTION) {
                // Get text from the text field
                userInput = textField.getText();
                if (!userInput.isEmpty()) {
                    // Convert the user input to an InetAddress object
                    InetAddress serverAddress = InetAddress.getByName(userInput);
                }
            }
            // Create a socket connection to the server
            InetAddress serverAddress = InetAddress.getByName(userInput);
            int serverPort = 1234;
            socket = new Socket(serverAddress, serverPort);
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
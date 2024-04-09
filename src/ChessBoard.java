import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public class ChessBoard extends JPanel {
    // Init Vars
    private static final int ROWS = 8;
    private static final int COLS = 8;
    public static JPanel bottomLabels = new JPanel(new GridLayout(1, COLS));
    public static JPanel sideLabels = new JPanel(new GridLayout(ROWS, 1));
    private static final int BOARD_SIZE = 820;
    private static final int FONT_SIZE = 16;
    private static final int PADDING_RIGHT = 0;

    public static String turn = "WHITE";
    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};
    public static boolean moved = true;

    private String[][] boardInit = {
            {"Rook", "Knight", "Bishop", "Queen", "Empty", "Bishop", "Knight", "Rook"},
            {"Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn"},
            {"Rook", "Knight", "Bishop", "Queen", "Empty", "Bishop", "Knight", "Rook"}
    };

    public static ChessSquare[][] chessBoard = new ChessSquare[ROWS][COLS];

    public static ChessSquare previousClickedTile = null;
    public static ArrayList<String> previousMoves = null;
    public static boolean movesShown = false;
    public static int whiteMin = Integer.parseInt(LaunchScreen.gameTime);
    public static int whiteSec = 0;
    public static int blackMin = Integer.parseInt(LaunchScreen.gameTime);
    public static int blackSec = 0;
    public static PieceObject whiteKing;
    public static PieceObject blackKing;
    private JLabel whiteTimerLabel;
    private JLabel blackTimerLabel;
    private JLabel turnLabel;
    private Timer timer;
    static boolean enPassantHappenedCheck = false;
    static boolean promoted =  false;

    private ActionListener pieceListener = new ActionListener() {

        // Create an action listener for the chess pieces
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the clicked square and its rotated counter part
            ChessSquare clickedSquare = (ChessSquare) e.getSource();
            ChessSquare flippedSquare = getFlippedSquare(clickedSquare);
            if (!moved) {
                if ((flippedSquare).getPiece() != null && (flippedSquare).getPiece().color == Color.BLACK) {
                    // If the user clicks on his own piece, highlight it and display the possible moves
                    movesShown = false;
                    resetTileColors();
                    previousClickedTile = flippedSquare;
                    clickedSquare.setBackground(new Color(205,209,106));
                    displayPossibleMoves(previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name));
                    previousMoves = previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name);
                    movesShown = true;

                }
                if(((flippedSquare).getPiece() == null || (flippedSquare).getPiece().color == Color.WHITE) && previousClickedTile != null){
                    // If the user then clicks on an empty tile or tries to take an enemy piece
                    if (previousClickedTile.getPiece() != null) {
                        resetTileColors();
                        previousClickedTile.setBackground(new Color(205,209,106));
                        displayPossibleMoves(previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name));
                        // try to move the piece
                        if(movePiece((flippedSquare).getName())) {
                            try {
                                // send a notification to the server of the mover made
                                ChessGame.toServer = new PrintWriter(ChessGame.socket.getOutputStream(), true);
                                ChessGame.toServer.flush();
                                if (previousMoves.contains((flippedSquare).getName()) || enPassantHappenedCheck) {
                                    String name = promotion(flippedSquare);
                                    // if the move is a castle move call a special method to send 2 notifications
                                    if(Objects.equals(name, "King") && Objects.equals(previousClickedTile.getName(), "e 8")){
                                        castle(flippedSquare);
                                    }
                                    // send the notification to the server
                                    ChessGame.toServer.println(previousClickedTile.getName() + " " + (flippedSquare).getName() + " " + name + " " + (flippedSquare).getPiece().EnPassantAble + " " + enPassantHappenedCheck);
                                    moved = true;
                                }
                            } catch (IOException ioException) {
                                JOptionPane.showMessageDialog(null, "This is a message dialog without a parent frame.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                                ioException.printStackTrace();

                            }
                        }
                        turn = "WHITE";
                    }

                }
                if (moved){
                    // If the move was successful, reset the board and switch the turn
                    previousClickedTile = null;
                    movesShown = false;
                    Resources.playSound("Resources/Sounds/move-self.wav");
                }
            }
        }
    };

    // Function that moves the piece and returns a boolean if the move was successful
    public boolean movePiece(String name) {
        // Parse the X and Y coordinates of the move
        int x = name.charAt(0) - 97;
        int y = 7 - (name.charAt(2) - 49);
        // Check if the move is inside the arrayList of valid moves
        ArrayList<String> temp = previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name);
        if (temp.contains(name) || temp.contains( name + " wr") || temp.contains( name + " wl")) {
            // If the move is valid, check if the tile has a piece and remove it`
            if (chessBoard[y][x].getPiece() != null) {
                GameCanvas.gameManager.removeGameObject(chessBoard[y][x].getPiece());
                Resources.playSound("Resources/Sounds/capture.wav");
            }
            // If the move is an en passant move, remove the piece that was taken
            if (temp.contains(name + " wr") || temp.contains(name + " wl")) {
                GameCanvas.gameManager.removeGameObject(chessBoard[y - 1][x].getPiece());
                Resources.playSound("Resources/Sounds/capture.wav");
                enPassantHappenedCheck = true;
            }
            // Remove the piece from the previous tile and add it to the new tile
            GameCanvas.gameManager.removeGameObject(previousClickedTile.getPiece());
            doMove(y, x);
            unEnpassant(y, x);
            previousClickedTile.setPiece(null);
            resetTileColors();
            switchTurn();

        }
        return true;
    }

    // Move the enemy piece based off the notification from the client
    public static void moveResponse(int oldx, int oldy, int x, int y, String name, boolean enPassant, boolean enPassantHappened) {

        if (chessBoard[y][x].getPiece() != null) {
            // Do the move
            GameCanvas.gameManager.removeGameObject(chessBoard[y][x].getPiece());
            chessBoard[y][x].setPiece(null);
        }
        if (enPassantHappened) {
            // Remove the piece that was taken in the en passant move
            GameCanvas.gameManager.removeGameObject(chessBoard[y + 1][x].getPiece());
            chessBoard[y + 1][x].setPiece(null);
            enPassantHappenedCheck = false;
        }
        // Remove the piece from the old tile and add it to the new tile
        GameCanvas.gameManager.removeGameObject(chessBoard[oldy][oldx].getPiece());
        chessBoard[oldy][oldx].setPiece(null);
        PieceObject piece = new PieceObject(name, Color.WHITE, chessBoard[y][x].getPos()[0], chessBoard[y][x].getPos()[1], enPassant);
        chessBoard[y][x].setPiece(piece);
        GameCanvas.gameManager.addGameObject(piece);
        moved = false;
        switchTurn();

        // Check for checkMate and StaleMate and display a message
        if (!blackKing.hasAvailableMoves()) {
            if(blackKing.isKingChecked()){
                ChessGame.toServer.println("Mate");
                JOptionPane.showMessageDialog( null, "You Lost\nCheck Mate!\n",
                        "Notice", JOptionPane.ERROR_MESSAGE);
                System.exit(0); // Close the application
            }
            else{
                ChessGame.toServer.println("Draw");
                JOptionPane.showMessageDialog(null, "DRAW\nStale Mate!\n",
                        "Notice", JOptionPane.ERROR_MESSAGE);
                System.exit(0); // Close the application
            }
        }

    }

   // Function that sends a notification to the client if the move is a castle move
    public void castle(ChessSquare square){
        if(Objects.equals((square).getName(), "c 8")){
            ChessGame.toServer.println("d 1" + " " + "d 8" + " " + "Rook" + " " + (square).getPiece().EnPassantAble + " " + enPassantHappenedCheck);
        }
        else if(Objects.equals((square).getName(), "g 1")){
            ChessGame.toServer.println("h 8" + " " + "f 8" + " " + "Rook" + " " + (square).getPiece().EnPassantAble + " " + enPassantHappenedCheck);
        }
    }

    // Function that checks if the move is a castle move and moves the rook
    public void checkCastle(int y, int x) {
        // Check for short castle and move the rook
        if(Objects.equals(previousClickedTile.getName(), "e 8") && Objects.equals(chessBoard[y][x].getName(), "c 8")){
            GameCanvas.gameManager.removeGameObject(chessBoard[0][0].getPiece());
            ChessBoard.chessBoard[0][0].setPiece(null);
            PieceObject rook = new PieceObject("Rook", Color.BLACK, chessBoard[0][3].getPos()[0], chessBoard[0][3].getPos()[1] , false);
            GameCanvas.gameManager.addGameObject(rook);
            ChessBoard.chessBoard[0][3].setPiece(rook);

        }
        // Check for long castle and move the rook
        else if(Objects.equals(previousClickedTile.getName(), "e 8") && Objects.equals(chessBoard[y][x].getName(), "g 8")){
            GameCanvas.gameManager.removeGameObject(chessBoard[0][7].getPiece());
            ChessBoard.chessBoard[0][7].setPiece(null);
            PieceObject rook = new PieceObject("Rook", Color.BLACK, chessBoard[0][5].getPos()[0], chessBoard[0][5].getPos()[1] , false);
            GameCanvas.gameManager.addGameObject(rook);
            ChessBoard.chessBoard[0][5].setPiece(rook);

        }
    }

    // Check if the pawn has reached the end of the board and promote it to a queen
    public String promotion(ChessSquare square){
        if(promoted){
            promoted = false;
            return "Queen";
        }
        return square.getPiece().name;
    }

    // Function that moves the piece to the new tile
    public void doMove(int y, int x) {
        // Move the King by removing the old piece and adding a new one
        if (previousClickedTile.getPiece().name.equals("King")) {
            if (previousClickedTile.getPiece().color == Color.WHITE) {
                whiteKing = new KingObject(chessBoard[y][x].getPos()[0], chessBoard[y][x].getPos()[1], y, x, Color.WHITE);
                GameCanvas.gameManager.addGameObject(whiteKing);
                chessBoard[y][x].setPiece(whiteKing);
            } else {
                blackKing = new KingObject(chessBoard[y][x].getPos()[0], chessBoard[y][x].getPos()[1], y, x, Color.BLACK);
                // Check if the move is a castle move
                checkCastle(y, x);
                blackKing.hasMoved = true;
                GameCanvas.gameManager.addGameObject(blackKing);
                chessBoard[y][x].setPiece(blackKing);
            }
        }
        else {
            // If the move wasnt the king then move the piece and check if it was a promotion
            PieceObject piece;
            if (Objects.equals(previousClickedTile.getPiece().name, "Pawn") && y == 7) {
                promoted = true;
                piece = new PieceObject("Queen", previousClickedTile.getPiece().color, chessBoard[y][x].getPos()[0], chessBoard[y][x].getPos()[1], previousClickedTile.getPiece().EnPassantAble);
            } else {
                piece = new PieceObject(previousClickedTile.getPiece().name, previousClickedTile.getPiece().color, chessBoard[y][x].getPos()[0], chessBoard[y][x].getPos()[1], previousClickedTile.getPiece().EnPassantAble);
            }
            piece.hasMoved = true;
            chessBoard[y][x].setPiece(piece);
            GameCanvas.gameManager.addGameObject(piece);
        }
    }

    // Function that displays the possible moves for the piece
    public void displayPossibleMoves(ArrayList<String> moves) {
        for (String move : moves) {
            ChessSquare square = getFlippedSquare(chessBoard[7 - (move.charAt(2) - 49)][(move.charAt(0) - 97)]);

            if (chessBoard[7 - (move.charAt(2) - 49)][(move.charAt(0) - 97)].getPiece() != null && chessBoard[7 - (move.charAt(2) - 49)][(move.charAt(0) - 97)].getPiece().color != Color.BLACK) {
                square.setBackground(new Color(129,150,105));
            }
        }
    }

    // Function that returns the flipped square
    private ChessSquare getFlippedSquare(ChessSquare clickedSquare) {
        System.out.println(clickedSquare.getName() + " name");

        int column = clickedSquare.getName().charAt(0) - 97;
        int row = 8 - (clickedSquare.getName().charAt(2) - 48);

        System.out.println(column + " " + row);
        return chessBoard[7-row][7-column];
    }

   // Function that removes the en passant ability from the enemy pieces
    private void unEnpassant(int y, int x){
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (chessBoard[row][col].getPiece() != null && chessBoard[row][col] != chessBoard[y][x]){
                    chessBoard[row][col].getPiece().EnPassantAble = false;
                }
            }
        }
    }

    // Function that switches the turn
    private static void switchTurn() {
        if (turn.equals("WHITE")) {
            turn = "BLACK";
        } else {
            turn = "WHITE";
        }
    }

    // Function that resets the tile colors after hovering
    public void resetTileColors() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if ((row + col) % 2 == 0) {
                    chessBoard[row][col].setBackground(new Color(234, 234, 228));
                } else {
                    chessBoard[row][col].setBackground(LaunchScreen.gameColor);
                }
            }
        }
    }

    // Constructor
    public ChessBoard() {
        // Set the layout and create the board
        setLayout(new BorderLayout());
        JPanel boardPanel = new JPanel(new GridLayout(ROWS, COLS));

        JPanel timerPanel = new JPanel(new GridLayout(3, 1));
        Font labelFont = new Font("SansSerif", Font.BOLD, FONT_SIZE);

        whiteTimerLabel = new JLabel("White: " + whiteMin + ":" + String.format("%02d", whiteSec));
        blackTimerLabel = new JLabel("Black: " + blackMin + ":" + String.format("%02d", blackSec));
        turnLabel = new JLabel("Turn: W" );
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the timer every second
                if (turn.equals("BLACK")) {
                    if (blackSec == 0) {
                        blackMin--;
                        blackSec = 59;
                    } else {
                        blackSec--;
                        if (blackMin == 0 && blackSec == 0){
                            // If the time runs out, display a message and close the application
                            timer.stop();
                            JOptionPane.showMessageDialog(ChessBoard.this, "WHITE WINS\nYou lost by time.\n",
                                    "Notice", JOptionPane.ERROR_MESSAGE);
                            System.exit(0); // Close the application
                            return;
                        }
                    }
                } else {
                    if (whiteSec == 0) {
                        whiteMin--;
                        whiteSec = 59;
                    } else {
                        whiteSec--;
                        if (whiteMin == 0 && whiteSec == 0){
                            // If the time runs out, display a message and close the application
                            timer.stop();
                            JOptionPane.showMessageDialog(ChessBoard.this, "BLACK WINS\nYou won by time!\n",
                                    "Notice", JOptionPane.ERROR_MESSAGE);
                            System.exit(0); // Close the application
                            return;
                        }
                    }
                }
                whiteTimerLabel.setText("White: " + whiteMin + ":" + String.format("%02d", whiteSec));
                blackTimerLabel.setText("Black: " + blackMin + ":" + String.format("%02d", blackSec));
                // Display the Turn
                if(turn.equals("WHITE")){
                    turnLabel.setText("Turn: W");}
                else{
                    turnLabel.setText("Turn: B");
                }          }
        });
        timer.start();

        // Create the chess board
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                // Create a new ChessSquare object and add it to the board
                ChessSquare currButton = new ChessSquare();
                currButton.setName(colNames[col] + " " + (ROWS - row));
                currButton.setBackground(LaunchScreen.gameColor);
                currButton.setBorder(new EmptyBorder(0, 0, 0, 0));
                currButton.setPos(col * (BOARD_SIZE / COLS), row * (BOARD_SIZE / ROWS));

                // Set the background color of the tiles
                if ((row + col) % 2 == 0) {
                    currButton.setBackground(new Color(234, 234, 228));
                }

                currButton.setOpaque(true);
                currButton.addActionListener(pieceListener);
                chessBoard[row][col] = currButton;
                boardPanel.add(chessBoard[row][col]);
            }

            // Add an action listener to the buttons
            JLabel sideLabel = new JLabel(String.valueOf( row+1));
            sideLabel.setHorizontalAlignment(JLabel.CENTER);
            sideLabel.setFont(labelFont);
            sideLabel.setBorder(new EmptyBorder(0, 0, PADDING_RIGHT, 0));
            sideLabels.add(sideLabel);
        }

        // Create the labels
        for (String colName : colNames) {
            char reversedColName = (char) ('a' + 'h' - colName.charAt(0));
            JLabel bottomLabel = new JLabel(String.valueOf(reversedColName));
            bottomLabel.setHorizontalAlignment(JLabel.CENTER);
            bottomLabel.setFont(labelFont);
            bottomLabels.add(bottomLabel);
        }
        bottomLabels.add(new JLabel(""));
        timerPanel.setBackground(Color.LIGHT_GRAY);

        // Set font and colors for white timer label
        whiteTimerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        whiteTimerLabel.setForeground(Color.BLACK); // Text color
        whiteTimerLabel.setBackground(Color.WHITE); // Background color
        whiteTimerLabel.setOpaque(true); // Make background color visible

        // Set font and colors for black timer label
        blackTimerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        blackTimerLabel.setForeground(Color.WHITE); // Text color
        blackTimerLabel.setBackground(Color.BLACK); // Background color
        blackTimerLabel.setOpaque(true); // Make background color visible

        // Set font and colors for turn label
        turnLabel.setFont(new Font("Arial", Font.BOLD, 16));
        turnLabel.setForeground(Color.BLACK); // Text color
        turnLabel.setBackground(Color.LIGHT_GRAY); // Background color
        turnLabel.setOpaque(true); // Make background color visible

        timerPanel.add(whiteTimerLabel);
        timerPanel.add(turnLabel);
        timerPanel.add(blackTimerLabel);

        // Set the preferred size of the labels
        whiteTimerLabel.setPreferredSize(new Dimension(100, 30));
        turnLabel.setPreferredSize(new Dimension(100, 30));
        blackTimerLabel.setPreferredSize(new Dimension(100, 30));
        bottomLabels.setPreferredSize(new Dimension(BOARD_SIZE, 30));
        sideLabels.setPreferredSize(new Dimension(30, BOARD_SIZE));
        boardPanel.setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));

        // Add the panels to the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(bottomLabels, BorderLayout.SOUTH);
        mainPanel.add(sideLabels, BorderLayout.WEST);
        mainPanel.add(timerPanel, BorderLayout.EAST);

        add(mainPanel);
        initBoard();

    }

    public void initBoard() {
        // Add the pieces to the board
        for (int row = 0; row < COLS; row++) {
            for (int col = 0; col < ROWS; col++) {
                if (!boardInit[row][col].equals("Empty")) {

                    Color color = (row > 3) ? Color.WHITE : Color.BLACK;
                    int[] pos = chessBoard[row][col].getPos();
                    System.out.println(pos[0] + " " + pos[1]);
                    PieceObject piece = new PieceObject(boardInit[row][col], color, pos[0] ,  pos[1] -  (row*2), false);
                    chessBoard[row][col].setPiece(piece);
                    GameCanvas.gameManager.addGameObject(piece);
                }
            }
        }

        // Add the kings to the board
        whiteKing = new KingObject(chessBoard[7][4].getPos()[0], chessBoard[7][4].getPos()[1], 7 , 4, Color.WHITE);
        blackKing = new KingObject(chessBoard[0][4].getPos()[0], chessBoard[0][4].getPos()[1], 0 , 4, Color.BLACK);
        GameCanvas.gameManager.addGameObject(whiteKing);
        GameCanvas.gameManager.addGameObject(blackKing);
        chessBoard[7][4].setPiece(whiteKing);
        chessBoard[0][4].setPiece(blackKing);
    }
}

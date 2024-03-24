import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ChessBoard extends JPanel {

    private static final int ROWS = 8;
    private static final int COLS = 8;
    private static final int BOARD_SIZE = 800;
    private static final int FONT_SIZE = 16;
    private static final int PADDING_RIGHT = 10;
    private static int turn = 0;
    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};
    public static boolean moved = false;

    private String[][] boardInit = {
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Queen", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Knight", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"}
    };

    public static ChessSquare[][] chessBoard = new ChessSquare[ROWS][COLS];

    private ChessSquare previousClickedTile = null;
    private Color previousTileColor = null;

    JButton endButton = new JButton("End");

    private ActionListener endListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(moved);
            if (moved) {
                System.out.println(moved);
                try {
                    ChessGame.toClient = new PrintWriter(ChessGame.clientSocket.getOutputStream(), true);
                    ChessGame.toClient.println("YOUR TURN"); // Notify the client it's their turn
                    String messageFromClient = ChessGame.fromClient.readLine();
                    System.out.println(turn + " client sent: " + messageFromClient);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            moved = false;
        }
    };

    private ActionListener pieceListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(moved);
            if (!moved) {
                if (previousClickedTile == null) {
                    previousClickedTile = (ChessSquare) e.getSource();
                    previousTileColor = previousClickedTile.getBackground();
                    previousClickedTile.setBackground(Color.RED);
                } else {
                    previousClickedTile.setBackground(previousTileColor);
                    if (previousClickedTile.getPiece() != null) {
                        movePiece(((ChessSquare) e.getSource()).getName());
                        try {
                            ChessGame.toClient = new PrintWriter(ChessGame.clientSocket.getOutputStream(), true);
                            ChessGame.toClient.flush();
                            ChessGame.toClient.println(previousClickedTile.getName() + " " + ((ChessSquare) e.getSource()).getName());
                            moved = true;
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    }

                }
                if (previousClickedTile.getPiece() != null) {
                    displayPossibleMoves(previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name));
                    previousTileColor = previousClickedTile.getBackground();
                    previousClickedTile.setBackground(Color.RED);
                } else {
                    previousClickedTile = (ChessSquare) e.getSource();
                    resetTileColors();
                }
                if (moved){
                    previousClickedTile = null;
                }
            }
        }
    };

    public void displayPossibleMoves(ArrayList<String> moves) {
        for (String move : moves) {
            chessBoard[7 - (move.charAt(2) - 49)][(move.charAt(0) - 97)].setBackground(Color.GREEN);
        }
    }

    public void movePiece(String name) {
        int x = name.charAt(0) - 97;
        int y = 7 - (name.charAt(2) - 49);
        System.out.println("Going to " + name);
        System.out.println(x);
        System.out.println(y);
        System.out.println(ChessBoard.chessBoard[y][x]);
        if (previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name).contains(name)) {
            GameCanvas.gameManager.removeGameObject(previousClickedTile.getPiece());
            PieceObject piece = new PieceObject(previousClickedTile.getPiece().name, previousClickedTile.getPiece().color, chessBoard[y][x].getPos()[0], chessBoard[y][x].getPos()[1]);
            chessBoard[y][x].setPiece(piece);
            GameCanvas.gameManager.addGameObject(piece);
            previousClickedTile.setPiece(null);
        }
    }

    public void resetTileColors() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if ((row + col) % 2 == 0) {
                    chessBoard[row][col].setBackground(Color.WHITE);
                } else {
                    chessBoard[row][col].setBackground(Color.BLACK);
                }
            }
        }
    }

    public ChessBoard() {
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        JPanel bottomLabels = new JPanel(new GridLayout(1, COLS));
        JPanel sideLabels = new JPanel(new GridLayout(ROWS, 1));

        Font labelFont = new Font("SansSerif", Font.BOLD, FONT_SIZE);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                ChessSquare currButton = new ChessSquare();
                currButton.setName(colNames[col] + " " + (ROWS - row));
                currButton.setBackground(Color.BLACK);
                currButton.setBorder(new EmptyBorder(0, 0, 0, 0));
                currButton.setPos(col * (BOARD_SIZE / COLS), row * (BOARD_SIZE / ROWS));

                if ((row + col) % 2 == 0) {
                    currButton.setBackground(Color.WHITE);
                }

                currButton.setOpaque(true);
                currButton.addActionListener(pieceListener);
                chessBoard[row][col] = currButton;
                boardPanel.add(chessBoard[row][col]);
            }
            JLabel sideLabel = new JLabel(String.valueOf(ROWS - row));
            sideLabel.setHorizontalAlignment(JLabel.CENTER);
            sideLabel.setFont(labelFont);
            sideLabel.setBorder(new EmptyBorder(0, 0, PADDING_RIGHT, 0));
            sideLabels.add(sideLabel);
        }
        for (String colName : colNames) {
            JLabel bottomLabel = new JLabel(colName);
            bottomLabel.setHorizontalAlignment(JLabel.CENTER);
            bottomLabel.setFont(labelFont);
            bottomLabels.add(bottomLabel);
        }
        add(boardPanel, BorderLayout.CENTER);
        add(bottomLabels, BorderLayout.SOUTH);
        add(sideLabels, BorderLayout.WEST);
        endButton.addActionListener(endListener);
        add(endButton, BorderLayout.EAST);
        initBoard();

    }

    public void initBoard() {
        for (int row = 0; row < COLS; row++) {
            for (int col = 0; col < ROWS; col++) {
                int[] pos = chessBoard[row][col].getPos();
                PieceObject piece = new PieceObject(boardInit[row][col], Color.BLACK, pos[0], pos[1]);
                chessBoard[row][col].setPiece(piece);
                GameCanvas.gameManager.addGameObject(piece);
            }
        }
    }
}

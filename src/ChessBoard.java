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
    public static String turn = "WHITE";
    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};
    public static boolean moved = true;
    private String[][] boardInit = {
            {"King", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Rook", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Queen", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Knight", "Empty"},
            {"Empty", "Pawn", "Empty", "Empty", "Empty", "Bishop", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"}
    };

    public static ChessSquare[][] chessBoard = new ChessSquare[ROWS][COLS];

    private ChessSquare previousClickedTile = null;
    private Color previousTileColor = null;

    public static int whiteMin = Integer.parseInt(LaunchScreen.gameTime); // Initial minutes
    public static int whiteSec = 0; // Initial seconds
    public static int blackMin = Integer.parseInt(LaunchScreen.gameTime); // Initial minutes
    public static int blackSec = 0; // Initial seconds

    public static JLabel statusLabel;
    private Timer timer;

    private ActionListener pieceListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!moved) {
                if (previousClickedTile == null) {
                    if (((ChessSquare) e.getSource()).getPiece() != null && ((ChessSquare) e.getSource()).getPiece().color == Color.BLACK) {
                        previousClickedTile = (ChessSquare) e.getSource();
                        previousTileColor = previousClickedTile.getBackground();
                        previousClickedTile.setBackground(Color.RED);
                    }
                } else {
                    ((ChessSquare) e.getSource()).setBackground(previousTileColor);
                    if (previousClickedTile.getPiece() != null) {
                        movePiece(((ChessSquare) e.getSource()).getName());
                        try {
                            ChessGame.toServer = new PrintWriter(ChessGame.socket.getOutputStream(), true);
                            ChessGame.toServer.flush();
                            ChessGame.toServer.println(previousClickedTile.getName() + " " + ((ChessSquare) e.getSource()).getName() + " " +((ChessSquare) e.getSource()).getPiece().name);
                            moved = true;
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        turn = "WHITE";
                    }

                }
                if (previousClickedTile.getPiece() != null && previousClickedTile.getPiece().color == Color.BLACK) {
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
        if (previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name).contains(name)) {
            GameCanvas.gameManager.removeGameObject(previousClickedTile.getPiece());
            PieceObject piece = new PieceObject(previousClickedTile.getPiece().name, previousClickedTile.getPiece().color, chessBoard[y][x].getPos()[0], chessBoard[y][x].getPos()[1]);
            chessBoard[y][x].setPiece(piece);
            GameCanvas.gameManager.addGameObject(piece);
            previousClickedTile.setPiece(null);
            switchTurn();
        }
    }

    private void switchTurn() {
        if (turn.equals("WHITE")) {
            turn = "BLACK";
        } else {
            turn = "WHITE";
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
        statusLabel = new JLabel(turn + " | White: " + whiteMin + ":" + String.format("%02d", whiteSec) + " | Black: " + blackMin + ":" + String.format("%02d", blackSec));
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(turn);
                if (turn.equals("BLACK")) {
                    if (blackSec == 0) {
                        blackMin--;
                        blackSec = 59;
                    } else {
                        blackSec--;
                        if (blackMin == 0 && blackSec == 0){
                            timer.stop();
                            JOptionPane.showMessageDialog(ChessBoard.this, "You lost by time.\n",
                                    "WHITE WINS", JOptionPane.ERROR_MESSAGE);
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
                            timer.stop();
                            JOptionPane.showMessageDialog(ChessBoard.this, "You won by time!\n",
                                    "BLACK WINS", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
                statusLabel.setText(turn + " | White: " + whiteMin + ":" + String.format("%02d", whiteSec) + " | Black: " + blackMin + ":" + String.format("%02d", blackSec));
            }
        });
        timer.start();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                ChessSquare currButton = new ChessSquare();
                currButton.setName(colNames[col] + " " + (ROWS - row));
                currButton.setBackground(LaunchScreen.gameColor);
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
        add(statusLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
        add(bottomLabels, BorderLayout.SOUTH);
        add(sideLabels, BorderLayout.WEST);


        initBoard();

    }

    public void initBoard() {
        for (int row = 0; row < COLS; row++) {
            for (int col = 0; col < ROWS; col++) {
                if (!boardInit[row][col].equals("Empty")) {
                    Color color = (row > 3) ? Color.BLACK : Color.WHITE;
                    int[] pos = chessBoard[row][col].getPos();
                    PieceObject piece = new PieceObject(boardInit[row][col], color, pos[0], pos[1]);
                    chessBoard[row][col].setPiece(piece);
                    GameCanvas.gameManager.addGameObject(piece);
                }
            }
        }
    }
}

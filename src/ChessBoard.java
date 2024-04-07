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

    private ChessSquare previousClickedTile = null;
    private Color previousTileColor = null;
    public static int whiteMin = Integer.parseInt(LaunchScreen.gameTime); // Initial minutes
    public static int whiteSec = 0; // Initial seconds
    public static int blackMin = Integer.parseInt(LaunchScreen.gameTime); // Initial minutes
    public static int blackSec = 0; // Initial seconds

    // static white and black king piece objects
    public static PieceObject whiteKing;
    public static PieceObject blackKing;

    public static JLabel statusLabel;
    private Timer timer;
    static boolean enPassantHappenedCheck = false;

    private ActionListener pieceListener = new ActionListener() {


        // try catch block works for moving pieces initially, clicking on a piece then clicking on an empty square attempts to
        // init the previous clicked tile as well as attempt to move a piece without a check. i think^ try to use previous clicked tile when possible
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!moved) {
                if (((ChessSquare) e.getSource()).getPiece() != null && ((ChessSquare) e.getSource()).getPiece().color == Color.BLACK) {
                    resetTileColors();
                    displayPossibleMoves(((ChessSquare) e.getSource()).getPiece().validMoves(((ChessSquare) e.getSource()).getName(), ((ChessSquare) e.getSource()).getPiece().name));
                    previousTileColor = ((ChessSquare) e.getSource()).getBackground();
                    previousClickedTile = (ChessSquare) e.getSource();
                    previousTileColor = previousClickedTile.getBackground();
                    previousClickedTile.setBackground(Color.RED);
                }
                if((((ChessSquare) e.getSource()).getPiece() == null || ((ChessSquare) e.getSource()).getPiece().color == Color.WHITE) && previousClickedTile != null){

                    if (previousClickedTile.getPiece() != null) {
                        resetTileColors();
                        previousClickedTile.setBackground(Color.RED);
                        displayPossibleMoves(previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name));
                        movePiece(((ChessSquare) e.getSource()).getName());
                        try {
                            ChessGame.toServer = new PrintWriter(ChessGame.socket.getOutputStream(), true);
                            ChessGame.toServer.flush();
                            if (((ChessSquare) e.getSource()).getPiece() != null) {
                                ChessGame.toServer.println(previousClickedTile.getName() + " " + ((ChessSquare) e.getSource()).getName() + " " + ((ChessSquare) e.getSource()).getPiece().name + " " + ((ChessSquare) e.getSource()).getPiece().EnPassantAble + " " + enPassantHappenedCheck);
                                moved = true;
                            }
                        } catch (IOException ioException) {
                            JOptionPane.showMessageDialog(null, "This is a message dialog without a parent frame.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                            ioException.printStackTrace();
                        }
                        turn = "WHITE";
                    }

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
        System.out.println(x + " " + y);
        ArrayList<String> temp = previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name);
        if (temp.contains(name) || temp.contains( name + " wr") || temp.contains( name + " wl")) {

            if (chessBoard[y][x].getPiece() != null ) {
                GameCanvas.gameManager.removeGameObject(chessBoard[y][x].getPiece());
            }
            if(temp.contains(name + " wr") || temp.contains( name + " wl")) {
                GameCanvas.gameManager.removeGameObject(chessBoard[y-1][x].getPiece());
                enPassantHappenedCheck = true;
            }

            GameCanvas.gameManager.removeGameObject(previousClickedTile.getPiece());

            if(previousClickedTile.getPiece().name.equals("King")){
                if(previousClickedTile.getPiece().color == Color.WHITE){
                    whiteKing = new KingObject(chessBoard[y][x].getPos()[0], chessBoard[y][x].getPos()[1], y, x, Color.WHITE);
                    GameCanvas.gameManager.addGameObject(whiteKing);
                    chessBoard[y][x].setPiece(whiteKing);
                }else{
                    blackKing = new KingObject(chessBoard[y][x].getPos()[0], chessBoard[y][x].getPos()[1], y, x, Color.BLACK);
                    GameCanvas.gameManager.addGameObject(blackKing);
                    chessBoard[y][x].setPiece(blackKing);
                }
            }
            else {
                PieceObject piece = new PieceObject(previousClickedTile.getPiece().name, previousClickedTile.getPiece().color, chessBoard[y][x].getPos()[0], chessBoard[y][x].getPos()[1],  previousClickedTile.getPiece().EnPassantAble);
                chessBoard[y][x].setPiece(piece);
                GameCanvas.gameManager.addGameObject(piece);
            }
            unEnpassant(y, x);
            previousClickedTile.setPiece(null);
            resetTileColors();
            switchTurn();
        }
    }


    public static void moveResponse(int oldx, int oldy, int x, int y, boolean enPassant, boolean enPassantHappened){
        if (chessBoard[y][x].getPiece() != null) {
            GameCanvas.gameManager.removeGameObject(chessBoard[y][x].getPiece());
        }
        if(enPassantHappened){
            GameCanvas.gameManager.removeGameObject(chessBoard[y-1][x].getPiece());
            enPassantHappenedCheck = false;
        }
        String name = chessBoard[oldy][oldx].getPiece().name;
        GameCanvas.gameManager.removeGameObject(chessBoard[oldy][oldx].getPiece());
        chessBoard[oldy][oldx].setPiece(null);
        PieceObject piece = new PieceObject(name, Color.WHITE, chessBoard[y][x].getPos()[0], chessBoard[y][x].getPos()[1], enPassant);
        chessBoard[y][x].setPiece(piece);
        GameCanvas.gameManager.addGameObject(piece);

        moved = false;
        switchTurn();
    }

    private void unEnpassant(int y, int x){
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (chessBoard[row][col].getPiece() != null && chessBoard[row][col] != chessBoard[y][x]){
                    chessBoard[row][col].getPiece().EnPassantAble = false;
                }
            }
        }
    }



    private static void switchTurn() {
        if (turn.equals("WHITE")) {
            turn = "BLACK";
        } else {
            turn = "WHITE";
        }
    }

    // modify with selected colors
    public void resetTileColors() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if ((row + col) % 2 == 0) {
                    chessBoard[row][col].setBackground(Color.WHITE);
                } else {
                    chessBoard[row][col].setBackground(Color.GRAY);
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
                if (turn.equals("BLACK")) {
                    if (blackSec == 0) {
                        blackMin--;
                        blackSec = 59;
                    } else {
                        blackSec--;
                        if (blackMin == 0 && blackSec == 0){
                            timer.stop();
                            JOptionPane.showMessageDialog(ChessBoard.this, "WHITE WINS\nYou lost by time.\n",
                                    "Notice", JOptionPane.ERROR_MESSAGE);
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
                            JOptionPane.showMessageDialog(ChessBoard.this, "BLACK WINS\nYou won by time!\n",
                                    "Notice", JOptionPane.ERROR_MESSAGE);
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
                    Color color = (row > 3) ? Color.WHITE : Color.BLACK;
                    int[] pos = chessBoard[row][col].getPos();
                    PieceObject piece = new PieceObject(boardInit[row][col], color, pos[0], pos[1], false);
                    chessBoard[row][col].setPiece(piece);
                    GameCanvas.gameManager.addGameObject(piece);
                }
            }
        }
        whiteKing = new KingObject(chessBoard[7][4].getPos()[0], chessBoard[7][4].getPos()[1], 7, 4, Color.WHITE);
        blackKing = new KingObject(chessBoard[0][4].getPos()[0], chessBoard[0][4].getPos()[1], 0, 4, Color.BLACK);
        GameCanvas.gameManager.addGameObject(whiteKing);
        GameCanvas.gameManager.addGameObject(blackKing);
        chessBoard[7][4].setPiece(whiteKing);
        chessBoard[0][4].setPiece(blackKing);
    }
}

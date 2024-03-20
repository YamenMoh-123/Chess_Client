import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Objects;
public class ChessBoard extends JPanel {

    private static final int ROWS = 8;
    private static final int COLS = 8;
    private static final int BOARD_SIZE = 800;
    private static final int FONT_SIZE = 16;
    private static final int PADDING_RIGHT = 10;
    private static String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};

   /* private String[][] boardInit = {
            {"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"},
            {"Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty", "Empty"},
            {"Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn", "Pawn"},
            {"Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight", "Rook"},
    };

    */

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
 //   public static GameManager gameManager = new GameManager();

    // action listener for the buttons
     private ActionListener pieceListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            //System.out.println(((JButton) e.getSource()).getName());


            if(previousClickedTile == null){
                previousClickedTile = (ChessSquare) e.getSource();
                previousTileColor = previousClickedTile.getBackground();
                previousClickedTile.setBackground(Color.RED);
            }

            else {
                previousClickedTile.setBackground(previousTileColor);
                if(previousClickedTile.getPiece() != null ){
                    // from - to
                    movePiece(((ChessSquare) e.getSource()).getName());
                }
                previousClickedTile = (ChessSquare) e.getSource();
                previousTileColor = previousClickedTile.getBackground();
                previousClickedTile.setBackground(Color.RED);
                resetTileColors();

                //System.out.println(previousClickedTile.getName());
            }
            // case for clicking on take can merge ^^
            if(previousClickedTile.getPiece() != null){
                displayPossibleMoves(previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name));
            }






           // System.out.println(previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name));

            // set piece function takes in current button position
            // call piece.validateMove()
            // piece calls its own validateMove function, returns a list of possible moves (taking / vs movement for pawn).
            // this checks if the move is valid. obstructed, can take
            // for checks. the piece is allowed to move on the backend, isCheck is called and returns true, the move is invalid
        }
    };

     // function to display possible moves
    public void displayPossibleMoves(ArrayList<String> moves){
        for (String move : moves){
            chessBoard[7-(move.charAt(2)-49)][(move.charAt(0)-97)].setBackground(Color.GREEN);
        }
    }

    public void movePiece(String name){
        int x = name.charAt(0) - 97;
        int y = 7 - (name.charAt(2) - 49);
        System.out.println("Going to " + name);
        if(previousClickedTile.getPiece().validMoves(previousClickedTile.getName(), previousClickedTile.getPiece().name).contains(name)){

            GameCanvas.gameManager.removeGameObject(previousClickedTile.getPiece());
            PieceObject piece = new PieceObject(previousClickedTile.getPiece().name, previousClickedTile.getPiece().color, chessBoard[y][x].getPos()[0], chessBoard[y][x].getPos()[1]);
            chessBoard[y][x].setPiece(piece);
            GameCanvas.gameManager.addGameObject(piece);
            previousClickedTile.setPiece(null);



        }

    }

    public void resetTileColors(){
        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLS; col++){
                if((row + col) % 2 == 0){
                    chessBoard[row][col].setBackground(Color.WHITE);
                }
                else{
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

                currButton.setName(colNames[col] + " " + (ROWS-row));
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
            initBoard();

    }

    public void initBoard(){
        for(int row = 0; row < COLS; row ++){
            for(int col = 0; col < ROWS; col++){
                if(!boardInit[row][col].equals("Empty")){
                    int []pos = chessBoard[row][col].getPos();
                    PieceObject piece = new PieceObject(boardInit[row][col], Color.BLACK, pos[0], pos[1]);
                    chessBoard[row][col].setPiece(piece);
                    GameCanvas.gameManager.addGameObject(piece);
            }
            }

        }

    }

}

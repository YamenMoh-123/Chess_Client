import javax.swing.*;

public class ChessGame extends JPanel{
    private static int Game_Width = 800;
    private static int Game_Height = 800;


    public ChessGame(){
        JPanel mainPanel = new JPanel();
        mainPanel.setSize(650, 650);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChessBoard chessBoard = new ChessBoard();
        frame.add(chessBoard);
        //frame.add(new ChessGame());
        frame.setSize(Game_Width, Game_Height);
        frame.setVisible(true);

    }

}
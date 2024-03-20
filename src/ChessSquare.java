import javax.swing.*;

public class ChessSquare extends JButton {
    private PieceObject piece;
    private int x;
    private int y;


    public ChessSquare() {
        super();
    }

    public PieceObject getPiece() {
        return piece;
    }

    public void setPiece(PieceObject piece) {
        this.piece = piece;

    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int[] getPos() {
        return new int[] {x, y};
    }
}

import javax.swing.*;

public class ChessSquare extends JButton {
    private PieceObject piece;

    public ChessSquare() {
        super();
    }

    public PieceObject getPiece() {
        return piece;
    }

    public void setPiece(PieceObject piece) {
        this.piece = piece;
    }
}

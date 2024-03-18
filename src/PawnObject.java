import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PawnObject extends PieceObject{
    static BufferedImage[] whitePawnImages = new SpriteSheet("Resources/Sprites/Pieces_Sprites.png", 6, 6, 133, 133).getImagesFrom(0, 1);

    public PawnObject(int value, String name, Color color, int x, int y) {
        super(value, name, color, x, y);
    }

    @Override
    public ArrayList<String> validMoves(String startingPos){
        ArrayList<String> validMoves = new ArrayList<String>();
        int x = startingPos.charAt(0) - 97;
        int y = startingPos.charAt(2) - 49;
        if(this.color == Color.WHITE){
            if(y == 1){
                validMoves.add((char)(x+97) + " " + (y+2));
            }
            if(y+1 < 8){
                validMoves.add((char)(x+97) + " " + (y+2));
            }
        }else{
            if(y == 6){
                validMoves.add((char)(x+97) + " " + (y-2));
            }
            if(y-1 >= 0){
                validMoves.add((char)(x+97) + " " + (y-2));
            }
        }
        return validMoves;
    }
    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(whitePawnImages[0], 10, 10, 100, 100, null);
    }
}

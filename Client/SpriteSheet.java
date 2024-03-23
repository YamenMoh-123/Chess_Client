import java.awt.image.*;
import java.util.Arrays;

public class SpriteSheet {

    public BufferedImage[] images;
    private BufferedImage source;

    public SpriteSheet(String filepath, int rows, int columns, int cellWidth, int cellHeight) {
        source = ImageLoader.loadImage(filepath);
        images = new BufferedImage[rows * columns];

        int index = 0;
        for(int r = 0; r < rows; r++) {
            for(int c = 0; c < columns; c++) {
                int x = c * cellWidth;
                int y = r * cellHeight;
                images[index++] = source.getSubimage(x, y, cellWidth, cellHeight);
            }
        }
    }

    public BufferedImage[] getImagesFrom(int startIndex, int endIndex) {
        return Arrays.copyOfRange(images, startIndex, endIndex + 1);
    }
}

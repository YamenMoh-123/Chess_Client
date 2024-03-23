
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class ImageLoader {

    public static BufferedImage loadImage(String filepath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filepath));
        } catch (IOException e) {
            System.out.println(filepath + " was not found");
        }

        return image;
    }

}
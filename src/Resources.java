import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Resources {

    public static SpriteSheet pieceSheet = new SpriteSheet("Resources/Sprites/Pieces_Sprites.png", 2, 6, 133, 133);

    public static void playSound(String soundFilePath) {
        File soundFile = new File(soundFilePath);
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}

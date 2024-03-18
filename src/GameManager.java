import java.awt.Color;
import java.awt.Graphics2D;
import java.util.LinkedList;

public class GameManager {

    LinkedList<PieceObject> gameObjects = new LinkedList<PieceObject>();

    public void tick() {
        gameObjects.forEach(gameObject -> gameObject.tick());
    }

    public void render(Graphics2D g2d) {
        g2d.translate(0, 0);
        gameObjects.forEach(gameObject -> gameObject.render(g2d));
    }

    public void addGameObject(PieceObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(PieceObject gameObject) {
        gameObjects.remove(gameObject);
    }
}

import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel implements Runnable {
    private Thread thread;
    private boolean running = false;
    public static GameManager gameManager = new GameManager();

    public GameCanvas() {
        this.setPreferredSize(new Dimension(900, 900));
        setDoubleBuffered(true);
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "Game Thread");
        thread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double ns = 1000000000.0 / 60; // Update 60 times per second
        double delta = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            repaint();
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
            }
        }
    }

    private void tick() {
        // Update game logic
        gameManager.tick();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Render game
        gameManager.render(g2d);
    }
}

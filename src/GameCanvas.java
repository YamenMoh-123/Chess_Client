
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class GameCanvas extends Canvas implements Runnable {

    private Thread thread;
    private BufferStrategy bs;
    public static GameManager gameManager = new GameManager();


    public GameCanvas() {
        System.out.println("game canvas was made");
    }

    public void start() {
        System.out.println("game canvas start was called");
        this.requestFocus();
        System.out.println("1");
        this.createBufferStrategy(2);
        System.out.println("2");
        bs = this.getBufferStrategy();
        System.out.println("3");
        thread = new Thread(this, "Game Thread");
        System.out.println("4");
        thread.start();
        System.out.println("game canvas start was finished");
    }

    @Override
    public void run() {
        System.out.println("game canvas is running");
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0;

        final int UPS_CAP = 60;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / (double) (1000000000 / UPS_CAP);
            lastTime = now;

            while (delta >= 1) {
                tick();
                delta--;
            }

            render();

            if (System.currentTimeMillis() - timer > 1000) timer += 1000;
        }
    }

    private void tick() {
        gameManager.tick();
    }

    public void render() {

        Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        System.out.println("calling render");
        gameManager.render(g2d);

        g2d.dispose();
        bs.show();
    }
}


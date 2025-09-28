package main;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable {
    private TelaJogo gameScreen;
    private BufferedImage img;
    private Thread gameThread;
    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;

    public Game() {
        this.importImg();
        this.setSize(640, 640);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo((Component)null);
        this.gameScreen = new TelaJogo(this.img);
        this.add(this.gameScreen);
        this.setVisible(true);
    }

    private void importImg() {
        InputStream is = this.getClass().getResourceAsStream("spriteatlas.png");
        try {
            this.img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    private void updateGame() {
        gameScreen.update();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / FPS_SET;
        double timePerUpdate = 1_000_000_000.0 / UPS_SET;

        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        long lastTimeCheck = System.currentTimeMillis();

        int frames = 0;
        int updates = 0;

        while(true) {
            long now = System.nanoTime();

            if ((now - lastFrame) >= timePerFrame) {
                this.repaint();
                lastFrame = now;
                frames++;
            }

            if ((now - lastUpdate) >= timePerUpdate) {
                this.updateGame();
                lastUpdate = now;
                updates++;
            }

            if (System.currentTimeMillis() - lastTimeCheck >= 1000L) {
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
                lastTimeCheck = System.currentTimeMillis();
            }
        }
    }
}

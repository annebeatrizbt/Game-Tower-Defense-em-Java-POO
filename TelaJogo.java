package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public class TelaJogo extends JPanel {
    private Random random;
    private BufferedImage img;
    private ArrayList<BufferedImage> sprites = new ArrayList<>();

    public TelaJogo(BufferedImage img) {
        this.img = img;
        this.loadSprites();
        this.random = new Random();
    }

    private void loadSprites() {
        for(int y = 0; y < 10; ++y) {
            for(int x = 0; x < 10; ++x) {
                this.sprites.add(this.img.getSubimage(x * 32, y * 32, 32, 32));
            }
        }
    }

    public void update() {
        // futuramente atualizar lógica de sprites
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int y = 0; y < 10; ++y) {
            for(int x = 0; x < 10; ++x) {
                g.drawImage(this.sprites.get(this.getRndInt()), x * 32, y * 32, null);
            }
        }
    }

    private int getRndInt() {
        return this.random.nextInt(this.sprites.size());
    }
}

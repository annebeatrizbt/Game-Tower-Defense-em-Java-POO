package ui;

import java.awt.Color;
import java.awt.Graphics;

public class Barra {

    protected int x, y, largura, altura;

    public Barra(int x, int y, int largura, int altura) {
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;

    }
    
    protected void desenharFeedbackBotao(Graphics g, MeuBotao b) {
        
        if (b.isMouseSobre())
            g.setColor(Color.white);
        else
            g.setColor(Color.BLACK);

       
        g.drawRect(b.x, b.y, b.largura, b.altura);

        
        if (b.isMousePressionado()) {
            g.drawRect(b.x + 1, b.y + 1, b.largura - 2, b.altura - 2);
            g.drawRect(b.x + 2, b.y + 2, b.largura - 4, b.altura - 4);
        }
    }
}

package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MeuBotao {

    public int x, y, largura, altura, id;
    private String texto;
    private Rectangle limites;
    private boolean mouseSobre, mousePressionado;

    
    public MeuBotao(String texto, int x, int y, int largura, int altura) {
        this.texto = texto;
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.id = -1;

        iniciarLimites();
    }


    public MeuBotao(String texto, int x, int y, int largura, int altura, int id) {
        this.texto = texto;
        this.x = x;
        this.y = y;
        this.largura = largura;
        this.altura = altura;
        this.id = id;

        iniciarLimites();
    }

    private void iniciarLimites() {
        this.limites = new Rectangle(x, y, largura, altura);
    }

    public void desenhar(Graphics g) {
       
        desenharCorpo(g);

      
        desenharBorda(g);

       
        desenharTexto(g);
    }

    private void desenharBorda(Graphics g) {

        g.setColor(Color.black);
        g.drawRect(x, y, largura, altura);
        if (mousePressionado) {
            g.drawRect(x + 1, y + 1, largura - 2, altura - 2);
            g.drawRect(x + 2, y + 2, largura - 4, altura - 4);
        }

    }

    private void desenharCorpo(Graphics g) {
        if (mouseSobre)
            g.setColor(Color.gray);
        else
            g.setColor(Color.WHITE);
        g.fillRect(x, y, largura, altura);

    }

    private void desenharTexto(Graphics g) {
        int w = g.getFontMetrics().stringWidth(texto);
        int h = g.getFontMetrics().getHeight();
        g.drawString(texto, x - w / 2 + largura / 2, y + h / 2 + altura / 2);

    }

    public void resetarBooleanos() {
        this.mouseSobre = false;
        this.mousePressionado = false;
    }

    public void setMousePressionado(boolean mousePressionado) {
        this.mousePressionado = mousePressionado;
    }

    public void setMouseSobre(boolean mouseSobre) {
        this.mouseSobre = mouseSobre;
    }

    public boolean isMouseSobre() {
        return mouseSobre;
    }

    public boolean isMousePressionado() {
        return mousePressionado;
    }

    public Rectangle getLimites() {
        return limites;
    }

    public int getId() {
        return id;
    }
}

package cenas;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

public interface MetodosCena {

    public void renderizar(Graphics g);

    public void mouseClicado(int x, int y);

    public void mouseMovido(int x, int y);

    public void mousePressionado(int x, int y);

    public void mouseSolto(int x, int y);

    public void mouseArrastado(int x, int y);
}
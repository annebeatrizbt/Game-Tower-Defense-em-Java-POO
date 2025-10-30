package objetos;

import java.awt.geom.Point2D;

public class Projetil {

    private Point2D.Float pos;
    private int id, tipoProjetil;
    private boolean ativo = true;

    public Projetil(float x, float y, int id, int tipoProjetil) {
        pos = new Point2D.Float(x, y);
        this.id = id;
        this.tipoProjetil = tipoProjetil;
    }

    public void mover(float x, float y) {
        pos.x += x;
        pos.y += y;
    }

    public Point2D.Float getPos() {
        return pos;
    }

    public void setPos(Point2D.Float pos) {
        this.pos = pos;
    }

    public int getId() {
        return id;
    }

    public int getTipoProjetil() {
        return tipoProjetil;
    }

    public boolean estaAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

}
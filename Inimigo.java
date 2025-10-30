package inimigos;

import static ajuda.Constantes.Direcao.*; 

import java.awt.Rectangle;

public abstract class Inimigo {

    protected float x, y;
    protected Rectangle limites; 
    protected int vida; 
    protected int vidaMaxima; 
    protected int ID;
    protected int tipoInimigo; 
    protected int ultimaDirecao; 
    protected boolean vivo = true; 

    public Inimigo(float x, float y, int ID, int tipoInimigo) { 
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.tipoInimigo = tipoInimigo; 
        limites = new Rectangle((int) x, (int) y, 32, 32); 
        ultimaDirecao = -1; 
        definirVidaInicial(); 
    }

    private void definirVidaInicial() { 
        vida = ajuda.Constantes.Inimigos.GetVidaInicial(tipoInimigo); 
        vidaMaxima = vida;
    }

    public void sofrerDano(int dano) {
        this.vida -= dano;
        if (vida <= 0)
            vivo = false;
    }

    public void mover(float velocidade, int direcao) {
        ultimaDirecao = direcao;
        switch (direcao) {
        case ESQUERDA: // Corrigido de LEFT
            this.x -= velocidade;
            break;
        case CIMA: // Corrigido de UP
            this.y -= velocidade;
            break;
        case DIREITA: // Corrigido de RIGHT
            this.x += velocidade;
            break;
        case BAIXO: // Corrigido de DOWN
            this.y += velocidade;
            break;
        }
    }

    public void definirPosicao(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float getBarraDeVidaFloat() {
        return vida / (float) vidaMaxima;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rectangle getLimites() {
        return limites;
    }

    public int getVida() {
        return vida;
    }

    public int getID() {
        return ID;
    }

    public int getTipoInimigo() {
        return tipoInimigo;
    }

    public int getUltimaDirecao() {
        return ultimaDirecao;
    }

    public boolean estaVivo() {
        return vivo;
    }

}
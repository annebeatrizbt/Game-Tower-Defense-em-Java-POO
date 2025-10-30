package gerenciadores;

import static ajuda.Constantes.Direcao.*;
import static ajuda.Constantes.Inimigos.*;
import static ajuda.Constantes.Blocos.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ajuda.CarregarSalvar;
import cenas.Jogando;
import inimigos.Morcego;
import inimigos.Inimigo; // Correto, usando a classe no singular
import inimigos.Cavaleiro;
import inimigos.Orc;
import inimigos.Lobo;
import objetos.PontoCaminho;

public class GerenciadorInimigos {

    private Jogando jogando;
    private BufferedImage[] imgsInimigo;
    private ArrayList<Inimigo> inimigos = new ArrayList<>(); // Correto
    private PontoCaminho inicio, fim;
    private int larguraBarraVida = 20;

    public GerenciadorInimigos(Jogando jogando, PontoCaminho inicio, PontoCaminho fim) {
        this.jogando = jogando;
        imgsInimigo = new BufferedImage[4];
        this.inicio = inicio;
        this.fim = fim;

        addInimigo(ORC);
        addInimigo(MORCEGO);
        addInimigo(CAVALEIRO);
        addInimigo(LOBO);

        carregarImgsInimigo();
    }

    private void carregarImgsInimigo() {
        BufferedImage atlas = CarregarSalvar.getAtlasSprites();
        for (int i = 0; i < 4; i++)
            imgsInimigo[i] = atlas.getSubimage(i * 32, 32, 32, 32);
    }

    public void atualizar() {
        for (Inimigo e : inimigos)
            if (e.estaVivo())
                atualizarMovimentoInimigo(e);
    }

    public void atualizarMovimentoInimigo(Inimigo e) {
        if (e.getUltimaDirecao() == -1)
            definirNovaDirecaoEMover(e);

        int novoX = (int) (e.getX() + getVelocidadeELargura(e.getUltimaDirecao(), e.getTipoInimigo()));
        int novoY = (int) (e.getY() + getVelocidadeEAltura(e.getUltimaDirecao(), e.getTipoInimigo()));

        if (getTipoBloco(novoX, novoY) == BLOCO_ESTRADA) {
            e.mover(GetVelocidade(e.getTipoInimigo()), e.getUltimaDirecao());
        } else if (estaNoFim(e)) {
            System.out.println("Vidas Perdidas!");
        } else {
            definirNovaDirecaoEMover(e);
        }
    }

    private void definirNovaDirecaoEMover(Inimigo e) {
        int dir = e.getUltimaDirecao();
        int coordX = (int) (e.getX() / 32);
        int coordY = (int) (e.getY() / 32);

        corrigirDeslocamentoBlocoInimigo(e, dir, coordX, coordY);

        if (estaNoFim(e))
            return;

        if (dir == ESQUERDA || dir == DIREITA) {
            int novoY = (int) (e.getY() + getVelocidadeEAltura(CIMA, e.getTipoInimigo()));
            if (getTipoBloco((int) e.getX(), novoY) == BLOCO_ESTRADA)
                e.mover(GetVelocidade(e.getTipoInimigo()), CIMA);
            else
                e.mover(GetVelocidade(e.getTipoInimigo()), BAIXO);
        } else {
            int novoX = (int) (e.getX() + getVelocidadeELargura(DIREITA, e.getTipoInimigo()));
            if (getTipoBloco(novoX, (int) e.getY()) == BLOCO_ESTRADA)
                e.mover(GetVelocidade(e.getTipoInimigo()), DIREITA);
            else
                e.mover(GetVelocidade(e.getTipoInimigo()), ESQUERDA);
        }
    }

    private void corrigirDeslocamentoBlocoInimigo(Inimigo e, int dir, int coordX, int coordY) {
        switch (dir) {
            case DIREITA:
                if (coordX < 19)
                    coordX++;
                break;
            case BAIXO:
                if (coordY < 19)
                    coordY++;
                break;
        }

        e.definirPosicao(coordX * 32, coordY * 32);
    }

    private boolean estaNoFim(Inimigo e) {
        // CORRIGIDO: de getxCord/getyCord para getCoordX/getCoordY
        return (e.getX() == fim.getxCord() * 32 && e.getY() == fim.getyCord() * 32);
    }

    private int getTipoBloco(int x, int y) {
        return jogando.getTipoBloco(x, y);
    }

    private float getVelocidadeEAltura(int dir, int tipoInimigo) {
        if (dir == CIMA)
            return -GetVelocidade(tipoInimigo);
        else if (dir == BAIXO)
            return GetVelocidade(tipoInimigo) + 32;
        return 0;
    }

    private float getVelocidadeELargura(int dir, int tipoInimigo) {
        if (dir == ESQUERDA)
            return -GetVelocidade(tipoInimigo);
        else if (dir == DIREITA)
            return GetVelocidade(tipoInimigo) + 32;
        return 0;
    }

    public void addInimigo(int tipoInimigo) {
        // CORRIGIDO: de getxCord/getyCord para getCoordX/getCoordY
        int x = inicio.getxCord() * 32;
        int y = inicio.getyCord() * 32;

        switch (tipoInimigo) {
            case ORC:
                inimigos.add(new Orc(x, y, 0));
                break;
            case MORCEGO:
                inimigos.add(new Morcego(x, y, 0));
                break;
            case CAVALEIRO:
                inimigos.add(new Cavaleiro(x, y, 0));
                break;
            case LOBO:
                inimigos.add(new Lobo(x, y, 0));
                break;
        }
    }

    public void desenhar(Graphics g) {
        for (Inimigo e : inimigos) {
            if (e.estaVivo()) {
                desenharInimigo(e, g);
                desenharBarraVida(e, g);
            }
        }
    }

    private void desenharBarraVida(Inimigo e, Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) e.getX() + 16 - (getNovaLarguraBarra(e) / 2), (int) e.getY() - 10, getNovaLarguraBarra(e), 3);
    }

    private int getNovaLarguraBarra(Inimigo e) {
        return (int) (larguraBarraVida * e.getBarraDeVidaFloat());
    }

    private void desenharInimigo(Inimigo e, Graphics g) {
        g.drawImage(imgsInimigo[e.getTipoInimigo()], (int) e.getX(), (int) e.getY(), null);
    }

    public ArrayList<Inimigo> getInimigos() {
        return inimigos;
    }
}
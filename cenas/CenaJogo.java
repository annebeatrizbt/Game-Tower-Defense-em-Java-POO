package cenas;

import java.awt.image.BufferedImage;

import principal.Jogo;

public class CenaJogo {

    protected Jogo jogo;
    protected int indiceAnimacao;
    protected int VELOCIDADE_ANIMACAO = 25;
    protected int tick;

    public CenaJogo(Jogo jogo) {
        this.jogo = jogo;
    }

    public Jogo getJogo() {
        return jogo;
    }

    protected boolean eAnimacao(int spriteID) {
        return jogo.getGerenciadorBlocos().eAnimacaoSprite(spriteID);
    }

    protected void atualizarTick() {
        tick++;
        if (tick >= VELOCIDADE_ANIMACAO) {
            tick = 0;
            indiceAnimacao++;
            if (indiceAnimacao >= 4)
                indiceAnimacao = 0;
        }
    }

    protected BufferedImage getSprite(int spriteID) {
        return jogo.getGerenciadorBlocos().getSprite(spriteID);
    }

    protected BufferedImage getSprite(int spriteID, int indiceAnimacao) {
        return jogo.getGerenciadorBlocos().getSpriteAnimado(spriteID, indiceAnimacao);
    }

}

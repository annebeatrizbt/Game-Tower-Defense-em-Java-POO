package cenas;

import static principal.EstadosJogo.*;

import java.awt.Graphics;
import principal.Jogo;
import ui.MeuBotao;

public class Menu extends CenaJogo implements MetodosCena {

    private MeuBotao bJogar, bEditar, bConfiguracoes, bSair;

    public Menu(Jogo jogo) {
        super(jogo);
        iniciarBotoes();
    }

    private void iniciarBotoes() {
        int w = 150;
        int h = w / 3;
        int x = 640 / 2 - w / 2;
        int y = 150;
        int yOffset = 100;

        bJogar = new MeuBotao("Jogar", x, y, w, h);
        bEditar = new MeuBotao("Editar", x, y + yOffset, w, h);
        bConfiguracoes = new MeuBotao("Configurações", x, y + yOffset * 2, w, h);
        bSair = new MeuBotao("Sair", x, y + yOffset * 3, w, h);
    }

    @Override
    public void renderizar(Graphics g) {
        desenharBotoes(g);
    }

    private void desenharBotoes(Graphics g) {
        bJogar.desenhar(g);
        bEditar.desenhar(g);
        bConfiguracoes.desenhar(g);
        bSair.desenhar(g);
    }

    @Override
    public void mouseClicado(int x, int y) { // Padronizado
        if (bJogar.getLimites().contains(x, y))
            DefinirEstadoJogo(JOGANDO);
        else if (bEditar.getLimites().contains(x, y))
            DefinirEstadoJogo(EDITAR);
        else if (bConfiguracoes.getLimites().contains(x, y))
            DefinirEstadoJogo(CONFIGURACOES);
        else if (bSair.getLimites().contains(x, y))
            System.exit(0);
    }

    @Override
    public void mouseMovido(int x, int y) { // Padronizado
        bJogar.setMouseSobre(false);
        bEditar.setMouseSobre(false);
        bConfiguracoes.setMouseSobre(false);
        bSair.setMouseSobre(false);

        if (bJogar.getLimites().contains(x, y))
            bJogar.setMouseSobre(true);
        else if (bEditar.getLimites().contains(x, y))
            bEditar.setMouseSobre(true);
        else if (bConfiguracoes.getLimites().contains(x, y))
            bConfiguracoes.setMouseSobre(true);
        else if (bSair.getLimites().contains(x, y))
            bSair.setMouseSobre(true);
    }

    @Override
    public void mousePressionado(int x, int y) { // Padronizado
        if (bJogar.getLimites().contains(x, y))
            bJogar.setMousePressionado(true);
        else if (bEditar.getLimites().contains(x, y))
            bEditar.setMousePressionado(true);
        else if (bConfiguracoes.getLimites().contains(x, y))
            bConfiguracoes.setMousePressionado(true);
        else if (bSair.getLimites().contains(x, y))
            bSair.setMousePressionado(true);
    }

    @Override
    public void mouseSolto(int x, int y) { // Padronizado
        resetarBotoes();
    }

    private void resetarBotoes() {
        bJogar.resetarBooleanos();
        bEditar.resetarBooleanos();
        bConfiguracoes.resetarBooleanos();
        bSair.resetarBooleanos();
    }

    @Override
    public void mouseArrastado(int x, int y) { // Padronizado
        // sem ação por enquanto
    }
}
package ui;

import static principal.EstadosJogo.MENU;
import static principal.EstadosJogo.DefinirEstadoJogo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import ajuda.Constantes.Torres;
import cenas.Jogando;
import objetos.Torre;

public class BarraAcao extends Barra {

    private Jogando jogando;
    private MeuBotao bMenu;

    private MeuBotao[] botoesTorre;
    private Torre torreSelecionada;
    private Torre torreExibida;

    public BarraAcao(int x, int y, int largura, int altura, Jogando jogando) {
        super(x, y, largura, altura);
        this.jogando = jogando;

        iniciarBotoes();
    }

    private void iniciarBotoes() {

        bMenu = new MeuBotao("Menu", 2, 642, 100, 30);

        botoesTorre = new MeuBotao[3];
        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int) (w * 1.1f);

        for (int i = 0; i < botoesTorre.length; i++)
            botoesTorre[i] = new MeuBotao("", xStart + xOffset * i, yStart, w, h, i);

    }

    private void desenharBotoes(Graphics g) {
        bMenu.desenhar(g);

        for (MeuBotao b : botoesTorre) {
            g.setColor(Color.gray);
            g.fillRect(b.x, b.y, b.largura, b.altura);
            g.drawImage(jogando.getGerenciadorTorres().getImgsTorre()[b.getId()], b.x, b.y, b.largura, b.altura, null);
            desenharFeedbackBotao(g, b);
        }
    }

    public void desenhar(Graphics g) {

        // Fundo
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, largura, altura);

        // Botoes
        desenharBotoes(g);

        // TorreExibida
        desenharTorreExibida(g);

    }

    private void desenharTorreExibida(Graphics g) {
        if (torreExibida != null) {
            g.setColor(Color.gray);
            g.fillRect(410, 645, 220, 85);
            g.setColor(Color.black);
            g.drawRect(410, 645, 220, 85);
            g.drawRect(420, 650, 50, 50);
            g.drawImage(jogando.getGerenciadorTorres().getImgsTorre()[torreExibida.getTipoTorre()], 420, 650, 50, 50, null);
            g.setFont(new Font("LucidaSans", Font.BOLD, 15));
            g.drawString("" + Torres.GetNome(torreExibida.getTipoTorre()), 490, 660);
            g.drawString("ID: " + torreExibida.getId(), 490, 675);
            desenharBordaTorreExibida(g);
            desenharAlcanceTorreExibida(g);
        }

    }

    private void desenharAlcanceTorreExibida(Graphics g) {
        g.setColor(Color.white);
        g.drawOval(torreExibida.getX() + 16 - (int) (torreExibida.getAlcance() * 2) / 2, torreExibida.getY() + 16 - (int) (torreExibida.getAlcance() * 2) / 2, (int) torreExibida.getAlcance() * 2,
                (int) torreExibida.getAlcance() * 2);

    }

    private void desenharBordaTorreExibida(Graphics g) {

        g.setColor(Color.CYAN);
        g.drawRect(torreExibida.getX(), torreExibida.getY(), 32, 32);

    }

    public void exibirTorre(Torre t) {
        torreExibida = t;
    }

    public void mouseClicado(int x, int y) {
        if (bMenu.getLimites().contains(x, y))
            DefinirEstadoJogo(MENU);
        else {
            for (MeuBotao b : botoesTorre) {
                if (b.getLimites().contains(x, y)) {
                    torreSelecionada = new Torre(0, 0, -1, b.getId());
                    jogando.setTorreSelecionada(torreSelecionada);
                    return;
                }
            }
        }

    }

    public void mouseMovido(int x, int y) {
        bMenu.setMouseSobre(false);
        for (MeuBotao b : botoesTorre)
            b.setMouseSobre(false);

        if (bMenu.getLimites().contains(x, y))
            bMenu.setMouseSobre(true);
        else {
            for (MeuBotao b : botoesTorre)
                if (b.getLimites().contains(x, y)) {
                    b.setMouseSobre(true);
                    return;
                }
        }
    }

    public void mousePressionado(int x, int y) {
        if (bMenu.getLimites().contains(x, y))
            bMenu.setMousePressionado(true);
        else
            for (MeuBotao b : botoesTorre)
                if (b.getLimites().contains(x, y)) {
                    b.setMousePressionado(true);
                    return;
                }

    }

    public void mouseSolto(int x, int y) {
        bMenu.resetarBooleanos();
        for (MeuBotao b : botoesTorre)
            b.resetarBooleanos();

    }

}

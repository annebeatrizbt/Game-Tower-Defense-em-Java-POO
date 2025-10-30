package ui;

import static principal.EstadosJogo.MENU;
import static principal.EstadosJogo.DefinirEstadoJogo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ajuda.CarregarSalvar;
import cenas.Editando;
import objetos.Bloco;

public class BarraFerramentas extends Barra {

    private Editando editando;
    private MeuBotao bMenu, bSalvar;
    private MeuBotao bPontoInicial, bPontoFinal;
    private BufferedImage imgPontoInicial, imgPontoFinal;

    private Bloco blocoSelecionado;
    private MeuBotao botaoAtual;
    private int indiceAtual = 0;

    private final Map<MeuBotao, ArrayList<Bloco>> map = new HashMap<>();

    private MeuBotao bGrama, bAgua;
    private MeuBotao bEstradaR, bEstradaC, bAguaC, bAguaP, bAguaI;

    public BarraFerramentas(int x, int y, int width, int height, Editando editando) {
        super(x, y, width, height);
        this.editando = editando;

        carregarImagensCaminho();
        inicializarBotoes();
    }

    private void carregarImagensCaminho() {
        BufferedImage atlas = CarregarSalvar.getAtlasSprites();
        imgPontoInicial = atlas.getSubimage(7 * 32, 2 * 32, 32, 32);
        imgPontoFinal = atlas.getSubimage(8 * 32, 2 * 32, 32, 32);
    }

    private void inicializarBotoes() {
        bMenu = new MeuBotao("Menu", 2, 642, 100, 30);
        bSalvar = new MeuBotao("Salvar", 2, 674, 100, 30);

        int w = 50, h = 50;
        int xStart = 110, yStart = 650;
        int xOffset = (int) (w * 1.1f);
        int i = 0;

        bGrama = new MeuBotao("Grama", xStart, yStart, w, h, i++);
        bAgua = new MeuBotao("Agua", xStart + xOffset, yStart, w, h, i++);

        bEstradaR = criarBotaoMapa(editando.getJogo().getGerenciadorBlocos().getEstradasR(), xStart, yStart, xOffset, w, h, i++);
        bEstradaC = criarBotaoMapa(editando.getJogo().getGerenciadorBlocos().getEstradasC(), xStart, yStart, xOffset, w, h, i++);
        bAguaC = criarBotaoMapa(editando.getJogo().getGerenciadorBlocos().getCantos(), xStart, yStart, xOffset, w, h, i++);
        bAguaP = criarBotaoMapa(editando.getJogo().getGerenciadorBlocos().getPraias(), xStart, yStart, xOffset, w, h, i++);
        bAguaI = criarBotaoMapa(editando.getJogo().getGerenciadorBlocos().getIlhas(), xStart, yStart, xOffset, w, h, i++);

        bPontoInicial = new MeuBotao("PontoInicial", xStart, yStart + xOffset, w, h, i++);
        bPontoFinal = new MeuBotao("PontoFinal", xStart + xOffset, yStart + xOffset, w, h, i++);
    }

    private MeuBotao criarBotaoMapa(ArrayList<Bloco> lista, int x, int y, int xOff, int w, int h, int id) {
        MeuBotao b = new MeuBotao("", x + xOff * id, y, w, h, id);
        map.put(b, lista);
        return b;
    }

    private void salvarNivel() {
        editando.salvarNivel();
    }

    public void rotacionarSprite() {
        if (botaoAtual == null) return;

        indiceAtual = (indiceAtual + 1) % map.get(botaoAtual).size();
        blocoSelecionado = map.get(botaoAtual).get(indiceAtual);
        editando.setBlocoSelecionado(blocoSelecionado);
    }

    public void desenhar(Graphics g) {
        g.setColor(new Color(220, 123, 15));
        g.fillRect(x, y, largura, altura);

        desenharBotoes(g);
        desenharBlocoSelecionado(g);
    }

    private void desenharBotoes(Graphics g) {
        bMenu.desenhar(g);
        bSalvar.desenhar(g);

        desenharBotaoComImagem(g, bPontoInicial, imgPontoInicial);
        desenharBotaoComImagem(g, bPontoFinal, imgPontoFinal);

        desenharBotaoComImagem(g, bGrama, getImgBotao(bGrama.getId()));
        desenharBotaoComImagem(g, bAgua, getImgBotao(bAgua.getId()));

        for (Map.Entry<MeuBotao, ArrayList<Bloco>> entry : map.entrySet()) {
            MeuBotao b = entry.getKey();
            BufferedImage img = entry.getValue().get(0).getSprite();
            desenharBotaoComImagem(g, b, img);
        }
    }

    private void desenharBotaoComImagem(Graphics g, MeuBotao b, BufferedImage img) {
        g.drawImage(img, b.x, b.y, b.largura, b.altura, null);
        desenharFeedbackBotao(g, b);
    }

    private void desenharBlocoSelecionado(Graphics g) {
        if (blocoSelecionado != null) {
            g.drawImage(blocoSelecionado.getSprite(), 550, 650, 50, 50, null);
            g.setColor(Color.black);
            g.drawRect(550, 650, 50, 50);
        }
    }

    public BufferedImage getImgBotao(int id) {
        return editando.getJogo().getGerenciadorBlocos().getSprite(id);
    }

    public void mouseClicado(int x, int y) {
        if (bMenu.getLimites().contains(x, y)) {
            DefinirEstadoJogo(MENU);
            return;
        }
        if (bSalvar.getLimites().contains(x, y)) {
            salvarNivel();
            return;
        }

        if (bAgua.getLimites().contains(x, y)) selecionarBloco(bAgua);
        else if (bGrama.getLimites().contains(x, y)) selecionarBloco(bGrama);
        else if (bPontoInicial.getLimites().contains(x, y)) setBlocoEspecial(imgPontoInicial, -1);
        else if (bPontoFinal.getLimites().contains(x, y)) setBlocoEspecial(imgPontoFinal, -2);
        else {
            for (MeuBotao b : map.keySet()) {
                if (b.getLimites().contains(x, y)) {
                    botaoAtual = b;
                    indiceAtual = 0;
                    blocoSelecionado = map.get(b).get(0);
                    editando.setBlocoSelecionado(blocoSelecionado);
                    return;
                }
            }
        }
    }

    private void selecionarBloco(MeuBotao b) {
        blocoSelecionado = editando.getJogo().getGerenciadorBlocos().getBloco(b.getId());
        editando.setBlocoSelecionado(blocoSelecionado);
    }

    private void setBlocoEspecial(BufferedImage img, int id) {
        blocoSelecionado = new Bloco(img, id, id);
        editando.setBlocoSelecionado(blocoSelecionado);
    }

    public void mouseMovido(int x, int y) {
        resetarHover();

        if (bMenu.getLimites().contains(x, y)) bMenu.setMouseSobre(true);
        else if (bSalvar.getLimites().contains(x, y)) bSalvar.setMouseSobre(true);
        else if (bAgua.getLimites().contains(x, y)) bAgua.setMouseSobre(true);
        else if (bGrama.getLimites().contains(x, y)) bGrama.setMouseSobre(true);
        else if (bPontoInicial.getLimites().contains(x, y)) bPontoInicial.setMouseSobre(true);
        else if (bPontoFinal.getLimites().contains(x, y)) bPontoFinal.setMouseSobre(true);
        else for (MeuBotao b : map.keySet()) if (b.getLimites().contains(x, y)) b.setMouseSobre(true);
    }

    private void resetarHover() {
        bMenu.setMouseSobre(false);
        bSalvar.setMouseSobre(false);
        bAgua.setMouseSobre(false);
        bGrama.setMouseSobre(false);
        bPontoInicial.setMouseSobre(false);
        bPontoFinal.setMouseSobre(false);
        for (MeuBotao b : map.keySet()) b.setMouseSobre(false);
    }

    public void mousePressionado(int x, int y) {
        for (MeuBotao b : getTodosBotoes()) {
            if (b.getLimites().contains(x, y)) b.setMousePressionado(true);
        }
    }

    public void mouseSolto(int x, int y) {
        for (MeuBotao b : getTodosBotoes()) {
            b.resetarBooleanos();
        }
    }

    private ArrayList<MeuBotao> getTodosBotoes() {
        ArrayList<MeuBotao> botoes = new ArrayList<>(map.keySet());
        botoes.add(bMenu);
        botoes.add(bSalvar);
        botoes.add(bGrama);
        botoes.add(bAgua);
        botoes.add(bPontoInicial);
        botoes.add(bPontoFinal);
        return botoes;
    }

    public BufferedImage getImgPontoInicial() { return imgPontoInicial; }
    public BufferedImage getImgPontoFinal() { return imgPontoFinal; }
}

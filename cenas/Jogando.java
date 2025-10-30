package cenas;

import static ajuda.Constantes.Blocos.BLOCO_GRAMA;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ajuda.CarregarSalvar;
import gerenciadores.GerenciadorInimigos;
import gerenciadores.GerenciadorTorres;
import objetos.PontoCaminho;
import objetos.Torre;
import principal.Jogo;
import ui.BarraAcao;

public class Jogando extends CenaJogo implements MetodosCena {

    private int[][] nvl;
    private PontoCaminho inicio, fim;

    private GerenciadorInimigos gerenciadorInimigos;
    private GerenciadorTorres gerenciadorTorres;

    private BarraAcao barraAcao;
    private Torre torreSelecionada;
    private int mouseX, mouseY;

    public Jogando(Jogo jogo) {
        super(jogo);
        carregarNvelPadrao();

        barraAcao = new BarraAcao(0, 640, 640, 160, this);
        gerenciadorInimigos = new GerenciadorInimigos(this, inicio, fim);
        gerenciadorTorres = new GerenciadorTorres(this);
    }

    private void carregarNvelPadrao() {
        nvl = CarregarSalvar.GetDadosNivel("new_level");
        ArrayList<PontoCaminho> pontos = CarregarSalvar.GetPontosCaminhoNivel("new_level");

        if (pontos == null || pontos.size() < 2) {
            System.err.println("ERRO: Falha ao carregar pontos do nível! Usando posições padrões.");
            inicio = new PontoCaminho(0, 0);
            fim = new PontoCaminho(1, 1);
        } else {
            inicio = pontos.get(0);
            fim = pontos.get(1);
        }
    }

    public void atualizar() {
        atualizarTick();
        gerenciadorInimigos.atualizar();
        gerenciadorTorres.atualizar();
    }

    @Override
    public void renderizar(Graphics g) {
        desenharNivel(g);
        barraAcao.desenhar(g);
        gerenciadorInimigos.desenhar(g);
        gerenciadorTorres.desenhar(g);
        desenharTorreSelecionada(g);
        desenharDestaque(g);
    }

    private void desenharNivel(Graphics g) {
        for (int y = 0; y < nvl.length; y++) {
            for (int x = 0; x < nvl[y].length; x++) {
                int id = nvl[y][x];
                if (eAnimacao(id)) {
                    g.drawImage(getSprite(id, indiceAnimacao), x * 32, y * 32, null);
                } else {
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
                }
            }
        }
    }

    private void desenharTorreSelecionada(Graphics g) {
        if (torreSelecionada != null) {
            g.drawImage(gerenciadorTorres.getImgsTorre()[torreSelecionada.getTipoTorre()], mouseX, mouseY, null);
        }
    }

    private void desenharDestaque(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawRect(mouseX, mouseY, 32, 32);
    }

    @Override
    public void mouseClicado(int x, int y) {
        if (y >= 640) {
            barraAcao.mouseClicado(x, y);
        } else {
            if (torreSelecionada != null) {
                if (eBlocoGrama(mouseX, mouseY)) {
                    if (getTorreEm(mouseX, mouseY) == null) {
                        gerenciadorTorres.addTorre(torreSelecionada, mouseX, mouseY);
                        torreSelecionada = null;
                    }
                }
            } else {
                Torre t = getTorreEm(mouseX, mouseY);
                barraAcao.exibirTorre(t);
            }
        }
    }

    @Override
    public void mouseMovido(int x, int y) {
        if (y >= 640) {
            barraAcao.mouseMovido(x, y);
        } else {
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressionado(int x, int y) {
        if (y >= 640) {
            barraAcao.mousePressionado(x, y);
        }
    }

    @Override
    public void mouseSolto(int x, int y) {
        barraAcao.mouseSolto(x, y);
    }

    @Override
    public void mouseArrastado(int x, int y) {
    }

    public void teclaPressionada(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            torreSelecionada = null;
        }
    }

    private boolean eBlocoGrama(int x, int y) {
        int coordX = x / 32;
        int coordY = y / 32;

        if (coordX < 0 || coordX > 19 || coordY < 0 || coordY > 19) {
            return false;
        }

        int id = nvl[coordY][coordX];
        int tipoBloco = jogo.getGerenciadorBlocos().getBloco(id).getTipoBloco();
        return tipoBloco == BLOCO_GRAMA;
    }

    private Torre getTorreEm(int x, int y) {
        return gerenciadorTorres.getTorreEm(x, y);
    }

    public int getTipoBloco(int x, int y) {
        int coordX = x / 32;
        int coordY = y / 32;

        if (coordX < 0 || coordX > 19) {
            return 0;
        }
        if (coordY < 0 || coordY > 19) {
            return 0;
        }

        int id = nvl[coordY][coordX];
        return jogo.getGerenciadorBlocos().getBloco(id).getTipoBloco();
    }

    public void setNivel(int[][] nvl) {
        this.nvl = nvl;
    }

    public void setTorreSelecionada(Torre torreSelecionada) {
        this.torreSelecionada = torreSelecionada;
    }

    public GerenciadorTorres getGerenciadorTorres() {
        return gerenciadorTorres;
    }

    public GerenciadorInimigos getGerenciadorInimigos() {
        return gerenciadorInimigos;
    }
}
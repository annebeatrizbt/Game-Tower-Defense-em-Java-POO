package cenas;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import ajuda.CarregarSalvar;
import objetos.PontoCaminho;
import objetos.Bloco;
import principal.Jogo;
import ui.BarraFerramentas;

import static ajuda.Constantes.Blocos.BLOCO_ESTRADA;

public class Editando extends CenaJogo implements MetodosCena {

    private int[][] nivel;
    private Bloco blocoSelecionado;
    private int mouseX, mouseY;
    private int ultimoX, ultimoY, ultimoId;
    private boolean desenharSelecao;
    private BarraFerramentas barra;
    private PontoCaminho inicio, fim;

    public Editando(Jogo jogo) {
        super(jogo);
        carregarNivelPadrao();
        barra = new BarraFerramentas(0, 640, 640, 160, this);
    }

    private void carregarNivelPadrao() {
        // CORREÇÃO 2 (agora funciona): GetDadosNivel retorna int[][]
        nivel = CarregarSalvar.GetDadosNivel("new_level"); 
        ArrayList<PontoCaminho> pontos = CarregarSalvar.GetPontosCaminhoNivel("new_level");
        if (pontos != null && pontos.size() >= 2) {
            inicio = pontos.get(0);
            fim = pontos.get(1);
        } else {
            inicio = new PontoCaminho(0, 0);
            fim = new PontoCaminho(0, 0);
        }
    }

    public void atualizar() {
        atualizarTick();
    }

    @Override
    public void renderizar(Graphics g) {
        desenharNivel(g);
        barra.desenhar(g);
        desenharBlocoSelecionado(g);
        desenharPontosCaminho(g);
    }

    private void desenharPontosCaminho(Graphics g) {
        if (inicio != null)
             // CORREÇÃO 4: Usar getCoordX e getCoordY
            g.drawImage(barra.getImgPontoInicial(), inicio.getxCord() * 32, inicio.getyCord() * 32, 32, 32, null);

        if (fim != null)
            // CORREÇÃO 4: Usar getCoordX e getCoordY
            g.drawImage(barra.getImgPontoFinal(), fim.getxCord() * 32, fim.getyCord() * 32, 32, 32, null);
    }

    private void desenharNivel(Graphics g) {
        for (int y = 0; y < nivel.length; y++) {
            for (int x = 0; x < nivel[y].length; x++) {
                int id = nivel[y][x];
                if (eAnimacao(id)) {
                    g.drawImage(getSprite(id, indiceAnimacao), x * 32, y * 32, null);
                } else {
                    g.drawImage(getSprite(id), x * 32, y * 32, null);
                }
            }
        }
    }

    private void desenharBlocoSelecionado(Graphics g) {
        if (blocoSelecionado != null && desenharSelecao) {
            g.drawImage(blocoSelecionado.getSprite(), mouseX, mouseY, 32, 32, null);
        }
    }

    public void salvarNivel() {
        // CORREÇÃO 3: Chamar SalvarNivel em vez de CriarNivel
        CarregarSalvar.SalvarNivel("new_level", nivel, inicio, fim); 
        jogo.getJogando().setNivel(nivel);
    }

    public void setBlocoSelecionado(Bloco bloco) {
        this.blocoSelecionado = bloco;
        desenharSelecao = true;
    }

    private void alterarBloco(int x, int y) {
        if (blocoSelecionado != null) {
            int tileX = x / 32;
            int tileY = y / 32;
            
            // Checagem de limites para evitar ArrayIndexOutOfBoundsException
            if (tileX < 0 || tileX >= 20 || tileY < 0 || tileY >= 20)
                return;

            if (blocoSelecionado.getId() >= 0) {
                if (ultimoX == tileX && ultimoY == tileY && ultimoId == blocoSelecionado.getId())
                    return;

                ultimoX = tileX;
                ultimoY = tileY;
                ultimoId = blocoSelecionado.getId();

                nivel[tileY][tileX] = blocoSelecionado.getId();
            } else {
                int id = nivel[tileY][tileX];
                if (jogo.getGerenciadorBlocos().getBloco(id).getTipoBloco() == BLOCO_ESTRADA) {
                    if (blocoSelecionado.getId() == -1)
                        inicio = new PontoCaminho(tileX, tileY);
                    else
                        fim = new PontoCaminho(tileX, tileY);
                }
            }
        }
    }

    @Override
    public void mouseClicado(int x, int y) {
        if (y >= 640)
            barra.mouseClicado(x, y);
        else
            alterarBloco(mouseX, mouseY);
    }

    @Override
    public void mouseMovido(int x, int y) {
        if (y >= 640) {
            barra.mouseMovido(x, y);
            desenharSelecao = false;
        } else {
            desenharSelecao = true;
            mouseX = (x / 32) * 32;
            mouseY = (y / 32) * 32;
        }
    }

    @Override
    public void mousePressionado(int x, int y) {
        if (y >= 640)
            barra.mousePressionado(x, y);
    }

    @Override
    public void mouseSolto(int x, int y) {
        barra.mouseSolto(x, y);
    }

    @Override
    public void mouseArrastado(int x, int y) {
        if (y < 640)
            alterarBloco(x, y);
    }

    public void teclaPressionada(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R)
            barra.rotacionarSprite();
    }
}
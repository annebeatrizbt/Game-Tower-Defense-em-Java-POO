package gerenciadores;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ajuda.CarregarSalvar;
import ajuda.Utilitarios;
import cenas.Jogando;
import inimigos.Inimigo;
import objetos.Torre;

public class GerenciadorTorres {

    private Jogando jogando;
    private BufferedImage[] imgsTorre;
    private ArrayList<Torre> torres = new ArrayList<>();
    private int quantidadeTorres = 0;

    public GerenciadorTorres(Jogando jogando) {
        this.jogando = jogando;
        carregarImgsTorre();
    }

    private void carregarImgsTorre() {
        BufferedImage atlas = CarregarSalvar.getAtlasSprites();
        imgsTorre = new BufferedImage[3];
        for (int i = 0; i < 3; i++)
            imgsTorre[i] = atlas.getSubimage((4 + i) * 32, 32, 32, 32);
    }

    public void addTorre(Torre torreSelecionada, int xPos, int yPos) {
        torres.add(new Torre(xPos, yPos, quantidadeTorres++, torreSelecionada.getTipoTorre()));
    }

    public void atualizar() {
        atacarInimigoSePerto();
    }

    private void atacarInimigoSePerto() {
        for (Torre t : torres) {
            for (Inimigo e : jogando.getGerenciadorInimigos().getInimigos()) {
                if (e.estaVivo() && estaInimigoNoAlcance(t, e)) {
                    e.sofrerDano(1);
                }
            }
        }
    }

    private boolean estaInimigoNoAlcance(Torre t, Inimigo e) {
        int alcance = Utilitarios.GetDistanciaHipotenusa(t.getX(), t.getY(), e.getX(), e.getY());
        return alcance < t.getAlcance();
    }

    public void desenhar(Graphics g) {
        for (Torre t : torres)
            g.drawImage(imgsTorre[t.getTipoTorre()], t.getX(), t.getY(), null);
    }

    public Torre getTorreEm(int x, int y) {
        for (Torre t : torres)
            if (t.getX() == x && t.getY() == y)
                return t;
        return null;
    }

    public BufferedImage[] getImgsTorre() {
        return imgsTorre;
    }

}

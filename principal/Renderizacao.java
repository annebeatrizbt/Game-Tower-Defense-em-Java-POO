package principal;

import java.awt.Graphics;

public class Renderizacao {

    private Jogo jogo;

    public Renderizacao(Jogo jogo) {
        this.jogo = jogo;

    }

    public void renderizar(Graphics g) {

        switch (EstadosJogo.estadoJogo) {

        case MENU:
            jogo.getMenu().renderizar(g);

            break;
        case JOGANDO:

            jogo.getJogando().renderizar(g);

            break;
        case CONFIGURACOES:

            jogo.getConfiguracoes().renderizar(g);

            break;
        case EDITAR:
            jogo.getEditor().renderizar(g);
            break;
        default:
            break;

        }

    }

}

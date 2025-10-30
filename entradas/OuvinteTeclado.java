package entradas;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import principal.EstadosJogo;
import principal.Jogo;
import static principal.EstadosJogo.EDITAR;
import static principal.EstadosJogo.JOGANDO;

public class OuvinteTeclado implements KeyListener {

    private Jogo jogo;

    public OuvinteTeclado(Jogo jogo) {
        this.jogo = jogo;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (EstadosJogo.estadoJogo == EDITAR) {
            jogo.getEditor().teclaPressionada(e);
        } else if (EstadosJogo.estadoJogo == JOGANDO) {
            jogo.getJogando().teclaPressionada(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
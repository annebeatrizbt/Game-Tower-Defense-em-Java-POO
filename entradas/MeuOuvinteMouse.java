package entradas;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import principal.Jogo;
import principal.EstadosJogo;

public class MeuOuvinteMouse implements MouseListener, MouseMotionListener {

    private Jogo jogo;

    public MeuOuvinteMouse(Jogo jogo) {
        this.jogo = jogo;
    }

    @Override
    public void mouseDragged(MouseEvent e) { // Nome da AWT em Inglês
        switch (EstadosJogo.estadoJogo) {
            // Chamada interna do seu jogo em Português
            case MENU -> jogo.getMenu().mouseArrastado(e.getX(), e.getY());
            case JOGANDO -> jogo.getJogando().mouseArrastado(e.getX(), e.getY());
            case CONFIGURACOES -> jogo.getConfiguracoes().mouseArrastado(e.getX(), e.getY());
            case EDITAR -> jogo.getEditor().mouseArrastado(e.getX(), e.getY());
            default -> {}
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) { // Nome da AWT em Inglês
        switch (EstadosJogo.estadoJogo) {
            // Chamada interna do seu jogo em Português
            case MENU -> jogo.getMenu().mouseMovido(e.getX(), e.getY());
            case JOGANDO -> jogo.getJogando().mouseMovido(e.getX(), e.getY());
            case CONFIGURACOES -> jogo.getConfiguracoes().mouseMovido(e.getX(), e.getY());
            case EDITAR -> jogo.getEditor().mouseMovido(e.getX(), e.getY());
            default -> {}
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) { // Nome da AWT em Inglês
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (EstadosJogo.estadoJogo) {
                // Chamada interna do seu jogo em Português
                case MENU -> jogo.getMenu().mouseClicado(e.getX(), e.getY());
                case JOGANDO -> jogo.getJogando().mouseClicado(e.getX(), e.getY());
                case CONFIGURACOES -> jogo.getConfiguracoes().mouseClicado(e.getX(), e.getY());
                case EDITAR -> jogo.getEditor().mouseClicado(e.getX(), e.getY());
                default -> {}
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { // Nome da AWT em Inglês
        switch (EstadosJogo.estadoJogo) {
            // Chamada interna do seu jogo em Português
            case MENU -> jogo.getMenu().mousePressionado(e.getX(), e.getY());
            case JOGANDO -> jogo.getJogando().mousePressionado(e.getX(), e.getY());
            case CONFIGURACOES -> jogo.getConfiguracoes().mousePressionado(e.getX(), e.getY());
            case EDITAR -> jogo.getEditor().mousePressionado(e.getX(), e.getY());
            default -> {}
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) { // Nome da AWT em Inglês
        switch (EstadosJogo.estadoJogo) {
            // Chamada interna do seu jogo em Português
            case MENU -> jogo.getMenu().mouseSolto(e.getX(), e.getY());
            case JOGANDO -> jogo.getJogando().mouseSolto(e.getX(), e.getY());
            case CONFIGURACOES -> jogo.getConfiguracoes().mouseSolto(e.getX(), e.getY());
            case EDITAR -> jogo.getEditor().mouseSolto(e.getX(), e.getY());
            default -> {}
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) { // Nome da AWT em Inglês
        // Não utilizado
    }

    @Override
    public void mouseExited(MouseEvent e) { // Nome da AWT em Inglês
        // Não utilizado
    }
}

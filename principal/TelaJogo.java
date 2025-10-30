package principal;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import entradas.OuvinteTeclado;
import entradas.MeuOuvinteMouse;

public class TelaJogo extends JPanel {

    private Jogo jogo;
    private Dimension tamanho;

    private MeuOuvinteMouse meuOuvinteMouse;
    private OuvinteTeclado ouvinteTeclado;

    public TelaJogo(Jogo jogo) {
        this.jogo = jogo;
        definirTamanhoPainel();
    }

    public void iniciarEntradas() {
        meuOuvinteMouse = new MeuOuvinteMouse(jogo);
        ouvinteTeclado = new OuvinteTeclado(jogo);

        addMouseListener(meuOuvinteMouse);
        addMouseMotionListener(meuOuvinteMouse);
        addKeyListener(ouvinteTeclado);

        // 🔧 Garantir que o painel aceite foco e receba eventos do teclado
        setFocusable(true);
        requestFocusInWindow();
    }

    private void definirTamanhoPainel() {
        tamanho = new Dimension(640, 800);

        setMinimumSize(tamanho);
        setPreferredSize(tamanho);
        setMaximumSize(tamanho);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        jogo.getRenderizacao().renderizar(g);
    }
}

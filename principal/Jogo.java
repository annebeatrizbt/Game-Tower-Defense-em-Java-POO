package principal;

import javax.swing.JFrame;
import ajuda.CarregarSalvar;
import gerenciadores.GerenciadorBlocos;
import cenas.Configuracoes;
import cenas.Editando;
import cenas.FimDeJogo;
import cenas.Jogando;
import cenas.Menu;

public class Jogo extends JFrame implements Runnable {

	private final double FPS_DEFINIDO = 120.0;
	private final double UPS_DEFINIDO = 60.0;

	private TelaJogo telaJogo;
	private Thread threadJogo;
	private Renderizacao renderizacao;

	private Menu menu;
	private Jogando jogando;
	private Configuracoes configuracoes;
	private Editando editando;
	private FimDeJogo fimDeJogo;
	private GerenciadorBlocos gerenciadorBlocos;

	public Jogo() {
		iniciarClasses();
		criarNivelPadrao();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Your Game");
		add(telaJogo);
		pack();
		setVisible(true);
	}

	private void criarNivelPadrao() {
		int[] vetor = new int[400];
		for (int i = 0; i < vetor.length; i++)
			vetor[i] = 0;

		CarregarSalvar.CriarNivel("new_level", vetor);
	}

	private void iniciarClasses() {
		gerenciadorBlocos = new GerenciadorBlocos();
		renderizacao = new Renderizacao(this);
		telaJogo = new TelaJogo(this);
		menu = new Menu(this);
		jogando = new Jogando(this);
		configuracoes = new Configuracoes(this);
		editando = new Editando(this);
		fimDeJogo = new FimDeJogo(this);
	}

	private void iniciar() {
		threadJogo = new Thread(this);
		threadJogo.start();
	}

	private void atualizarJogo() {
		switch (EstadosJogo.estadoJogo) {
			case EDITAR:
				editando.atualizar();
				break;
			case JOGANDO:
				jogando.atualizar();
				break;
			case MENU:
				break;
			case CONFIGURACOES:
				break;
			case FIM_DE_JOGO:
				// Adicionado para cobrir o novo estado, embora a cena FimDeJogo geralmente não precise de atualização constante
				break; 
			default:
				break;
		}
	}

	public static void main(String[] args) {
		Jogo jogo = new Jogo();
		jogo.telaJogo.iniciarEntradas();
		jogo.iniciar();
	}

	@Override
	public void run() {
		double tempoPorQuadro = 1000000000.0 / FPS_DEFINIDO;
		double tempoPorAtualizacao = 1000000000.0 / UPS_DEFINIDO;

		long ultimoQuadro = System.nanoTime();
		long ultimaAtualizacao = System.nanoTime();
		long ultimoVerificadorTempo = System.currentTimeMillis();

		int quadros = 0;
		int atualizacoes = 0;

		long agora;

		while (true) {
			agora = System.nanoTime();

			if (agora - ultimoQuadro >= tempoPorQuadro) {
				repaint();
				ultimoQuadro = agora;
				quadros++;
			}

			if (agora - ultimaAtualizacao >= tempoPorAtualizacao) {
				atualizarJogo();
				ultimaAtualizacao = agora;
				atualizacoes++;
			}

			if (System.currentTimeMillis() - ultimoVerificadorTempo >= 1000) {
				System.out.println("FPS: " + quadros + " | UPS: " + atualizacoes);
				quadros = 0;
				atualizacoes = 0;
				ultimoVerificadorTempo = System.currentTimeMillis();
			}
		}
	}

	public Renderizacao getRenderizacao() {
		return renderizacao;
	}

	public Menu getMenu() {
		return menu;
	}

	public Jogando getJogando() {
		return jogando;
	}

	public Configuracoes getConfiguracoes() {
		return configuracoes;
	}

	public Editando getEditor() {
		return editando;
	}
	
	public FimDeJogo getFimDeJogo() {
		return fimDeJogo;
	}

	public GerenciadorBlocos getGerenciadorBlocos() {
		return gerenciadorBlocos;
	}
}

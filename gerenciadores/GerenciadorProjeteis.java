package gerenciadores;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import inimigos.Inimigo;
import ajuda.CarregarSalvar;
import objetos.Projetil;
import objetos.Torre;
import cenas.Jogando;

import static ajuda.Constantes.Torres.*;
import static ajuda.Constantes.Projeteis.*;

public class GerenciadorProjeteis {

	private Jogando jogando;
	private ArrayList<Projetil> projeteis = new ArrayList<>();
	private ArrayList<Explosao> explosoes = new ArrayList<>();
	private BufferedImage[] imgsProjeteis, imgsExplosao;

	private int idProj = 0;

	public GerenciadorProjeteis(Jogando jogando) {
		this.jogando = jogando;
		importarImagens();
	}

	private void importarImagens() {
		BufferedImage atlas = CarregarSalvar.getAtlasSprites();
		imgsProjeteis = new BufferedImage[3];

		for (int i = 0; i < 3; i++)
			imgsProjeteis[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);

		importarExplosao(atlas);
	}

	private void importarExplosao(BufferedImage atlas) {
		imgsExplosao = new BufferedImage[7];

		for (int i = 0; i < 7; i++)
			imgsExplosao[i] = atlas.getSubimage(i * 32, 32 * 2, 32, 32);
	}

	public void novoProjetil(Torre t, Inimigo i) {
		int tipo = getTipoProjetil(t);

		int dx = (int) (t.getX() - i.getX());
		int dy = (int) (t.getY() - i.getY());
		int distTotal = Math.abs(dx) + Math.abs(dy);

		float porcentX = (float) Math.abs(dx) / distTotal;

		float velX = porcentX * ajuda.Constantes.Projeteis.GetVelocidade(tipo);
		float velY = ajuda.Constantes.Projeteis.GetVelocidade(tipo) - velX;

		if (t.getX() > i.getX())
			velX *= -1;
		if (t.getY() > i.getY())
			velY *= -1;

		float rot = 0;

		if (tipo == FLECHA) {
			float ang = (float) Math.atan(dy / (float) dx);
			rot = (float) Math.toDegrees(ang);
			if (dx < 0)
				rot += 180;
		}

		projeteis.add(new Projetil(
				t.getX() + 16,
				t.getY() + 16,
				velX,
				velY,
				t.getDano(),
				rot,
				idProj++,
				tipo));
	}

	public void atualizar() {
		for (Projetil p : projeteis) {
			if (p.estaAtivo()) {
				p.mover();

				if (atingiuInimigo(p)) {
					p.setAtivo(false);

					if (p.getTipoProjetil() == BOMBA) {
						explosoes.add(new Explosao(p.getPosicao()));
						atingirArea(p);
					}
				} else if (estaProjetilForaDosLimites(p)) {
					p.setAtivo(false);
				}
			}
		}

		for (Explosao e : explosoes)
			if (e.getIndex() < 7)
				e.update();
	}

	private void atingirArea(Projetil p) {
		for (Inimigo i : jogando.getGerenciadorInimigos().getInimigos()) {
			if (i.estaVivo()) {
				float raio = 40f;

				float dx = Math.abs(p.getPosicao().x - i.getX());
				float dy = Math.abs(p.getPosicao().y - i.getY());

				float dist = (float) Math.hypot(dx, dy);

				if (dist <= raio)
					i.sofrerDano(p.getDano());
			}
		}
	}

	private boolean atingiuInimigo(Projetil p) {
		for (Inimigo i : jogando.getGerenciadorInimigos().getInimigos()) {
			if (i.estaVivo()) {
				if (i.getLimites().contains(p.getPosicao())) {
					i.sofrerDano(p.getDano());
					if (p.getTipoProjetil() == CORRENTE)
						i.sofrerLentidao();
					return true;
				}
			}
		}
		return false;
	}

	private boolean estaProjetilForaDosLimites(Projetil p) {
		if (p.getPosicao().x >= 0)
			if (p.getPosicao().x <= 640)
				if (p.getPosicao().y >= 0)
					if (p.getPosicao().y <= 800)
						return false;
		return true;
	}

	public void desenhar(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		for (Projetil p : projeteis)
			if (p.estaAtivo()) {
				if (p.getTipoProjetil() == FLECHA) {
					g2d.translate(p.getPosicao().x, p.getPosicao().y);
					g2d.rotate(Math.toRadians(p.getRotacao()));
					g2d.drawImage(imgsProjeteis[p.getTipoProjetil()], -16, -16, null);
					g2d.rotate(-Math.toRadians(p.getRotacao()));
					g2d.translate(-p.getPosicao().x, -p.getPosicao().y);
				} else {
					g2d.drawImage(
							imgsProjeteis[p.getTipoProjetil()],
							(int) p.getPosicao().x - 16,
							(int) p.getPosicao().y - 16,
							null);
				}
			}

		desenharExplosoes(g2d);
	}

	private void desenharExplosoes(Graphics2D g2d) {
		for (Explosao e : explosoes)
			if (e.getIndex() < 7)
				g2d.drawImage(
						imgsExplosao[e.getIndex()],
						(int) e.getPos().x - 16,
						(int) e.getPos().y - 16,
						null);
	}

	private int getTipoProjetil(Torre t) {
		switch (t.getTipoTorre()) {
		case ARQUEIRO:
			return FLECHA;
		case CANHAO:
			return BOMBA;
		case MAGO:
			return CORRENTE;
		}
		return 0;
	}

	public void resetar() {
		projeteis.clear();
		explosoes.clear();

		idProj = 0;
	}

	public class Explosao {

		private Point2D.Float pos;
		private int tickExplosao, indiceExplosao;

		public Explosao(Point2D.Float pos) {
			this.pos = pos;
		}

		public void update() {
			tickExplosao++;
			if (tickExplosao >= 6) {
				tickExplosao = 0;
				indiceExplosao++;
			}
		}

		public int getIndex() {
			return indiceExplosao;
		}

		public Point2D.Float getPos() {
			return pos;
		}
	}
}

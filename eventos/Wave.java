package eventos;

import java.util.ArrayList;

public class Wave {

	private ArrayList<Integer> listaInimigos;

	public Wave(ArrayList<Integer> listaInimigos) {
		this.listaInimigos = listaInimigos;
	}

	public ArrayList<Integer> getListaInimigos() {
		return listaInimigos;
	}
}

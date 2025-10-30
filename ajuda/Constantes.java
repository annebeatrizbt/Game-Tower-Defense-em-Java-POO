package ajuda; 

public class Constantes { 

    public static class Projeteis { 
        public static final int FLECHA = 0; 
        public static final int BOMBA = 1; 
        public static final int CORRENTES = 2; 

        public static float GetVelocidade(int tipo) { 
            switch (tipo) {
            case FLECHA:
                return 3f;
            case BOMBA:
                return 1f;
            case CORRENTES:
                return 2f;
            }
            return 0f;
        }
    }

    public static class Torres { 
        public static final int CANHAO = 0; 
        public static final int ARQUEIRO = 1; 
        public static final int MAGO = 2; 

        public static String GetNome(int tipoTorre) { 
            switch (tipoTorre) {
            case CANHAO:
                return "Canhão"; 
            case ARQUEIRO:
                return "Arqueiro"; 
            case MAGO:
                return "Mago"; 
            }
            return "";
        }

        public static float GetDanoInicial(int tipoTorre) { 
            switch (tipoTorre) {
            case CANHAO:
                return 25;
            case ARQUEIRO:
                return 15;
            case MAGO:
                return 5;
            }

            return 0;
        }

        public static float GetAlcancePadrao(int tipoTorre) { 
            switch (tipoTorre) {
            case CANHAO:
                return 100;
            case ARQUEIRO:
                return 100;
            case MAGO:
                return 100;
            }

            return 0;
        }

        public static float GetTempoRecargaPadrao(int tipoTorre) { 
            switch (tipoTorre) {
            case CANHAO:
                return 10;
            case ARQUEIRO:
                return 10;
            case MAGO:
                return 10;
            }

            return 0;
        }
    }
    public static class Direcao{
		public static final int ESQUERDA = 0;
		public static final int CIMA = 1;
		public static final int DIREITA = 2;
		public static final int BAIXO = 3;
	}

	public static class Inimigos{

		public static final int ORC = 0;
		public static final int MORCEGO = 1;
		public static final int CAVALEIRO = 2;
		public static final int LOBO = 3;

		public static float GetVelocidade(int tipoInimigo) {
			switch (tipoInimigo) {
			case ORC:
				return 0.5f;
			case MORCEGO:
				return 0.65f;
			case CAVALEIRO:
				return 0.3f;
			case LOBO:
				return 0.75f;
			}
			return 0;
		}

		public static int GetVidaInicial(int tipoInimigo) {
			switch (tipoInimigo) {
			case ORC:
				return 100;
			case MORCEGO:
				return 60;
			case CAVALEIRO:
				return 250;
			case LOBO:
				return 85;
			}
			return 0;
		}
	}

	public static class Blocos {
		public static final int BLOCO_AGUA = 0;
		public static final int BLOCO_GRAMA = 1;
		public static final int BLOCO_ESTRADA = 2;
	}

}


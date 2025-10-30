package ajuda;

import java.util.ArrayList;

public class Utilitarios {

    public static int[][] ArrayListParaMatriz2Dint(ArrayList<Integer> lista, int altura, int largura) {
        int[][] novaMatriz = new int[altura][largura];

        for (int j = 0; j < novaMatriz.length; j++)
            for (int i = 0; i < novaMatriz[j].length; i++) {
                // CORRIGIDO: Deve ser 'j * largura + i'
                int indice = j * largura + i; 
                
                // Adicionada uma verificação de segurança para evitar falhas
                if(indice < lista.size())
                    novaMatriz[j][i] = lista.get(indice);
            }

        return novaMatriz;

    }

    public static int[] Vetor2DparaVetor1Dint(int[][] matriz) {
        int[] vetor = new int[matriz.length * matriz[0].length];

        for (int j = 0; j < matriz.length; j++)
            for (int i = 0; i < matriz[j].length; i++) {
                // CORRIGIDO: Deve ser 'j * matriz[0].length + i' (usando a largura)
                int indice = j * matriz[0].length + i;
                vetor[indice] = matriz[j][i];
            }

        return vetor;
    }

    public static int GetDistanciaHipotenusa(float x1, float y1, float x2, float y2) {

        float difX = Math.abs(x1 - x2);
        float difY = Math.abs(y1 - y2);

        return (int) Math.hypot(difX, difY);

    }

}
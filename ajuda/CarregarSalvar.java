package ajuda;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import objetos.PontoCaminho;
import ajuda.Utilitarios; 

public class CarregarSalvar {

    private static final String PASTA_BASE = System.getProperty("user.home") + "/meuJogoNiveis/";

    static {
        File dir = new File(PASTA_BASE);
        if (!dir.exists()) dir.mkdirs();
    }

    // === SPRITES ===
    public static BufferedImage getAtlasSprites() {
        try (InputStream is = CarregarSalvar.class.getResourceAsStream("/spriteatlas.png")) {
            if (is == null) {
                System.err.println("Erro: spriteatlas.png não encontrado!");
                return null;
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // === ARQUIVOS ===
    public static void CriarNivel(String nome, int[] idArr) {
        File novoNivel = new File(PASTA_BASE + nome + ".txt");

        if (novoNivel.exists()) {
            System.out.println("Arquivo: " + nome + " já existe!");
            return;
        }

        try {
            novoNivel.createNewFile();
            EscreverNoArquivo(novoNivel, idArr, new PontoCaminho(0, 0), new PontoCaminho(0, 0));
            System.out.println("Novo nível criado em: " + novoNivel.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SalvarNivel(String nome, int[][] nivel, PontoCaminho inicio, PontoCaminho fim) {
        File arquivoNivel = new File(PASTA_BASE + nome + ".txt");

        if (arquivoNivel.exists()) {
            // CORREÇÃO 1: Converter int[][] para int[] antes de escrever
            int[] nivel1D = Utilitarios.Vetor2DparaVetor1Dint(nivel);
            EscreverNoArquivo(arquivoNivel, nivel1D, inicio, fim);
            System.out.println("Nível salvo com sucesso: " + nome);
        } else {
            System.out.println("Arquivo: " + nome + " não existe!");
        }
    }

    private static void EscreverNoArquivo(File f, int[] idArr, PontoCaminho inicio, PontoCaminho fim) {
        try (PrintWriter pw = new PrintWriter(f)) {

            // salva o tamanho do array
            pw.println(idArr.length);

            // salva cada id
            for (int id : idArr)
                pw.println(id);

            // CORREÇÃO 4: Usar getCoordX e getCoordY
            pw.println(inicio.getxCord());
            pw.println(inicio.getyCord());
            pw.println(fim.getxCord());
            pw.println(fim.getyCord());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Integer> LerDoArquivo(File arquivo) {
        ArrayList<Integer> lista = new ArrayList<>();

        try (Scanner sc = new Scanner(arquivo)) {
            while (sc.hasNextLine()) {
                lista.add(Integer.parseInt(sc.nextLine().trim()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // === LEITURA DO MAPA ===
    
    // CORREÇÃO 2: Mudar o retorno de int[] para int[][]
    public static int[][] GetDadosNivel(String nome) {
        File arquivoNvl = new File(PASTA_BASE + nome + ".txt");

        if (!arquivoNvl.exists()) {
            System.out.println("Arquivo: " + nome + " não existe!");
            return null;
        }

        ArrayList<Integer> lista = LerDoArquivo(arquivoNvl);

        if (lista.size() < 5) {
            System.out.println("Arquivo inválido");
            return null;
        }

        int tamanho = lista.get(0); // Ex: 400
        
        // Criar uma sub-lista contendo apenas os dados do nível (removendo o tamanho e os pontos de caminho)
        ArrayList<Integer> dadosBlocos = new ArrayList<>(lista.subList(1, tamanho + 1));

        // Converter a lista 1D de volta para uma matriz 2D (assumindo 20x20)
        // Se sua classe Utilitarios não tiver ArrayListParaMatriz2Dint, você precisará adicioná-la.
        return Utilitarios.ArrayListParaMatriz2Dint(dadosBlocos, 20, 20);
    }

    public static ArrayList<PontoCaminho> GetPontosCaminhoNivel(String nome) {
        File arquivoNvl = new File(PASTA_BASE + nome + ".txt");

        if (!arquivoNvl.exists()) {
            System.out.println("Arquivo: " + nome + " não existe!");
            return null;
        }

        ArrayList<Integer> lista = LerDoArquivo(arquivoNvl);

        if (lista.size() < 5) {
            System.out.println("Arquivo incompleto!");
            return null;
        }

        int tamanho = lista.get(0);

        int inicioX = lista.get(1 + tamanho);
        int inicioY = lista.get(2 + tamanho);
        int fimX = lista.get(3 + tamanho);
        int fimY = lista.get(4 + tamanho);

        ArrayList<PontoCaminho> pontos = new ArrayList<>();
        pontos.add(new PontoCaminho(inicioX, inicioY));
        pontos.add(new PontoCaminho(fimX, fimY));

        return pontos;
    }
}
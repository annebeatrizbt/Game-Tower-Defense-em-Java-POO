package ajuda;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class AjusteImg {

    public static BufferedImage getImgRotacionada(BufferedImage img, int anguloRotacao) {

        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage novaImg = new BufferedImage(w, h, img.getType());
        Graphics2D g2d = novaImg.createGraphics();

        g2d.rotate(Math.toRadians(anguloRotacao), w / 2, h / 2);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return novaImg;

    }

    public static BufferedImage montarImg(BufferedImage[] imagens) {
        int w = imagens[0].getWidth();
        int h = imagens[0].getHeight();

        BufferedImage novaImg = new BufferedImage(w, h, imagens[0].getType());
        Graphics2D g2d = novaImg.createGraphics();

        for (BufferedImage img : imagens) {
            g2d.drawImage(img, 0, 0, null);
        }

        g2d.dispose();
        return novaImg;

    }

    public static BufferedImage getImgMontadaRotacionada(BufferedImage[] imagens, int anguloRotacao, int rotacionarNoIndice) {
        int w = imagens[0].getWidth();
        int h = imagens[0].getHeight();

        BufferedImage novaImg = new BufferedImage(w, h, imagens[0].getType());
        Graphics2D g2d = novaImg.createGraphics();

        for (int i = 0; i < imagens.length; i++) {
            if (rotacionarNoIndice == i)
                g2d.rotate(Math.toRadians(anguloRotacao), w / 2, h / 2);
            g2d.drawImage(imagens[i], 0, 0, null);
            if (rotacionarNoIndice == i)
                g2d.rotate(Math.toRadians(-anguloRotacao), w / 2, h / 2);
        }

        g2d.dispose();
        return novaImg;

    }

    public static BufferedImage[] getImgMontadaRotacionada(BufferedImage[] imagens, BufferedImage segundaImagem, int anguloRotacao) {
        int w = imagens[0].getWidth();
        int h = imagens[0].getHeight();

        BufferedImage[] vetor = new BufferedImage[imagens.length];

        for (int i = 0; i < imagens.length; i++) {
            BufferedImage novaImg = new BufferedImage(w, h, imagens[0].getType());
            Graphics2D g2d = novaImg.createGraphics();

            g2d.drawImage(imagens[i], 0, 0, null);
            g2d.rotate(Math.toRadians(anguloRotacao), w / 2, h / 2);
            g2d.drawImage(segundaImagem, 0, 0, null);
            g2d.dispose();

            vetor[i] = novaImg;
        }

        return vetor;

    }

}

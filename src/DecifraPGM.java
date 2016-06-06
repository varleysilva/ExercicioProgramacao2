
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.image.BufferedImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author varleysilva
 */
public class DecifraPGM {

    int div;

    public void setDiv(int div) {
        this.div = div;
    }

    public static int getDiv() {
        int div = 0;
        return div;
    }

    int matriz[] = {0, -1, 0, -1, 5, -1, 0, -1, 0};

    public static String le_linha(FileInputStream arquivo) {
        String linha = "";
        byte bb;
        try {
            while ((bb = (byte) arquivo.read()) != '\n') {
                linha += (char) bb;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Linha: " + linha);
        return linha;
    }

    public static void decodeNegativo(String file_diretorio) throws FileNotFoundException {
//       String diretorio = file_diretorio = file_diretorio.substring(1);
        System.out.print("FALA NOIS NO DECODE");
        try {
            FileInputStream arquivo = new FileInputStream(file_diretorio);
            BufferedImage imagem_pgm = null, imagem_pgm_negativo = null;
            System.out.println(file_diretorio + "diretorio");
            int width = 0;
            int height = 0;
            int maxVal = 0;
            int count = 0;
            byte bb;

            String linha = PGMreader.le_linha(arquivo);
            if ("P5".equals(linha)) {
                linha = PGMreader.le_linha(arquivo);
                while (linha.startsWith("#")) {
                    linha = PGMreader.le_linha(arquivo);
                }
                Scanner in = new Scanner(linha);
                if (in.hasNext() && in.hasNextInt()) {
                    width = in.nextInt();
                } else {
                    System.out.println("Arquivo corrompido");
                }
                if (in.hasNext() && in.hasNextInt()) {
                    height = in.nextInt();
                } else {
                    System.out.println("Arquivo corrompido");
                }
                linha = PGMreader.le_linha(arquivo);
                in.close();
                in = new Scanner(linha);
                maxVal = in.nextInt();
                in.close();

                imagem_pgm = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
                imagem_pgm_negativo = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
                byte[] pixels = ((DataBufferByte) imagem_pgm.getRaster().getDataBuffer()).getData();
                byte[] pixels_negativo = ((DataBufferByte) imagem_pgm_negativo.getRaster().getDataBuffer()).getData();

                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        bb = (byte) arquivo.read();
                        pixels[count] = bb;
                        pixels_negativo[count] = (byte) (maxVal - bb);
                        count++;
                    }
                }

//				while(count < (height*width)) {
//					bb = (byte) arquivo.read();
//					pixels[count] = bb;
//					pixels_negativo[count] = (byte) (maxVal - bb); 
//					count++;
//				}				
            } else {
                System.out.println("Arquivo inválido");
            }

            System.out.println("Height=" + height);
            System.out.println("Width=" + width);
            System.out.println("Total de Pixels = " + (width * height));
            System.out.println("Total de Pixels lidos = " + count);
            JFrame frame = new JFrame();
            frame.getContentPane().setLayout(new FlowLayout());
            frame.getContentPane().add(new JLabel(new ImageIcon(imagem_pgm)));
            frame.getContentPane().add(new JLabel(new ImageIcon(imagem_pgm_negativo)));
            frame.pack();
            frame.setVisible(true);
            arquivo.close();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            return;
        }

    }

    public static void decodeSharpen(String file_diretorio) throws FileNotFoundException {
        int value, i;
        int j;
        int matriz[] = {0, -1, 0, -1, 5, -1, 0, -1, 0};
        System.out.print("FALA NOIS NO DECODE");
        try {
            FileInputStream arquivo = new FileInputStream(file_diretorio);
            BufferedImage imagem_pgm = null, imagem_pgm_negativo = null;
            System.out.println(file_diretorio + "diretorio");
            int width = 0;
            int height = 0;
            int maxVal = 0;
            int count = 0;
            byte bb;

            String linha = PGMreader.le_linha(arquivo);
            if ("P5".equals(linha)) {
                linha = PGMreader.le_linha(arquivo);
                while (linha.startsWith("#")) {
                    linha = PGMreader.le_linha(arquivo);
                }
                Scanner in = new Scanner(linha);
                if (in.hasNext() && in.hasNextInt()) {
                    width = in.nextInt();
                } else {
                    System.out.println("Arquivo corrompido");
                }
                if (in.hasNext() && in.hasNextInt()) {
                    height = in.nextInt();
                } else {
                    System.out.println("Arquivo corrompido");
                }
                linha = PGMreader.le_linha(arquivo);
                in.close();
                in = new Scanner(linha);
                maxVal = in.nextInt();
                in.close();

                imagem_pgm = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
                imagem_pgm_negativo = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
                byte[] pixels = ((DataBufferByte) imagem_pgm.getRaster().getDataBuffer()).getData();
                byte[] pixels_negativo = ((DataBufferByte) imagem_pgm_negativo.getRaster().getDataBuffer()).getData();

                int size = height * width;
                //bb = (byte) arquivo.read();
                //pixels[count] = bb;

                for (i = 0; i < size / 2; i++) {
                    for (j = 0; j < height; j++) {
                        pixels_negativo[count] = pixels[i * height + j];
                        pixels_negativo[((j - height * i) + (height - 1) * width)] = (byte)(pixels[(j - height * i) + (height - 1) * width]);
                    }
                }
                                System.out.println("TESTE");

                
                
                
                
                
                
                
                
                
                
                
                
                
                
            
        

            

//						
        }else {
                System.out.println("Arquivo inválido");
            }

        System.out.println("Height=" + height);
        System.out.println("Width=" + width);
        System.out.println("Total de Pixels = " + (width * height));
        System.out.println("Total de Pixels lidos = " + count);
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(imagem_pgm)));
        frame.getContentPane().add(new JLabel(new ImageIcon(imagem_pgm_negativo)));
        frame.pack();
        frame.setVisible(true);
        arquivo.close();
    }
    catch (Throwable t

    
        ) {
            t.printStackTrace(System.err);
        return;
    }
}
}

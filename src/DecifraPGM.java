
import java.awt.FlowLayout;
import java.awt.image.DataBufferByte;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
            if ("P5".equals(linha) || "P5".equals(linha)) {
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

     public static void decodeSharpen(String file_diretorio) throws FileNotFoundException, IOException{
  
//		try {
		FileInputStream arquivo = new FileInputStream(file_diretorio);
		BufferedImage imagem_pgm = null, imagem_pgm_sharpen = null;
                System.out.println(file_diretorio  + "diretorio");
                int width = 0;
		int height = 0;
		int maxVal = 0;
		int count = 0;
		byte bb;
                int matriz[] = {0, -1, 0, -1, 5, -1, 0, -1, 0};
		
                String linha = DecifraPGM.le_linha(arquivo);
		if("P5".equals(linha)) {
                    linha = DecifraPGM.le_linha(arquivo);
                    while (linha.startsWith("#")) {
			linha = DecifraPGM.le_linha(arquivo);
                    }
		    Scanner in = new Scanner(linha); 
		    if(in.hasNext() && in.hasNextInt())
                    width = in.nextInt();
		    else
                    System.out.println("Arquivo corrompido");
		    if(in.hasNext() && in.hasNextInt())
		    	height = in.nextInt();
		    else
		    	System.out.println("Arquivo corrompido");
			linha = DecifraPGM.le_linha(arquivo);
			in.close();
			in = new Scanner(linha);
			maxVal = in.nextInt();
			in.close();
			
			imagem_pgm = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			imagem_pgm_sharpen = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			byte [] pixels = ((DataBufferByte) imagem_pgm.getRaster().getDataBuffer()).getData();
			byte [] pixels_sharpen = ((DataBufferByte) imagem_pgm_sharpen.getRaster().getDataBuffer()).getData();
			int size = (height*width);
                        int i = 0;
                        int parou = 0;
                    while(count < (height*width)) {
                        if(parou == 8){
                            parou = 0;
                        }
                            bb = (byte) arquivo.read();
                            pixels[count] = bb;
                            for(i = parou; i <  9; i++){
                                if(i==0){
                                    pixels_sharpen[count] = (byte) (bb); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==1){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==2){
                                    pixels_sharpen[count] = (byte) (bb); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==3){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==4){
                                    pixels_sharpen[count] = (byte) (bb+5); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==5){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==6){
                                    pixels_sharpen[count] = (byte) (bb); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i == 7){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==8){
                                    pixels_sharpen[count] = (byte) (bb); 
                                    count++;
                                    parou++;
                                    break;
                                }
                            }
			}				
			} 
			else {
                            System.out.println("Arquivo inválido");
			}   
			
			System.out.println("Height=" + height);
			System.out.println("Width=" + width);
			System.out.println("Total de Pixels = " + (width * height));
			System.out.println("Total de Pixels lidos = " + count);
                        JFrame frame = new JFrame();
			frame.getContentPane().setLayout(new FlowLayout());
			frame.getContentPane().add(new JLabel(new ImageIcon(imagem_pgm)));
			frame.getContentPane().add(new JLabel(new ImageIcon(imagem_pgm_sharpen)));
			frame.pack();
			frame.setVisible(true);
                        arquivo.close();
   }
   
   public static void decodeSmooth(String file_diretorio) throws FileNotFoundException, IOException{
                FileInputStream arquivo = new FileInputStream(file_diretorio);
		BufferedImage imagem_pgm = null, imagem_pgm_smooth = null;
                System.out.println(file_diretorio  + "diretorio");
                int width = 0;
		int height = 0;
		int maxVal = 0;
		int count = 0;
		byte bb;
                int matriz[] = {0, -1, 0, -1, 5, -1, 0, -1, 0};
		
                String linha = DecifraPGM.le_linha(arquivo);
		if("P5".equals(linha)) {
                    linha = DecifraPGM.le_linha(arquivo);
                    while (linha.startsWith("#")) {
			linha = DecifraPGM.le_linha(arquivo);
                    }
		    Scanner in = new Scanner(linha); 
		    if(in.hasNext() && in.hasNextInt())
                    width = in.nextInt();
		    else
                    System.out.println("Arquivo corrompido");
		    if(in.hasNext() && in.hasNextInt())
		    	height = in.nextInt();
		    else
		    	System.out.println("Arquivo corrompido");
			linha = DecifraPGM.le_linha(arquivo);
			in.close();
			in = new Scanner(linha);
			maxVal = in.nextInt();
			in.close();
			
			imagem_pgm = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			imagem_pgm_smooth = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			byte [] pixels = ((DataBufferByte) imagem_pgm.getRaster().getDataBuffer()).getData();
			byte [] pixels_sharpen = ((DataBufferByte) imagem_pgm_smooth.getRaster().getDataBuffer()).getData();
			int size = (height*width);
                        int i = 0;
                        int parou = 0;
                    while(count < (height*width)) {
                        if(parou == 8){
                            parou = 0;
                        }
                            bb = (byte) arquivo.read();
                            pixels[count] = bb;
                            for(i = parou; i <  9; i++){
                                if(i==0){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==1){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==2){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==3){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==4){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==5){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==6){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i == 7){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                                else if(i==8){
                                    pixels_sharpen[count] = (byte) (bb-1); 
                                    count++;
                                    parou++;
                                    break;
                                }
                            }
			}				
			} 
			else {
                            System.out.println("Arquivo inválido");
			}   
			
			System.out.println("Height=" + height);
			System.out.println("Width=" + width);
			System.out.println("Total de Pixels = " + (width * height));
			System.out.println("Total de Pixels lidos = " + count);
                        JFrame frame = new JFrame();
			frame.getContentPane().setLayout(new FlowLayout());
			frame.getContentPane().add(new JLabel(new ImageIcon(imagem_pgm)));
			frame.getContentPane().add(new JLabel(new ImageIcon(imagem_pgm_smooth)));
			frame.pack();
			frame.setVisible(true);
                        arquivo.close();
   }
}

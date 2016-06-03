
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author varleysilva
 */
abstract class DecifraPGM {
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
   public static double Decode(String file) throws FileNotFoundException{
       try {
			FileInputStream arquivo = new FileInputStream(file);
			BufferedImage imagem_pgm = null, imagem_pgm_negativo = null;
		    int width = 0;
		    int height = 0;
		    int maxVal = 0;
			int count = 0;
			byte bb;
			
			String linha = PGMreader.le_linha(arquivo);
			if("P5".equals(linha)) {
				linha = PGMreader.le_linha(arquivo);
				while (linha.startsWith("#")) {
					linha = PGMreader.le_linha(arquivo);
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
				linha = PGMreader.le_linha(arquivo);
				in.close();
				in = new Scanner(linha);
				maxVal = in.nextInt();
				in.close();
				
				imagem_pgm = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
				imagem_pgm_negativo = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
				byte [] pixels = ((DataBufferByte) imagem_pgm.getRaster().getDataBuffer()).getData();
				byte [] pixels_negativo = ((DataBufferByte) imagem_pgm_negativo.getRaster().getDataBuffer()).getData();
				
				while(count < (height*width)) {
					bb = (byte) arquivo.read();
					pixels[count] = bb;
					pixels_negativo[count] = (byte) (maxVal - bb); 
					count++;
				}				
			}
			else {
				System.out.println("Arquivo inválido");
			}
			
			System.out.println("Height=" + height);
			System.out.println("Width=" + width);
			System.out.println("Total de Pixels = " + (width * height));
			System.out.println("Total de Pixels lidos = " + count);
                        arquivo.close();
   }
                    catch(Throwable t) {
			t.printStackTrace(System.err) ;
			return 0;
		}
       return 0;
}
}


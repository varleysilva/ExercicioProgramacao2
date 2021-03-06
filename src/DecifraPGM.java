
import java.awt.FlowLayout;
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
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.BufferedWriter;
import java.io.FileWriter;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author varleysilva
 */
public class DecifraPGM extends Decifra {

    private static void setPixels_Sharpen(byte[] pixels_Sharpen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void setS_imagem(BufferedImage imagem_pgm_sharpen) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    String file_arquivo;

    private int[][] pixels;
    private byte[] bytes;

    DecifraPGM(String path) {
        this.mountImage(path);
    }

    private void setPixels(int height, int width) {
        this.pixels = new int[height][width];
    }

    private void setPixel(int y, int x, int color) {
        this.pixels[y][x] = color;
    }

    public int getPixel(int y, int x) {
        return this.pixels[y][x];
    }

    private void setBytes() {
        this.bytes = new byte[this.getSize()];
    }

    private void setByte(int pos, byte bb) {
        this.bytes[pos] = bb;
    }

    public byte getByte(int pos) {
        return this.bytes[pos];
    }

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

    public void mountImage(String path) {
        this.open(path, BufferedImage.TYPE_INT_RGB);
        this.setPixels(this.getHeight(), this.getWidth());
        this.setBytes();

        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                try {
                    int color = this.getFile().read();
                    byte bb = (byte) color;
                    this.setByte(this.getWidth() * y + x, bb);

                    if (color < 0 || color > this.getMaxGrey()) {
                        color = 0;
                    }

                    //System.out.println(pos++);
                    this.setPixel(y, x, color);
                    this.getPicture().setRGB(x, y, new Color(color, color, color).getRGB());
                } catch (Throwable t) {
                    t.printStackTrace(System.err);
                }
            }
        }
        this.decodeMessage(path);
    }

    public static void decodeNegativo(String file_diretorio) throws FileNotFoundException {
        try {
            FileInputStream arquivo = new FileInputStream(file_diretorio);
            BufferedImage imagem_pgm = null, imagem_pgm_negativo = null;
            System.out.println(file_diretorio + "diretorio");
            int width = 0;
            int height = 0;
            int maxVal = 0;
            int count = 0;
            byte bb;

            String linha = Decifra.le_linha(arquivo);
            if ("P5".equals(linha)) {
                linha = Decifra.le_linha(arquivo);
                while (linha.startsWith("#")) {
                    linha = Decifra.le_linha(arquivo);
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
                linha = Decifra.le_linha(arquivo);
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
		
            } else {
                System.out.println("Arquivo inválido");
            }

            System.out.println("Height=" + height);
            System.out.println("Width=" + width);
            System.out.println("Total de Pixels = " + (width * height));
            System.out.println("Total de Pixels lidos = " + count);
            JFrame frame = new JFrame();
            frame.getContentPane().add(new JLabel(new ImageIcon(imagem_pgm_negativo)));
            frame.pack();
            frame.setVisible(true);
            arquivo.close();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
            return;
        }

    }

    public static void decodeSharpen(String file_diretorio) throws FileNotFoundException, IOException {


        FileInputStream arquivo = new FileInputStream(file_diretorio);
        BufferedImage imagem_pgm = null, imagem_pgm_sharpen = null;
        System.out.println(file_diretorio + "diretorio");
        int width = 0;
        int height = 0;
        int maxVal = 0;
        int count = 0;
        byte bb;
        int matriz[] = {0, -1, 0, -1, 5, -1, 0, -1, 0};

        String linha = DecifraPGM.le_linha(arquivo);
        if ("P5".equals(linha)) {
            linha = DecifraPGM.le_linha(arquivo);
            while (linha.startsWith("#")) {
                linha = DecifraPGM.le_linha(arquivo);
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
            linha = DecifraPGM.le_linha(arquivo);
            in.close();
            in = new Scanner(linha);
            maxVal = in.nextInt();
            in.close();

//            imagem_pgm = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
//            imagem_pgm_sharpen = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
//            byte[] pixels = ((DataBufferByte) imagem_pgm.getRaster().getDataBuffer()).getData();
//            byte[] pixels_sharpen = ((DataBufferByte) imagem_pgm_sharpen.getRaster().getDataBuffer()).getData();
            
            imagem_pgm_sharpen = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            byte[] pixels_Sharpen = ((DataBufferByte) imagem_pgm_sharpen.getRaster().getDataBuffer()).getData();
            
            while (count < (height * width)){
                bb = (byte) arquivo.read();
                pixels_Sharpen[count] = bb;
                count++;
            }
            System.out.println("teste");
            Kernel kernel = new Kernel(3, 3, new float[] {0, -1, 0, -1, 5, -1, 0, 1, 0});
            BufferedImageOp op = new ConvolveOp(kernel);
            imagem_pgm_sharpen = op.filter(imagem_pgm_sharpen, null);
            setPixels_Sharpen(pixels_Sharpen);
            setS_imagem(imagem_pgm_sharpen);
            arquivo.close();
          
    
        } else {
            System.out.println("Arquivo inválido");
        }

        System.out.println("Height=" + height);
        System.out.println("Width=" + width);
        System.out.println("Total de Pixels = " + (width * height));
        System.out.println("Total de Pixels lidos = " + count);
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        //frame.getContentPane().add(new JLabel(new ImageIcon(imagem_pgm)));
        frame.getContentPane().add(new JLabel(new ImageIcon(imagem_pgm_sharpen)));
        frame.pack();
        frame.setVisible(true);
        arquivo.close();
    }

    public static void decodeSmooth(String file_diretorio) throws FileNotFoundException, IOException {
        FileInputStream arquivo = new FileInputStream(file_diretorio);
        BufferedImage imagem_pgm = null, imagem_pgm_smooth = null;
        System.out.println(file_diretorio + "diretorio");
        int width = 0;
        int height = 0;
        int maxVal = 0;
        int count = 0;
        byte bb;
        int matriz[] = {0, -1, 0, -1, 5, -1, 0, -1, 0};

        String linha = DecifraPGM.le_linha(arquivo);
        if ("P5".equals(linha)) {
            linha = DecifraPGM.le_linha(arquivo);
            while (linha.startsWith("#")) {
                linha = DecifraPGM.le_linha(arquivo);
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
            linha = DecifraPGM.le_linha(arquivo);
            in.close();
            in = new Scanner(linha);
            maxVal = in.nextInt();
            in.close();

            imagem_pgm = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            imagem_pgm_smooth = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            byte[] pixels = ((DataBufferByte) imagem_pgm.getRaster().getDataBuffer()).getData();
            byte[] pixels_sharpen = ((DataBufferByte) imagem_pgm_smooth.getRaster().getDataBuffer()).getData();
            int size = (height * width);
            int i = 0;
            int parou = 0;
            while (count < (height * width)) {
                if (parou == 8) {
                    parou = 0;
                }
                bb = (byte) arquivo.read();
                pixels[count] = bb;
                for (i = parou; i < 9; i++) {
                    if (i == 0) {
                        pixels_sharpen[count] = (byte) (bb + maxVal);
                        count++;
                        parou++;
                        break;
                    } else if (i == 1) {
                        pixels_sharpen[count] = (byte) (bb + maxVal);
                        count++;
                        parou++;
                        break;
                    } else if (i == 2) {
                        pixels_sharpen[count] = (byte) (bb + maxVal);
                        count++;
                        parou++;
                        break;
                    } else if (i == 3) {
                        pixels_sharpen[count] = (byte) (bb + maxVal);
                        count++;
                        parou++;
                        break;
                    } else if (i == 4) {
                        pixels_sharpen[count] = (byte) (bb + maxVal);
                        count++;
                        parou++;
                        break;
                    } else if (i == 5) {
                        pixels_sharpen[count] = (byte) (bb + maxVal);
                        count++;
                        parou++;
                        break;
                    } else if (i == 6) {
                        pixels_sharpen[count] = (byte) (bb + maxVal);
                        count++;
                        parou++;
                        break;
                    } else if (i == 7) {
                        pixels_sharpen[count] = (byte) (bb + maxVal);
                        count++;
                        parou++;
                        break;
                    } else if (i == 8) {
                        pixels_sharpen[count] = (byte) (bb + maxVal);
                        count++;
                        parou++;
                        break;
                    } else if (i == 9) {
                        pixels_sharpen[count] = (byte) (bb + maxVal);
                        count++;
                        parou++;
                        break;
                    }
                }
            }
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
        frame.getContentPane().add(new JLabel(new ImageIcon(imagem_pgm_smooth)));
        frame.pack();
        frame.setVisible(true);
        arquivo.close();
    }

    public void decodeMessage(String path) {
        String text = "";
        
        char character = 0;
        int count = 1;
        int start = Integer.parseInt(this.getComment().replaceAll("[\\D]", ""));

        for (int pos = start; pos < this.getSize(); pos++) {
            character <<= 1;
            character |= this.getByte(pos) & 0x01;
            if (count == 8) {             // Is it the least significant bit ? 
                if (character == '#') {   // Is it the end of the message ?
                    break;
                }

                text += (char) character;
                count = 0;
                character = 0;

                
            }
            count++;

        }
        String filename;
        BufferedWriter writer = null;
                try {
                 writer = new BufferedWriter( new FileWriter(text));
                    System.out.println(path.substring(0, path.lastIndexOf(".")));
                    writer.write(text);

                } catch (IOException e) {
                } finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (IOException e) {
                    }
                }

        JOptionPane.showMessageDialog(null, text);
        JOptionPane.showMessageDialog(null, "Mensagem salva na pasta raiz do programa!");


    }
}

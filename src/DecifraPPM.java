
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kairon
 */
public class DecifraPPM extends Picture {

    private int[][][] pixels;

    DecifraPPM(String path) {
        
        this.mountImage(path);
    }

    private void setPixels(int height, int width) {
        this.pixels = new int[height][width][3];
    }

    // Fill every column of a pixel.
    private void setPixel(int y, int x, int c, int value) {
        this.pixels[y][x][c] = value;
    }

    // Get the array of pixel
    public int[] getPixel(int y, int x) {
        return this.pixels[y][x];
    }

    public void mountImage(String path) {
       
        this.open(path, BufferedImage.TYPE_INT_RGB);
        this.setPixels(this.getHeight(), this.getWidth());

        int y = 0;
        int x = 0;
        for (y = 0; y < this.getHeight(); y++) {
            for (x = 0; x < this.getWidth(); x++) {
                try {
                    int red = this.getFile().read();
                    int green = this.getFile().read();
                    int blue = this.getFile().read();

                    //System.out.println(red+" "+green+" "+blue);
                    this.setPixel(y, x, 0, red);
                    this.setPixel(y, x, 1, green);
                    this.setPixel(y, x, 2, blue);

                    Color pixel = new Color(red, green, blue);
                    this.getPicture().setRGB(x, y, pixel.getRGB());
                } catch (Throwable t) {
                    t.printStackTrace(System.err);
                }
            }
        }
        
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(this.getPicture())));
        frame.pack();
        frame.setVisible(true);
    }

    public void applyFilterRGB(int color) {
        int y = 0;
        int x = 0;
        for (y = 0; y < this.getHeight(); y++) {
            for (x = 0; x < this.getWidth(); x++) {
                int[] rgb = new int[3];
                rgb[color] = this.pixels[y][x][color];
                this.getPicture().setRGB(x, y, new Color(rgb[0], rgb[1], rgb[2]).getRGB());
            }
        }
    }
    
}

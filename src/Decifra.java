
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;



abstract public class Decifra{

    private FileInputStream file;
    private BufferedImage picture;
    private String magic, comment;
    private int width, height, size, maxGrey;

    Decifra() {
        this.setPicture(null);
        this.setComment(null);
    }

    private void setFile(String path) {
        try {
            this.file = new FileInputStream(path);
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    public void close() {
        try {
            this.getFile().close();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    public FileInputStream getFile() {
        return this.file;
    }

    private void setPicture(BufferedImage picture) {
        this.picture = picture;
    }

    public BufferedImage getPicture() {
        return this.picture;
    }

    private void setMagic(String magic) {
        this.magic = magic;
    }

    public String getMagic() {
        return this.magic;
    }

    private void setComment(String comment) {
        /* if it is null */
        if(comment == null){
            this.comment = null;
            return;
        }
        /* If null, does not concat */
        if (this.getComment() == null) {
            this.comment = comment;
            return;
        }
        
        this.comment += comment;
    }

    public String getComment() {
        return this.comment;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    private void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    private void setMaxGrey(int maxGrey) {
        this.maxGrey = maxGrey;
    }

    public int getMaxGrey() {
        return this.maxGrey;
    }

    public void open(String path, int type) {
        // set file to open
        this.setFile(path);

        /* Set Magic Number */
        String line = this.readLine(this.getFile());
        this.setMagic(line);

        /* not P5 and not P6? */
        if (!"P5".equals(this.getMagic()) && !"P6".equals(this.getMagic())) {
            System.out.println("Arquivo inválido");
            return;
        }

        /* Get Comment and Size Lines */
        while (true) {
            line = this.readLine(this.getFile());
            if (line.startsWith("#")) {
                this.setComment(line);
            } else {
                break;
            }

        }

        /* Get and Set Width */
        Scanner in = new Scanner(line);
        if (in.hasNext() && in.hasNextInt()) {
            this.setWidth(in.nextInt());
        } else {
            System.out.println("Arquivo corrompido");
            return;
        }

        /* Get and Set Height */
        if (in.hasNext() && in.hasNextInt()) {
            this.setHeight(in.nextInt());
        } else {
            System.out.println("Arquivo corrompido");
            return;
        }
        in.close();

        /* Set size of the image */
        this.setSize(this.getWidth() * this.getHeight());

        /* Get Max Grey Color */
        line = Decifra.readLine(this.getFile());
        in = new Scanner(line);
        this.setMaxGrey(in.nextInt());
        in.close();

        // Set Picture 
        this.setPicture(new BufferedImage(this.getWidth(), this.getHeight(), type));
        
        System.out.println("Magic=" + this.getMagic());
        System.out.println("Max=" + this.getMaxGrey());
        System.out.println("Comment=" + this.getComment());
        System.out.println("Height=" + this.getHeight());
        System.out.println("Width=" + this.getWidth());
        System.out.println("Total de Pixels = " + this.getSize());

    }

    private static String readLine(FileInputStream file) {
        String line = "";
        byte bb;
        try {
            while ((bb = (byte) file.read()) != '\n') {
                line += (char) bb;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public void saveImage(String name) {
        try {
            
            ImageIO.write(this.getPicture(), "PNG", new File(name));
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
    
    public void decodeRGB(int color){
        
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
        
   
}

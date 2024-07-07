package ImagesToChar.src.java.edu.school21.printer.logic;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Handler {
    private final char white;
    private final char black;
    private final String path;
    public Handler(char white, char black, String path) {
        this.white = white;
        this.black = black;
        this.path = path;
    }

    public void readPrintImage() throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("Файл отсутствует!");
            System.exit(-1);
        }
        BufferedImage img = ImageIO.read(file);
        int height = img.getHeight();
        int width = img.getWidth();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (img.getRGB(j, i) == -1)
                System.out.print(white);
                else System.out.print(black);
            }
            System.out.println();
        }
    }
}

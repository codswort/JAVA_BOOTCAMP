package ImagesToChar.src.java.edu.school21.printer.logic;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class Handler {
    private final String white;
    private final String black;
    public Handler(String white, String black) {
        this.white = white;
        this.black = black;
    }

    public void readPrintImage() throws IOException {
        File file = new File("ImagesToChar/src/resources/it.bmp");
        if (!file.exists()) {
            System.err.println("Файл отсутствует!");
            System.exit(-1);
        }
        BufferedImage img = ImageIO.read(file);
        int height = img.getHeight();
        int width = img.getWidth();
        ColoredPrinter coloredPrinter = new ColoredPrinter();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (img.getRGB(j, i) == Color.white.getRGB()) {
                    Ansi.BColor bgColor;
                    try {
                        bgColor = Ansi.BColor.valueOf(this.white);
                    } catch (IllegalArgumentException e) {
                        bgColor = Ansi.BColor.WHITE;
                    }
                    coloredPrinter.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, bgColor);

                } else if (img.getRGB(j, i) == Color.black.getRGB()) {
                    Ansi.BColor bgColor;
                    try {
                        bgColor = Ansi.BColor.valueOf(this.black);
                    } catch (IllegalArgumentException e) {
                        bgColor = Ansi.BColor.BLACK;
                    }
                    coloredPrinter.print(" ", Ansi.Attribute.NONE, Ansi.FColor.NONE, bgColor);;
                }
            }
            System.out.println();
        }
    }
}

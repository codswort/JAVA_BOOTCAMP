package ImagesToChar.src.java.edu.school21.printer.app;
import ImagesToChar.src.java.edu.school21.printer.logic.Handler;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.err.println("Проверьте аргументы! Первый аргумент - знак белого цвета, Второй аргумент - знак черного цвета, Третий аргумент - полный путь к двухцветному изображению");
            System.exit(-1);
        }

        char white = args[0].charAt(0);
        char black = args[1].charAt(0);
        String path = args[2];
        Handler handler = new Handler(white, black, path);
        handler.readPrintImage();
    }
}

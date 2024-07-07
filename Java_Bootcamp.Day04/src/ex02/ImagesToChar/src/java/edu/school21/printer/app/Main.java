package ImagesToChar.src.java.edu.school21.printer.app;
import ImagesToChar.src.java.edu.school21.printer.logic.Args;
import ImagesToChar.src.java.edu.school21.printer.logic.Handler;
import com.beust.jcommander.JCommander;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Проверьте аргументы! Первый аргумент - на какой цвет перекрасить белый," +
                    " Второй аргумент - на какой цвет перекрасить черный [Например: --white=RED --black=GREEN]");
            System.exit(-1);
        }
        Args arguments = new Args();
        JCommander jcommander = JCommander.newBuilder().addObject(arguments).build();
        jcommander.parse(args);

        String white = arguments.getCol1();
        String black = arguments.getCol2();
        Handler handler = new Handler(white, black);
        handler.readPrintImage();
    }
}

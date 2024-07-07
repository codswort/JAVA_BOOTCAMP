package ex02;

import java.io.*;
import java.nio.file.*;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1 || args[0].length() < 18) {
            System.err.print("Укажите полный путь к директории!");
            System.exit(-1);
        }
        FileManager fileManager = new FileManager(args[0].substring(17));
        try {
            fileManager.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

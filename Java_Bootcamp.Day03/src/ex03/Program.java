package src.ex03;
import java.io.*;
import java.util.LinkedList;

public class Program {
    private static final String currentDirectory = System.getProperty("user.dir") + "/src/ex03/";
    private static LinkedList<String> address = new LinkedList<>();
    public static void main(String[] args) {
        if (args.length != 1 || args[0].length() < 16) {
            System.err.print("Необходимо указывать в аргументах командной строки \"--threadsCount=n\",  где n - количество потоков!");
            System.exit(-1);
        }
        int threadsCount = Integer.parseInt(args[0].substring(15));
        if (threadsCount < 1) {
            System.err.print("Количество должно быть больше нуля!");
            System.exit(-1);
        }
        File file = new File(currentDirectory + "files_urls.txt");
        if (!file.exists()) {
            System.err.print("Нет такого файла по данному пути!");
            System.exit(-1);
        }
        readFile(file);
        Runner[] thread = new Runner[threadsCount];
        for (int k = 0; k < threadsCount; k++) {
            thread[k] = new Runner(address);
            thread[k].start();
        }
    }
    static private void readFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = reader.readLine()) != null) {
                String[] parts = str.split(" ");
                if (parts.length > 1) address.add(parts[1]);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}

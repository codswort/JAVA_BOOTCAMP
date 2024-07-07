package src.ex03;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;

public class Runner extends Thread {
    private static final String currentDirectory = System.getProperty("user.dir") + "/src/ex03/";
    private static LinkedList<String> address;

    public Runner(LinkedList<String> address) {
        Runner.address = address;
    }

    @Override
    public void run() {
        downloadFile();
    }

    static private void downloadFile() {
        Consumer consumer = new Consumer();
        while (true) {
            int i = consumer.getNextIndex();
            if (i >= address.size()) break;
            String tmp = address.get(i);
            System.out.println(Thread.currentThread().getName() + " start download file number " + i);
            String format = tmp.substring(tmp.lastIndexOf(".") + 1);
            String fileName = currentDirectory + "file" + i + "." + format;
            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                URL url = new URL(tmp);
                URLConnection connection = url.openConnection();
                InputStream inputStream = connection.getInputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
                System.out.println(Thread.currentThread().getName() + " finish download file number " + i);
            } catch (IOException e) {
                System.err.println("Ошибка при загрузки данных: " + e.getMessage());
            }
        }
    }
}

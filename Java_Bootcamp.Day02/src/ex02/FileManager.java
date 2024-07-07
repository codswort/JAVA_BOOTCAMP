package ex02;
import java.nio.file.*;
import java.util.*;
import java.io.*;

public class FileManager {
    static private Path pth;
    Scanner scanner = new Scanner(System.in);
    public FileManager(String path) {
        this.pth = Paths.get(path);
    }
    public void start() throws IOException {
        checkPath(pth);
        File currentDirectory = new File(pth.toString());
        File[] filesAndDirectories = currentDirectory.listFiles();
        System.out.println(pth.toString());
        manager(filesAndDirectories);
    }
    private void manager(File[] filesAndDirectories) throws IOException {
        System.out.print("-> ");
        if (scanner.hasNext()) {
            String input = scanner.nextLine();
            if (input.equals("ls")) {
                ifLsCommand(filesAndDirectories);
                start();
            } else if (input.startsWith("cd")) {
                ifCdCommand(input);
            } else if (input.startsWith("mv")) {
                ifMvCommand(input, filesAndDirectories);
            } else if (input.equals("exit")) {
                scanner.close();
                System.exit(-1);
            } else {
                System.out.println("Неизвестная команда!");
                start();
            }
        }
    }
    private void ifMvCommand(String input, File[] filesAndDirectories) throws IOException {
        String[] parts = input.split(" ");
        if (parts.length != 3 || !parts[0].equals("mv")) start();
        if (!checkIsFile(filesAndDirectories, parts[1])) {
            System.err.print("Отсутствует файл с таким именем!");
            System.exit(-1);
        }
        Path fromF = pth.resolve(parts[1]).normalize();
        Path toF = pth.resolve(parts[2]).normalize();
        if (Files.isDirectory(toF)) {
            if (fromF.isAbsolute()) {
                checkPath(toF);
                Files.move(fromF, toF.resolve(fromF.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                start();
            }
        } else {
            Files.move(fromF, toF);
            start();
        }
    }
    private void ifCdCommand(String input) throws IOException {
        String[] parts = input.split(" ");
        if (!parts[0].equals("cd")) start();
        if (parts.length == 1) {
            String tmp = System.getProperty("user.home");
            pth = Paths.get(tmp);
            start();
        }
        Path tmp = pth.resolve(parts[1]);
        checkPath(tmp);
        pth = tmp.normalize();
        start();
    }
    private boolean checkIsFile(File[] filesAndDirectories, String str) {
        for (File f : filesAndDirectories) {
            if (f.isFile() && f.getName().equals(str)) return true;
        }
        return false;
    }

    private void ifLsCommand (File[] filesAndDirectories) throws IOException {
        if (filesAndDirectories.length == 0) {
            System.out.println("Пустая директория!");
            start();
        }
        for (File file : filesAndDirectories)
            System.out.println(formatLs(file));
    }

    private String formatLs(File file) {
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        double filesize = 0;
        int unitIndex = 0;
        if (file.isFile()) {
            filesize = file.length();
        } else if (file.isDirectory()) {
            filesize = getFolderSize(file);
        }
        while (filesize > 1024 && unitIndex < units.length - 1) {
            filesize /= 1024;
            unitIndex++;
        }
        String formattedSize = String.format("%.2f", Math.floor(filesize*100)/100);
        return file.getName() + " " + formattedSize + " " + units[unitIndex];
    }
    private long getFolderSize(File file) {
        long sz = 0;
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) sz += f.length();
                else if (f.isDirectory()) sz += getFolderSize(f);
            }
        }
        return sz;
    }

    private void checkPath(Path path) {
        if (!path.isAbsolute()) {
            System.err.print("Путь должен быть абсолютным!");
            System.exit(-1);
        }
        if (!Files.isDirectory(path)) {
            System.err.print("Нет такой директории!");
            System.exit(-1);
        }
    }
}

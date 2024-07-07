package ex00;
import java.util.*;
import java.io.*;

public class FileDetector {
    private Map<String, String> fileSignatures = new HashMap<>();
    public FileDetector() {}
    public void ReadFile() {
        String pathFile;
        Scanner scanner = new Scanner(System.in);
        System.out.print("-> ");
        if (scanner.hasNext() && !(pathFile = scanner.nextLine()).equals("42")) {
            getSignatures();
            StringBuffer bytesFile = new StringBuffer();

            try (FileInputStream fis = new FileInputStream(pathFile)) {
                for (int i = 0; i < 8; i++) {
                    bytesFile.append(String.format("%02X ", fis.read()));
                }
                detectFile(bytesFile);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            } finally {
                scanner.close();
            }
        }
    }

    private void detectFile(StringBuffer bytesFile) throws IOException {
        createFile();
        FileOutputStream fos = new FileOutputStream("src/ex00/result.txt", true);
        boolean flag = false;
        for (String i : fileSignatures.keySet()) {
            if (bytesFile.toString().startsWith(i)) {
                fos.write(fileSignatures.get(i).getBytes());
                fos.write('\n');
                System.out.println("PROCESSED");
                flag = true;
            }
        }
        if (!flag) System.out.println("UNDEFINED");
        ReadFile();
    }
    private void createFile() throws IOException {
        File file = new File("src/ex00/result.txt");
        if (!file.exists()) file.createNewFile();
    }



    private boolean getSignatures() {
        try (FileInputStream fis = new FileInputStream("src/ex00/signatures.txt")) {
            Scanner scanner = new Scanner(fis);
            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                String[] parts = str.split(", ");
                fileSignatures.put(parts[1], parts[0]);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
}

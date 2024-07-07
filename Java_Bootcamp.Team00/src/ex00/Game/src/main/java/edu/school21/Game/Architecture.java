package src.ex00.Game.src.main.java.edu.school21.Game;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Architecture {

    public Map<String, String> readPropertiesFile(String filePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            System.err.println("File not found: " + filePath);
            System.exit(-1);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        Map<String, String> map = new HashMap<>();
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            if (parts.length == 3) {
                map.put(parts[0], parts[2]);
            } else {
                map.put(parts[0], " ");
            }
        }
        return map;
    }
}

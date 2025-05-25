package src.main.java.edu.school21.Chat.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    private Properties properties;
    public PropertiesLoader(String path) throws IOException {
        properties = new Properties();
        try(FileInputStream fis = new FileInputStream(new File(path))) {
            properties.load(fis);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public String getProperties(String str) {
        return properties.getProperty(str);
    }
}

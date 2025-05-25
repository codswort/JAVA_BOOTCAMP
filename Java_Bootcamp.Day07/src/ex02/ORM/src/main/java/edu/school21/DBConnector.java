package edu.school21;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import java.io.IOException;

public class DBConnector {
    private static String path = "src/main/resources/application.properties";
    private static PropertiesLoader propertiesLoader;

    static {
        try {
            propertiesLoader = new PropertiesLoader(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String URL = propertiesLoader.getProperties("db.url");
    private static String USER = propertiesLoader.getProperties("db.user");
    private static String PASSWORD = propertiesLoader.getProperties("db.passwd");
    public static DataSource start() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        return new HikariDataSource(config);
    }
}


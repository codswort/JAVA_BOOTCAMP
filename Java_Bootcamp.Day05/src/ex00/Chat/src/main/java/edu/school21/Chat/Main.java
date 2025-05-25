package edu.school21.Chat;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String passwd = "postgres";
        try (Connection connection = DriverManager.getConnection(url, user, passwd)) {
            String query = "select * from Chat.User";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("user_id");
                        String login = resultSet.getString("login");
                        String password = resultSet.getString("password");
                        System.out.println("id: " + id + ", login: " + login + ", password: " + password);
                    }
                }
            }
        } catch (SQLException exception) {
            System.out.println(exception);
        }
    }
}
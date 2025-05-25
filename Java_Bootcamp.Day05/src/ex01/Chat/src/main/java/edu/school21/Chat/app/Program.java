package src.main.java.edu.school21.Chat.app;

import src.main.java.edu.school21.Chat.repositories.MessagesRepository;
import src.main.java.edu.school21.Chat.repositories.MessagesRepositoryJdbcImpl;
import java.sql.SQLException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter a message ID");
            while (true) {
                System.out.print("-> ");
                String input = scanner.nextLine();
                if (input.equals("exit")) break;
                try {
                    Long id = Long.parseLong(input);
                    if (id < 0) {
                        System.out.println("ID cannot be negative.");
                        continue;
                    }
                    MessagesRepository test = new MessagesRepositoryJdbcImpl(DBConnector.start());
                    test.findById(id);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input. Please enter a valid integer or type 'exit' to quit.");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
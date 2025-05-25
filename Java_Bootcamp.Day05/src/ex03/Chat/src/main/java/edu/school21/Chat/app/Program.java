package src.main.java.edu.school21.Chat.app;

import src.main.java.edu.school21.Chat.models.Message;
import src.main.java.edu.school21.Chat.repositories.MessagesRepository;
import src.main.java.edu.school21.Chat.repositories.MessagesRepositoryJdbcImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class Program {
    public static void main(String[] args) {
        try {
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(DBConnector.start());
            Optional<Message> messageOptional = messagesRepository.findById(11L);
            if (messageOptional.isPresent()) {
                Message message = messageOptional.get();
                message.setMessageText("Bye");
                message.setLocalDateTime(null);
                messagesRepository.update(message);
            }
        } catch (NotSavedSubEntityException | SQLException nse) {
            nse.printStackTrace();
        }
    }
}
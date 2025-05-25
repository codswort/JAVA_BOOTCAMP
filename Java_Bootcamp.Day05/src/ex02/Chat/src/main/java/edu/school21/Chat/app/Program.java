package src.main.java.edu.school21.Chat.app;

import src.main.java.edu.school21.Chat.models.Message;
import src.main.java.edu.school21.Chat.repositories.MessagesRepositoryJdbcImpl;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Program {
    public static void main(String[] args) {
        try {
            MessagesRepositoryJdbcImpl messagesRepository = new MessagesRepositoryJdbcImpl(DBConnector.start());
            Message message = new Message(null, messagesRepository.findUserById(5L), messagesRepository.findRoomById(4L), "Hello Ilnaz!", LocalDateTime.now());

            messagesRepository.save(message);
            System.out.println(message.getMessageId());

        } catch (NotSavedSubEntityException | SQLException nse) {
            nse.printStackTrace();
        }
    }
}
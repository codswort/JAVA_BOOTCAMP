package src.main.java.edu.school21.Chat.repositories;
import src.main.java.edu.school21.Chat.models.Message;

import java.util.Optional;

public interface MessagesRepository {
    Optional<Message> findById(Long id);
    void save(Message message);
}

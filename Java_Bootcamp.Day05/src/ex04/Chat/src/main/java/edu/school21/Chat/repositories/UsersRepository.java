package src.main.java.edu.school21.Chat.repositories;

import src.main.java.edu.school21.Chat.models.User;

import java.util.List;

public interface UsersRepository {
    List<User> findAll(int page, int size);
}

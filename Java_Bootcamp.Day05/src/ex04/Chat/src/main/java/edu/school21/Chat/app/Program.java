package src.main.java.edu.school21.Chat.app;

import src.main.java.edu.school21.Chat.models.User;
import src.main.java.edu.school21.Chat.repositories.UsersRepository;
import src.main.java.edu.school21.Chat.repositories.UsersRepositoryJdbcImpl;

import java.sql.SQLException;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        try {
            UsersRepository usersRepository = new UsersRepositoryJdbcImpl(DBConnector.start());
            List<User> list = usersRepository.findAll(0, 3);
//            System.out.println(list);
        } catch (NotSavedSubEntityException | SQLException nse) {
            nse.printStackTrace();
        }
    }
}
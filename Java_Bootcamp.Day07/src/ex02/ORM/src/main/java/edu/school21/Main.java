package edu.school21;

import edu.school21.models.User;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            OrmManager ormManager = new OrmManager(DBConnector.start());
            ormManager.create(User.class);
            ormManager.save(new User(1L, "Ilnaz", "Nabiev", 32));
            ormManager.update(new User(1L, "Ilnaz2", "Nabiev2", 18));
            System.out.println(ormManager.findById(1l, User.class));
            ormManager.dropTable(User.class);
        } catch (SQLException nse) {
            nse.printStackTrace();
        }

    }
}
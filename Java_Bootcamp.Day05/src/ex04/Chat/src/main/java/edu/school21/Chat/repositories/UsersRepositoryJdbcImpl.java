package src.main.java.edu.school21.Chat.repositories;

import src.main.java.edu.school21.Chat.models.Room;
import src.main.java.edu.school21.Chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static src.main.java.edu.school21.Chat.repositories.Query.*;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private Connection connection;
    public UsersRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }
    public List<User> findAll(int page, int size) {
        String sql = FIND_ALL.getQuery();
        List<User> userList = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            int start = page * size;
            statement.setLong(1, size);
            statement.setLong(2, start );
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Long userID = result.getLong("user_id");
                    String login = result.getString("login");
                    String password = result.getString("password");
                    ArrayList<Room> createdRooms = getRoomsFromBD("created_room_id", result);
                    ArrayList<Room> participatedRooms = getRoomsFromBD("participated_room_id", result);
                    User user = new User(userID, login, password, createdRooms, participatedRooms);
                    System.out.println(userID);
                    userList.add(user);
                }
            }
            return userList;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    private ArrayList<Room> getRoomsFromBD(String columnLabel, ResultSet result) throws SQLException {
        String rooms = result.getString(columnLabel);
        String output = rooms.replaceAll("[{}]", "");
        ArrayList<Room> roomsArray = new ArrayList<>();
        if (output != null && !output.isEmpty()) {
            String[] parts = output.split(",");
            for (String str : parts) {
                if (!str.equals("NULL") && !str.isEmpty()) {
                    Long id = Long.valueOf(str);
                    roomsArray.add(findRoomById(id));
                }
            }
        }
        return roomsArray;
    }
    private User findUserById(Long id) {
        String sql = FIND_USER.getQuery();
        try (PreparedStatement preparedStatementMessage = connection.prepareStatement(sql)) {
            preparedStatementMessage.setLong(1, id);
            try (ResultSet rs = preparedStatementMessage.executeQuery()) {
                if (!rs.next()) return null;
                Long userID = rs.getLong("user_id");
                String userLogin = rs.getString("login");
                String userPassword = rs.getString("password");
                User user = new User(userID, userLogin, userPassword, new ArrayList<>(), new ArrayList<>());
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Room findRoomById(Long id) {
        String sql = FIND_ROOM.getQuery();
        try (PreparedStatement preparedStatementMessage = connection.prepareStatement(sql)) {
            preparedStatementMessage.setLong(1, id);
            try (ResultSet rs = preparedStatementMessage.executeQuery()) {
                if (!rs.next()) return null;
                Long chatroomID = rs.getLong("chatroom_id");
                String chatroomName = rs.getString("chatroom_name");
                Long ownerID = rs.getLong("chatroom_owner");
                Room room = new Room(chatroomID, chatroomName, findUserById(ownerID), new ArrayList<>());
                return room;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}

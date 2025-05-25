package src.main.java.edu.school21.Chat.repositories;

import src.main.java.edu.school21.Chat.app.NotSavedSubEntityException;
import src.main.java.edu.school21.Chat.models.*;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private Connection connection;
    public MessagesRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    public Optional<Message> findById(Long id) {
        String sqlMessage = "SELECT * FROM Chat.Message m " +
                "JOIN Chat.User u ON m.message_author = u.user_id " +
                "JOIN Chat.Chatroom r ON m.message_room = r.chatroom_id " +
                "WHERE m.message_id = ?";
        try (PreparedStatement preparedStatementMessage = connection.prepareStatement(sqlMessage)) {
            preparedStatementMessage.setLong(1, id);
            try (ResultSet rsMessage = preparedStatementMessage.executeQuery()) {
                if (!rsMessage.next()) return Optional.empty();
                Long messageId = rsMessage.getLong("message_id");

                Long authorID = rsMessage.getLong("message_author");
                String authorLogin = rsMessage.getString("login");//login
                String authorPassword = rsMessage.getString("password");//password
                User author = new User(authorID, authorLogin, authorPassword, null, null);

                Long roomID = rsMessage.getLong("message_room");
                String roomName = rsMessage.getString("chatroom_name");
                Room room = new Room(roomID, roomName, null, null);

                String text = rsMessage.getString("message_text");
                LocalDateTime dateTime = rsMessage.getTimestamp("message_date").toLocalDateTime();
                Message message = new Message(messageId, author, room, text, dateTime);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
                System.out.println("Message : {\n" +
                        "  id=" + messageId + ",\n" +
                        "  " + author + ",\n" +
                        "  " + room + ",\n" +
                        "  text=\"" + text + "\",\n" +
                        "  dateTime=" + dateTime.format(formatter) + "\n" +
                        "}");

                return Optional.of(message);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    public User findUserById(Long id) {
        String sql = "SELECT * FROM Chat.User WHERE user_id = ?";
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

    public Room findRoomById(Long id) {
        String sql = "SELECT * FROM Chat.Chatroom WHERE chatroom_id = ?";
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
    public void save(Message message) {
        String sql = "insert into Chat.Message (message_author, message_room, message_text, message_date) " +
                "values (?, ?, ?, ?) returning message_id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            checkMessage(message);
            statement.setLong(1, message.getMessageAuthor().getUserId());
            statement.setLong(2, message.getMessageRoom().getRoomId());
            statement.setString(3, message.getMessageText());
            statement.setTimestamp(4, Timestamp.valueOf(message.getLocalDateTime()));


            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    message.setMessageId(rs.getLong(1));
                } else {
                    throw new NotSavedSubEntityException("Error of creating the message ID");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(Message message) {
        String sql = "update Chat.Message SET message_text = ?, message_date = ? WHERE message_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (message.getMessageText() == null) {
                statement.setNull(1, Types.VARCHAR);
            } else statement.setString(1, message.getMessageText());
            if (message.getLocalDateTime() == null) {
                statement.setNull(2, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(2, Timestamp.valueOf(message.getLocalDateTime()));
            }

            statement.setLong(3, message.getMessageId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new NotSavedSubEntityException("Error of updating the message");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void checkMessage(Message message) {
        if (message.getMessageAuthor() == null || findUserById(message.getMessageAuthor().getUserId()) == null) {
            throw new NotSavedSubEntityException("User was not found!");
        }
        if (message.getMessageRoom() == null || findRoomById(message.getMessageRoom().getRoomId()) == null) {
            throw new NotSavedSubEntityException("Room was not found!");
        }
        if (message.getMessageText() == null || message.getMessageText().length() < 1) {
            throw new NotSavedSubEntityException("Incorrect text!");
        }
        if (message.getLocalDateTime() == null) {
            throw new NotSavedSubEntityException("Incorrect date and time!");
        }
    }


}

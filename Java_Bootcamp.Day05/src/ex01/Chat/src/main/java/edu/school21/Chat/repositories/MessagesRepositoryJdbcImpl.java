package src.main.java.edu.school21.Chat.repositories;

import src.main.java.edu.school21.Chat.models.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
}

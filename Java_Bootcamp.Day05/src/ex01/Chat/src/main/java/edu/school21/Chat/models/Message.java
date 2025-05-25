package src.main.java.edu.school21.Chat.models;
import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long messageId;
    private User messageAuthor;
    private Room messageRoom;
    private String messageText;
    private LocalDateTime localDateTime;

    public Message(Long messageId, User messageAuthor, Room messageRoom, String messageText, LocalDateTime localDateTime) {
        this.messageId = messageId;
        this.messageAuthor = messageAuthor;
        this.messageRoom = messageRoom;
        this.messageText = messageText;
        this.localDateTime = localDateTime;
    }

    public Long getMessageId() {
        return messageId;
    }

    public User getMessageAuthor() {
        return messageAuthor;
    }

    public Room getMessageRoom() {
        return messageRoom;
    }

    public String getMessageText() {
        return messageText;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setMessageAuthor(User messageAuthor) {
        this.messageAuthor = messageAuthor;
    }

    public void setMessageRoom(Room messageRoom) {
        this.messageRoom = messageRoom;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Message message = (Message) obj;
        return  Objects.equals(messageId, message.messageId) &&
                Objects.equals(messageAuthor, message.messageAuthor) &&
                Objects.equals(messageRoom, message.messageRoom) &&
                Objects.equals(messageText, message.messageText);
    }


    @Override
    public int hashCode() {
        return Objects.hash(messageId, messageAuthor, messageRoom, messageText);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + messageId + ';' +
                "author=" + messageAuthor + ';' +
                "room=" + messageRoom + ';' +
                "text=" + messageText + ';' +
                "date/time=" + localDateTime +
                "}";
    }
}

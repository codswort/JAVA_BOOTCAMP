package src.main.java.edu.school21.Chat.models;
import java.util.List;
import java.util.Objects;

public class Room {
    private Long roomId;
    private String roomName;
    private User owner;
    private List<Message> roomMessages;

    public Room(Long roomId, String roomName, User owner, List<Message> roomMessages) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.owner = owner;
        this.roomMessages = roomMessages;
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public User getOwner() {
        return owner;
    }

    public List<Message> getRoomMessages() {
        return roomMessages;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setRoomMessages(List<Message> roomMessages) {
        this.roomMessages = roomMessages;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return  Objects.equals(roomId, room.roomId) &&
                Objects.equals(roomName, room.roomName) &&
                Objects.equals(owner, room.owner) &&
                Objects.equals(roomMessages, room.roomMessages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, roomName, owner, roomMessages);
    }

    @Override
    public String toString() {
        return "room{" +
                "id=" + roomId + ',' +
                "name=\"" + roomName + "\"," +
                "owner=" + owner + ',' +
                "roomMessages=" + roomMessages +
                "}";
    }
}

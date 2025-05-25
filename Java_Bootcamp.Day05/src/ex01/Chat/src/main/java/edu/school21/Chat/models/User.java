package src.main.java.edu.school21.Chat.models;
import java.util.List;
import java.util.Objects;

public class User {
    private Long userId;
    private String login;
    private String password;
    private List<Room> createdRooms;
    private List<Room> socializesRooms;

    public User(Long userId, String login, String password, List<Room> createdRooms, List<Room> socializesRooms) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.socializesRooms = socializesRooms;
    }

    public Long getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Room> getCreatedRooms() {
        return createdRooms;
    }

    public List<Room> getSocializesRooms() {
        return socializesRooms;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedRooms(List<Room> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public void setSocializesRooms(List<Room> socializesRooms) {
        this.socializesRooms = socializesRooms;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return  Objects.equals(userId, user.userId) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(createdRooms, user.createdRooms) &&
                Objects.equals(socializesRooms, user.socializesRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, password, createdRooms, socializesRooms);
    }

    @Override
    public String toString() {
        return "author{" +
                "id=" + userId + ',' +
                "login=\"" + login + "\"," +
                "password=\"" + password + "\"," +
                "createdRooms=" + createdRooms + ',' +
                "socializesRooms=" + socializesRooms +
                "}";
    }

}
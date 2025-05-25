package edu.school21.models;

import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private Boolean status; //Authentication success status (true — authenticated, false — not authenticated).

    public User(Long id, String login, String password, Boolean status) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(status, user.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, status);
    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }

}

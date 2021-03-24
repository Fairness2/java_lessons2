package lesson_125344.Server.Auth;

import java.util.Objects;

public class User {
    private String login;
    private String password;
    private String name;
    private int id;

    public User (String login, String name, int id) {
        this(login, null, name, id);
    }

    public User (String login, String password, String name, int id) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        User targetUser = (User) o;
        return Objects.equals(login, targetUser.getLogin()) && Objects.equals(password, targetUser.getPassword()) && Objects.equals(name, targetUser.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, name);
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package lesson_125344.Server.Auth;

import java.sql.*;
import java.util.Optional;
import java.util.Set;

public class AuthenticationService {
    private static final String url = "jdbc:mysql://localhost:3306/chat";
    private static final String dbUser = "root";
    private static final String dbPassword = "1234567890";

    /*private static final Set<User> users = Set.of(
            new User("u1", "p1", "n1"),
            new User("u2", "p2", "n2"),
            new User("u3", "p3", "n3"),
            new User("u4", "p4", "n4")
    );*/

    public User findByCredentials(String login, String password){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE login = ? AND password = ?");
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet res = ps.executeQuery();
            User user = null;
            if (res.next()) {
                user = new User(res.getString("login"), res.getString("name"), res.getInt("id"));
            }
            return user;
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        //Optional<User> oUser = users.stream().filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password)).findFirst();
        //return oUser.orElse(null);
    }

    public boolean changeUserName(User user, String name) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            PreparedStatement ps = conn.prepareStatement("UPDATE user SET name = ? WHERE id = ?");
            ps.setString(1, name);
            ps.setInt(2, user.getId());
            int res = ps.executeUpdate();
            if (res == 1) {
                user.setName(name);
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

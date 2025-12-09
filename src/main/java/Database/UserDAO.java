package Database;
import Model.User;
import java.sql.*;

public class UserDAO {

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            return false;
        }
    }

    public User validateLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User mapRowToUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String username = rs.getString("username");
        String role = rs.getString("role");

        User user = new User(id, firstName, lastName, username, role);
        // set other fields if needed
        return user;
    }

    public boolean createUser(User user) throws SQLException {
        String sql = "INSERT INTO users (first_name, last_name, address, city, state, zip_code, " +
                "username, password, email, ssn, security_question, security_answer, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getCity());
            stmt.setString(5, user.getState());
            stmt.setString(6, user.getZipCode());
            stmt.setString(7, user.getUsername());
            stmt.setString(8, user.getPassword());
            stmt.setString(9, user.getEmail());
            stmt.setString(10, user.getSsn());
            stmt.setString(11, user.getSecurityQuestion());
            stmt.setString(12, user.getSecurityAnswer());
            stmt.setString(13, user.getRole());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        }


    }

    public User authenticateUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                }
            }
        }
        return null;
    }

    public String recoverPassword(String username, String securityAnswer) throws SQLException {
        String sql = "SELECT password FROM users WHERE username = ? AND security_answer = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, securityAnswer);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("password");
            }

            return null;
        }
    }
}

package Database;

import Model.User;
import java.sql.*;

public class UserDAO {
    /**
     * Authenticate user (login)
     */
    public User authenticateUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }

            return null; // Authentication failed
        }
    }

    /**
     * Create a new user (registration)
     */
    public boolean createUser(User user) throws SQLException {
        String query = "INSERT INTO users (first_name, last_name, address, zip, state, " +
                "username, password, email, ssn, security_question, security_answer, is_admin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getZip());
            stmt.setString(5, user.getState());
            stmt.setString(6, user.getUsername());
            stmt.setString(7, user.getPassword());
            stmt.setString(8, user.getEmail());
            stmt.setString(9, user.getSsn());
            stmt.setString(10, user.getSecurityQuestion());
            stmt.setString(11, user.getSecurityAnswer());
            stmt.setBoolean(12, user.isAdmin());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    /**
     * Check if username already exists
     */
    public boolean usernameExists(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

            return false;
        }
    }

    /**
     * Get user by username
     */
    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }

            return null;
        }
    }

    /**
     * Recover password using security question
     */
    public String recoverPassword(String username, String securityAnswer) throws SQLException {
        String query = "SELECT password FROM users WHERE username = ? AND security_answer = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, securityAnswer);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("password");
            }

            return null;
        }
    }

    /**
     * Helper method to extract User object from ResultSet
     */
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setAddress(rs.getString("address"));
        user.setZip(rs.getString("zip"));
        user.setState(rs.getString("state"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setSsn(rs.getString("ssn"));
        user.setSecurityQuestion(rs.getString("security_question"));
        user.setSecurityAnswer(rs.getString("security_answer"));
        user.setAdmin(rs.getBoolean("is_admin"));
        return user;
    }

}

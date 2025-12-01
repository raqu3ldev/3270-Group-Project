package Service;

import Model.User;
import Database.UserDAO;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Authenticate a user (login)
     *
     * @return User object if successful, null if failed
     */
    public User authenticateUser(String username, String password) {
        try {
            return userDAO.authenticateUser(username, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error authenticating user: " + e.getMessage());
        }
    }

    /**
     * Register a new user
     *
     * @return true if successful, false if username already exists
     */
    public boolean registerUser(String firstName, String lastName, String address,
                                String zip, String state, String username, String password,
                                String email, String ssn, String securityQuestion,
                                String securityAnswer) {
        try {
            // Check if username already exists
            if (userDAO.usernameExists(username)) {
                return false;
            }

            // Create new user
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAddress(address);
            user.setZip(zip);
            user.setState(state);
            user.setUsername(username);
            user.setPassword(password); // In production, hash this!
            user.setEmail(email);
            user.setSsn(ssn);
            user.setSecurityQuestion(securityQuestion);
            user.setSecurityAnswer(securityAnswer);
            user.setAdmin(false); // Default to customer

            return userDAO.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error registering user: " + e.getMessage());
        }
    }

    /**
     * Recover password using security question
     */
    public String recoverPassword(String username, String securityAnswer) {
        try {
            return userDAO.recoverPassword(username, securityAnswer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error recovering password: " + e.getMessage());
        }
    }

    /**
     * Get user by username
     */
    public User getUserByUsername(String username) {
        try {
            return userDAO.getUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting user: " + e.getMessage());
        }
    }
}
package UI;

import Model.User;
import Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginScreenController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button forgotButton;

    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserService();

        loginButton.setOnAction(e -> handleLogin());
        registerButton.setOnAction(e -> handleRegister());
        forgotButton.setOnAction(e -> handleForgotPassword());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter username and password!");
            return;
        }

        try {
            User user = userService.authenticateUser(username, password);

            if (user != null) {
                Stage stage = (Stage) loginButton.getScene().getWindow();

                if (user.isAdmin()) {
                    AdminMenu adminMenu = new AdminMenu(stage);
                    adminMenu.show();
                } else {
                    CustomerMenu customerMenu = new CustomerMenu(stage, user);
                    customerMenu.show();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password!");
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Login error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleRegister() {
        try {
            Stage stage = (Stage) registerButton.getScene().getWindow();
            RegisterScreen registerScreen = new RegisterScreen(stage);
            registerScreen.show();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error opening registration: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleForgotPassword() {
        showAlert(Alert.AlertType.INFORMATION, "Forgot Password",
                "Password recovery feature - Coming soon!");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
package UI;

import Model.User;
import Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Please enter both username and password.");
            return;
        }

        try {
            User user = userService.authenticateUser(username, password);

            if (user != null) {
                showAlert(Alert.AlertType.INFORMATION, "Login Successful",
                        "Welcome, " + user.getFirstName() + "!");

                Stage stage = (Stage) loginButton.getScene().getWindow();

                if ("ADMIN".equals(user.getRole())) {
                    AdminMenu adminMenu = new AdminMenu(stage);
                    adminMenu.show();
                } else {
                    CustomerMenu customerMenu = new CustomerMenu(stage, user);
                    customerMenu.show();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed",
                        "Invalid username or password.");
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "An error occurred during login: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleRegister() {
        try {
            Stage stage = (Stage) registerButton.getScene().getWindow();
            RegisterScreen registerScreen = new RegisterScreen(stage);
            registerScreen.show();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to open registration screen: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleForgotPassword() {
        javafx.scene.control.TextInputDialog usernameDialog = new javafx.scene.control.TextInputDialog();
        usernameDialog.setTitle("Forgot Password");
        usernameDialog.setHeaderText("Password Recovery");
        usernameDialog.setContentText("Enter your username:");

        usernameDialog.showAndWait().ifPresent(username -> {
            if (username.trim().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Username cannot be empty.");
                return;
            }

            javafx.scene.control.TextInputDialog answerDialog = new javafx.scene.control.TextInputDialog();
            answerDialog.setTitle("Forgot Password");
            answerDialog.setHeaderText("Security Question");
            answerDialog.setContentText("What was your childhood home's zipcode?");

            answerDialog.showAndWait().ifPresent(answer -> {
                try {
                    String password = userService.recoverPassword(username.trim(), answer.trim());

                    if (password != null) {
                        showAlert(Alert.AlertType.INFORMATION, "Password Recovery",
                                "Your password is: " + password);
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Recovery Failed",
                                "Invalid username or security answer.");
                    }
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error",
                            "An error occurred: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
        });
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


package UI;

import Service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterScreenController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField ssnField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField securityAnswerField;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserService();

        registerButton.setOnAction(e -> handleRegister());
        backButton.setOnAction(e -> handleBack());
    }

    private void handleRegister() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String ssn = ssnField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String securityAnswer = securityAnswerField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
                password.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error",
                    "Please fill in all required fields!");
            return;
        }

        if (!email.contains("@")) {
            showAlert(Alert.AlertType.WARNING, "Validation Error",
                    "Please enter a valid email address!");
            return;
        }

        // Validate password length
        if (password.length() < 6) {
            showAlert(Alert.AlertType.WARNING, "Validation Error",
                    "Password must be at least 6 characters long!");
            return;
        }

        try {
            boolean success = userService.registerUser(
                    firstName, lastName, "", "", "", username, password,
                    email, ssn, "What was your childhood home's zipcode?", securityAnswer
            );

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Registration successful! Please login.");
                handleBack();
            } else {
                showAlert(Alert.AlertType.ERROR, "Registration Failed",
                        "Username already exists! Please choose a different username.");
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Registration failed: " + ex.getMessage());
        }
    }

    private void handleBack() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            LoginScreen loginScreen = new LoginScreen(stage);
            loginScreen.show();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error returning to login: " + ex.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
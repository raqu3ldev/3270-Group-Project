package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    @FXML
    public void initialize() {
        loginButton.setOnAction(e ->
                System.out.println("Login clicked: " + usernameField.getText()));

        registerButton.setOnAction(e ->
                System.out.println("Navigate to Register Screen"));

        forgotButton.setOnAction(e ->
                System.out.println("Forgot Password clicked"));
    }
}

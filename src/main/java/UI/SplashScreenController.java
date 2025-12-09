package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SplashScreenController {

    @FXML
    private Button continueButton;

    @FXML
    public void initialize() {
        continueButton.setOnAction(e -> goToLogin());
    }

    private void goToLogin() {
        try {
            Stage stage = (Stage) continueButton.getScene().getWindow();
            LoginScreen loginScreen = new LoginScreen(stage);
            loginScreen.show();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
package UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginScreen {

    private Stage stage;

    public LoginScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        stage.setTitle("Airline System Login");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/loginScreen.fxml"));
        Scene scene = new Scene(loader.load(), 400, 300);

        stage.setScene(scene);
        stage.show();
    }
}
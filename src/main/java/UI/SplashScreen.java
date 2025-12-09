package UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SplashScreen {

    private Stage stage;

    public SplashScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        stage.setTitle("Airline Reservation System");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Splash.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);

        stage.setScene(scene);
        stage.show();
    }
}
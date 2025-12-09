package UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminSearchFlights {

    private Stage stage;

    public AdminSearchFlights(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        stage.setTitle("Search Flights");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/adminSearchFlights.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);

        stage.setScene(scene);
        stage.show();
    }
}
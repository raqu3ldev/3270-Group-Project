package UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminManageFlights {

    private Stage stage;

    public AdminManageFlights(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        stage.setTitle("Manage Flights");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/adminManageFlights.fxml"));
        Scene scene = new Scene(loader.load(), 769, 414);

        stage.setScene(scene);
        stage.show();
    }
}
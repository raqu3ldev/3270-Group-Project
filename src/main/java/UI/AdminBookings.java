package UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminBookings {

    private Stage stage;

    public AdminBookings(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        stage.setTitle("All Bookings");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/adminBookings.fxml"));
        Scene scene = new Scene(loader.load(), 769, 414);

        stage.setScene(scene);
        stage.show();
    }
}
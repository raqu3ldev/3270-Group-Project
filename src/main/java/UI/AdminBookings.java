package UI;

import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminBookings {
    private Stage stage;
    private User user;

    public AdminBookings(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void show() throws IOException {
        stage.setTitle("All Bookings");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/adminBookings.fxml"));
        Scene scene = new Scene(loader.load(), 900, 500);

        AdminBookingsController controller = loader.getController();
        controller.setUser(user);
        controller.setStage(stage);

        stage.setScene(scene);
        stage.show();
    }
}


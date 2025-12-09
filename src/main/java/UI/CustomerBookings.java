package UI;

import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class CustomerBookings {

    private Stage stage;
    private User user;

    public CustomerBookings(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void show() throws IOException {
        stage.setTitle("My Bookings");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/CustomerBookings.fxml"));
        Scene scene = new Scene(loader.load(), 728, 463);

        CustomerBookingsController controller = loader.getController();
        controller.setUser(user);  // Pass user to controller

        stage.setScene(scene);
        stage.show();
    }
}
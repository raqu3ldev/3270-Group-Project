package UI;

import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminManageFlights {

    private Stage stage;
    private User user;

    public AdminManageFlights(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void show() throws IOException {
        stage.setTitle("Manage Flights");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/adminManageFlights.fxml"));
        Scene scene = new Scene(loader.load(), 900, 600);

        AdminManageFlightsController controller = loader.getController();
        controller.setUser(user);
        controller.setStage(stage);

        stage.setScene(scene);
        stage.show();
    }
}
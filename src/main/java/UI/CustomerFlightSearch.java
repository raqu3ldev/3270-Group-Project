package UI;

import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class CustomerFlightSearch {

    private Stage stage;
    private User user;

    public CustomerFlightSearch(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void show() throws IOException {
        stage.setTitle("Search Flights");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/CustomerFlightSearch.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);

        stage.setScene(scene);
        stage.show();
    }
}
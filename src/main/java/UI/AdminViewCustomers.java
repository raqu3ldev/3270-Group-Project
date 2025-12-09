package UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminViewCustomers {

    private Stage stage;

    public AdminViewCustomers(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        stage.setTitle("View Customers");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/adminViewCustomers.fxml"));
        Scene scene = new Scene(loader.load(), 950, 600);

        stage.setScene(scene);
        stage.show();
    }
}
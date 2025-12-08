package UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminMenu {

    private Stage stage;

    public AdminMenu(Stage stage) {
        this.stage = stage;
    }

    public void show() throws IOException {
        stage.setTitle("Admin Menu");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/adminMenu.fxml"));
        Scene scene = new Scene(loader.load(), 467, 338);  // Match your FXML size

        stage.setScene(scene);
        stage.show();
    }
}
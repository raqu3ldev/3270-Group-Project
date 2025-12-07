package UI;

import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminMenu {

    private Stage stage;
    private User user;

    public AdminMenu(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void show() throws IOException {
        stage.setTitle("Admin Menu");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/adminMenu.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);  // Load FIRST

        // Get controller AFTER loading
        AdminMenuController controller = loader.getController();
        controller.setUser(user);
        controller.setStage(stage);

        stage.setScene(scene);
        stage.show();
    }
}
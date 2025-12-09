package UI;

import Model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class CustomerMenu {

    private Stage stage;
    private User user;

    public CustomerMenu(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void show() throws IOException {
        stage.setTitle("Customer Menu - " + user.getFirstName() + " " + user.getLastName());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/customerMenu.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);

        CustomerMenuController controller = loader.getController();
        controller.setUser(user);

        stage.setScene(scene);
        stage.show();
    }
}
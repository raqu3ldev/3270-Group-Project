package UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


public class RegisterScreen {
    private Stage stage;

    public RegisterScreen (Stage stage){
        this.stage = stage;
    }

    public void show() throws IOException {
        stage.setTitle("Register");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/registerScreen.fxml"));
        Scene scene = new Scene(loader.load(), 692, 691);

        stage.setScene(scene);
        stage.show();
    }

}

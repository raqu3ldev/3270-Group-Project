package UI;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Set application title
        primaryStage.setTitle("Airline Reservation System");

        // Show splash screen first
        SplashScreen splashScreen = new SplashScreen(primaryStage);
        splashScreen.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
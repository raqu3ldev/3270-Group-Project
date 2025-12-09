package UI;

import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CustomerMenuController {
    @FXML private Label welcomeLabel;
    @FXML private Button searchButton;
    @FXML private Button ViewBookingsButton;
    @FXML private Button logoutButton;

    private User user;
    private Stage stage;

    @FXML
    public void initialize() {
        // Setup button actions
        searchButton.setOnAction(e -> handleSearch());
        ViewBookingsButton.setOnAction(e -> handleViewBookings());
        logoutButton.setOnAction(e -> handleLogout());
    }

    public void setUser(User user) {
        this.user = user;
        welcomeLabel.setText("Welcome, " + user.getFirstName() + " " + user.getLastName());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void handleSearch() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/CustomerFlightSearch.fxml"));
            Scene scene = new Scene(loader.load());

            CustomerFlightSearchController controller = loader.getController();
            controller.setUser(user);
            controller.setStage(stage);

            stage.setScene(scene);
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error loading search page: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleViewBookings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/CustomerBookings.fxml"));
            Scene scene = new Scene(loader.load());

            CustomerBookingsController controller = loader.getController();
            controller.setUser(user);
            controller.setStage(stage);

            stage.setScene(scene);
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error loading bookings page: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleLogout() {
        try {
            LoginScreen loginScreen = new LoginScreen(stage);
            loginScreen.show();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error returning to login: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
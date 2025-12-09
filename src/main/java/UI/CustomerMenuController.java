package UI;

import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class CustomerMenuController {

    @FXML
    private Button searchFlightsButton;

    @FXML
    private Button myBookingsButton;

    @FXML
    private Button logoutButton;

    private User user;

    @FXML
    public void initialize() {
        searchFlightsButton.setOnAction(e -> openSearchFlights());
        myBookingsButton.setOnAction(e -> openMyBookings());
        logoutButton.setOnAction(e -> logout());
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void openSearchFlights() {
        try {
            Stage stage = (Stage) searchFlightsButton.getScene().getWindow();
            CustomerFlightSearch searchScreen = new CustomerFlightSearch(stage, user);
            searchScreen.show();
        } catch (IOException ex) {
            System.out.println("Error loading search screen: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void openMyBookings() {
        try {
            Stage stage = (Stage) myBookingsButton.getScene().getWindow();
            CustomerBookings bookingsScreen = new CustomerBookings(stage, user);
            bookingsScreen.show();
        } catch (IOException ex) {
            System.out.println("Error loading bookings screen: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void logout() {
        try {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            LoginScreen loginScreen = new LoginScreen(stage);
            loginScreen.show();
        } catch (IOException ex) {
            System.out.println("Error loading login screen: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
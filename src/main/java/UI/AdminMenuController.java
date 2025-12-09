package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminMenuController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button manageFlightsButton;

    @FXML
    private Button searchFlightsButton;

    @FXML
    private Button viewBookingsButton;

    @FXML
    private Button viewCustomersButton;

    @FXML
    private Button logoutButton;

    @FXML
    public void initialize() {
        manageFlightsButton.setOnAction(e -> openManageFlights());
        searchFlightsButton.setOnAction(e -> openSearchFlights());
        viewBookingsButton.setOnAction(e -> openViewBookings());
        viewCustomersButton.setOnAction(e -> openViewCustomers());
        logoutButton.setOnAction(e -> logout());
    }

    private void openManageFlights() {
        try {
            Stage stage = (Stage) manageFlightsButton.getScene().getWindow();
            AdminManageFlights manageFlights = new AdminManageFlights(stage);
            manageFlights.show();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void openSearchFlights() {
        System.out.println("Search Flights - Coming soon!");
    }

    private void openViewBookings() {
        try {
            Stage stage = (Stage) viewBookingsButton.getScene().getWindow();
            AdminBookings bookings = new AdminBookings(stage);
            bookings.show();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void openViewCustomers() {
        System.out.println("View Customers - Coming soon!");
    }

    private void logout() {
        try {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            LoginScreen loginScreen = new LoginScreen(stage);
            loginScreen.show();
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
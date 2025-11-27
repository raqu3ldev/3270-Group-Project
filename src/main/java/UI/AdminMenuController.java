package UI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
        // Called automatically after FXML loads

        // Example actions – you’ll hook these up to real screens later
        manageFlightsButton.setOnAction(e ->
                System.out.println("Go to Manage Flights screen"));

        searchFlightsButton.setOnAction(e ->
                System.out.println("Go to Search Flights screen"));

        viewBookingsButton.setOnAction(e ->
                System.out.println("Go to View All Bookings screen"));

        viewCustomersButton.setOnAction(e ->
                System.out.println("Go to View Customers screen"));

        logoutButton.setOnAction(e ->
                System.out.println("Logout and go back to login/main menu"));
    }

    // Optional: call this after loading to show the admin's name
    public void setAdminName(String username) {
        welcomeLabel.setText("Welcome, Admin " + username);
    }
}

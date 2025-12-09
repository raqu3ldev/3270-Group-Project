package UI;

import Model.Flight;
import Model.User;
import Service.FlightService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.util.List;

public class CustomerFlightSearchController {

    @FXML
    private TextField fromField;

    @FXML
    private TextField toField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button searchButton;

    @FXML
    private Button backButton;

    private User user;
    private Stage stage;
    private FlightService flightService;

    @FXML
    public void initialize() {
        flightService = new FlightService();
        searchButton.setOnAction(e -> handleSearch());
        backButton.setOnAction(e -> goBack());
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void handleSearch() {
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        LocalDate date = datePicker.getValue();

        // Validate at least one search criteria is provided
        if (from.isEmpty() && to.isEmpty() && date == null) {
            showAlert(Alert.AlertType.WARNING, "Search Error",
                    "Please enter at least one search criteria!");
            return;
        }

        try {
            // Search for flights
            List<Flight> flights = flightService.searchFlights(from, to, date);

            if (flights.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No Results",
                        "No flights found matching your criteria.");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Search Results",
                        "Found " + flights.size() + " flights!");
                // TODO: Display results in a table or new window
            }

        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error searching flights: " + ex.getMessage());
        }
    }

    private void goBack() {
        try {
            CustomerMenu customerMenu = new CustomerMenu(stage, user);
            customerMenu.show();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error going back: " + ex.getMessage());
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
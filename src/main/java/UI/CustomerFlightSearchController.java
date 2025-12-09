package UI;

import Model.Flight;
import Model.User;
import Service.FlightService;
import Service.BookingService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @FXML
    private TableView<Flight> flightsTable;

    @FXML
    private TableColumn<Flight, String> flightNumberCol;

    @FXML
    private TableColumn<Flight, String> fromCityCol;

    @FXML
    private TableColumn<Flight, String> toCityCol;

    @FXML
    private TableColumn<Flight, String> departureTimeCol;

    @FXML
    private TableColumn<Flight, String> arrivalTimeCol;

    @FXML
    private TableColumn<Flight, Integer> availableSeatsCol;

    @FXML
    private TableColumn<Flight, Double> priceCol;

    @FXML
    private Button bookFlightButton;

    private User user;
    private Stage stage;
    private FlightService flightService;
    private BookingService bookingService;
    private ObservableList<Flight> flightsList;

    @FXML
    public void initialize() {
        flightService = new FlightService();
        bookingService = new BookingService();
        flightsList = FXCollections.observableArrayList();

        setupTable();
        searchButton.setOnAction(e -> handleSearch());
        backButton.setOnAction(e -> goBack());
        bookFlightButton.setOnAction(e -> handleBookFlight());
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setupTable() {
        flightNumberCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        fromCityCol.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        toCityCol.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        departureTimeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrivalTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        availableSeatsCol.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        flightsTable.setItems(flightsList);
    }

    private void handleSearch() {
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        LocalDate date = datePicker.getValue();

        try {
            // Search for flights (empty fields will return all flights)
            List<Flight> flights = flightService.searchFlights(from, to, date);
            flightsList.clear();
            flightsList.addAll(flights);

            if (flights.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "No Results",
                        "No flights found matching your criteria.");
            } else {
                String message = (from.isEmpty() && to.isEmpty() && date == null)
                        ? "Showing all available flights (" + flights.size() + " flights)"
                        : "Found " + flights.size() + " flights!";
                showAlert(Alert.AlertType.INFORMATION, "Search Results", message);
            }

        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error searching flights: " + ex.getMessage());
        }
    }

    private void handleBookFlight() {
        Flight selectedFlight = flightsTable.getSelectionModel().getSelectedItem();

        if (selectedFlight == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection",
                    "Please select a flight to book.");
            return;
        }

        try {
            // Check if flight is full
            if (selectedFlight.isFull()) {
                showAlert(Alert.AlertType.ERROR, "Flight Full",
                        "This flight is fully booked.");
                return;
            }

            // Check for conflicts
            if (bookingService.hasBookingConflict(user.getUserId(), selectedFlight)) {
                showAlert(Alert.AlertType.ERROR, "Booking Conflict",
                        "You already have a booking for this flight or a conflicting flight at the same time.");
                return;
            }

            // Book the flight
            boolean success = bookingService.bookFlight(user.getUserId(), selectedFlight.getFlightId());

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Flight booked successfully!");
                handleSearch(); // Refresh the list
            } else {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Failed to book flight. Please try again.");
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error booking flight: " + ex.getMessage());
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
package UI;

import Model.Flight;
import Model.Booking;
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
public class CustomerMenuController {
    @FXML private Label welcomeLabel;

    // Search fields
    @FXML private TextField fromCityField;
    @FXML private TextField toCityField;
    @FXML private DatePicker flightDatePicker;
    @FXML private Button searchButton;

    // Available flights table
    @FXML private TableView<Flight> availableFlightsTable;
    @FXML private TableColumn<Flight, String> flightNumberCol;
    @FXML private TableColumn<Flight, String> fromCityCol;
    @FXML private TableColumn<Flight, String> toCityCol;
    @FXML private TableColumn<Flight, String> departureTimeCol;
    @FXML private TableColumn<Flight, String> arrivalTimeCol;
    @FXML private TableColumn<Flight, Integer> availableSeatsCol;
    @FXML private TableColumn<Flight, Double> priceCol;
    @FXML private Button bookFlightButton;

    // My bookings table
    @FXML private TableView<Booking> myBookingsTable;
    @FXML private TableColumn<Booking, String> bookedFlightNumberCol;
    @FXML private TableColumn<Booking, String> bookedFromCityCol;
    @FXML private TableColumn<Booking, String> bookedToCityCol;
    @FXML private TableColumn<Booking, String> bookedDepartureTimeCol;
    @FXML private TableColumn<Booking, String> statusCol;
    @FXML private Button cancelBookingButton;
    @FXML private Button refreshBookingsButton;

    @FXML private Button logoutButton;

    private User user;
    private Stage stage;
    private FlightService flightService;
    private BookingService bookingService;

    private ObservableList<Flight> availableFlightsList;
    private ObservableList<Booking> myBookingsList;

    @FXML
    public void initialize() {
        flightService = new FlightService();
        bookingService = new BookingService();

        availableFlightsList = FXCollections.observableArrayList();
        myBookingsList = FXCollections.observableArrayList();

        setupAvailableFlightsTable();
        setupMyBookingsTable();
        setupEventHandlers();
    }

    public void setUser(User user) {
        this.user = user;
        welcomeLabel.setText("Welcome, " + user.getFirstName() + " " + user.getLastName());
        loadMyBookings();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setupAvailableFlightsTable() {
        flightNumberCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        fromCityCol.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        toCityCol.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        departureTimeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrivalTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        availableSeatsCol.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        availableFlightsTable.setItems(availableFlightsList);
    }

    private void setupMyBookingsTable() {
        bookedFlightNumberCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        bookedFromCityCol.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        bookedToCityCol.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        bookedDepartureTimeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        myBookingsTable.setItems(myBookingsList);
    }

    private void setupEventHandlers() {
        searchButton.setOnAction(e -> handleSearch());
        bookFlightButton.setOnAction(e -> handleBookFlight());
        cancelBookingButton.setOnAction(e -> handleCancelBooking());
        refreshBookingsButton.setOnAction(e -> loadMyBookings());
        logoutButton.setOnAction(e -> handleLogout());
    }

    private void handleSearch() {
        String fromCity = fromCityField.getText().trim();
        String toCity = toCityField.getText().trim();
        LocalDate flightDate = flightDatePicker.getValue();

        try {
            List<Flight> flights = flightService.searchFlights(fromCity, toCity, flightDate);
            availableFlightsList.clear();
            availableFlightsList.addAll(flights);

            if (flights.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Search Results",
                        "No flights found matching your criteria.");
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error searching flights: " + ex.getMessage());
        }
    }

    private void handleBookFlight() {
        Flight selectedFlight = availableFlightsTable.getSelectionModel().getSelectedItem();

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

            // Check for conflicts (same flight or time conflict)
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
                loadMyBookings();
                handleSearch(); // Refresh available flights
            } else {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Failed to book flight. Please try again.");
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error booking flight: " + ex.getMessage());
        }
    }

    private void handleCancelBooking() {
        Booking selectedBooking = myBookingsTable.getSelectionModel().getSelectedItem();

        if (selectedBooking == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection",
                    "Please select a booking to cancel.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Cancellation");
        confirmAlert.setHeaderText("Cancel Booking");
        confirmAlert.setContentText("Are you sure you want to cancel this booking?");

        if (confirmAlert.showAndWait().get() == ButtonType.OK) {
            try {
                boolean success = bookingService.cancelBooking(selectedBooking.getBookingId());

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success",
                            "Booking cancelled successfully!");
                    loadMyBookings();
                    handleSearch(); // Refresh available flights
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error",
                            "Failed to cancel booking.");
                }
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Error cancelling booking: " + ex.getMessage());
            }
        }
    }

    private void loadMyBookings() {
        try {
            List<Booking> bookings = bookingService.getUserBookings(user.getUserId());
            myBookingsList.clear();
            myBookingsList.addAll(bookings);
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error loading bookings: " + ex.getMessage());
        }
    }

    private void handleLogout() {
        try {
            LoginScreen loginScreen = new LoginScreen(stage);
            loginScreen.show();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error returning to login: " + ex.getMessage());
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

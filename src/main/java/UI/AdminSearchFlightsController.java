package UI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.Flight;
import Service.FlightService;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AdminSearchFlightsController {

    @FXML
    private TextField fromCityField;

    @FXML
    private TextField toCityField;

    @FXML
    private DatePicker departureDatePicker;

    @FXML
    private Button searchButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<Flight> flightsTable;

    @FXML
    private TableColumn<Flight, Integer> flightIdColumn;

    @FXML
    private TableColumn<Flight, String> flightNumberColumn;

    @FXML
    private TableColumn<Flight, String> fromCityColumn;

    @FXML
    private TableColumn<Flight, String> toCityColumn;

    @FXML
    private TableColumn<Flight, LocalDateTime> departureTimeColumn;

    @FXML
    private TableColumn<Flight, LocalDateTime> arrivalTimeColumn;

    @FXML
    private TableColumn<Flight, Integer> capacityColumn;

    @FXML
    private TableColumn<Flight, Integer> availableSeatsColumn;

    @FXML
    private TableColumn<Flight, Double> priceColumn;

    @FXML
    private Label messageLabel;

    private FlightService flightService;

    @FXML
    public void initialize() {
        flightService = new FlightService();

        // Set up table columns
        flightIdColumn.setCellValueFactory(new PropertyValueFactory<>("flightId"));
        flightNumberColumn.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        fromCityColumn.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        toCityColumn.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrivalTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("basePrice"));

        // Set button actions
        searchButton.setOnAction(e -> searchFlights());
        backButton.setOnAction(e -> goBack());

        // Load all flights initially
        loadAllFlights();
    }

    private void searchFlights() {
        String fromCity = fromCityField.getText().trim();
        String toCity = toCityField.getText().trim();
        LocalDate flightDate = departureDatePicker.getValue();

        try {
            List<Flight> flights = flightService.searchFlights(fromCity, toCity, flightDate);

            if (flights.isEmpty()) {
                showMessage("No flights found matching your search criteria.");
                flightsTable.getItems().clear();
            } else {
                flightsTable.getItems().setAll(flights);
                showMessage("Found " + flights.size() + " flight(s).");
            }
        } catch (Exception ex) {
            showError("Error searching flights: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void loadAllFlights() {
        try {
            List<Flight> flights = flightService.getAllFlights();
            flightsTable.getItems().setAll(flights);
            showMessage("Showing all flights (" + flights.size() + " total)");
        } catch (Exception ex) {
            showError("Error loading flights: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void goBack() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            AdminMenu adminMenu = new AdminMenu(stage);
            adminMenu.show();
        } catch (IOException ex) {
            showError("Error returning to admin menu: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void showMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: green;");
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: red;");
    }
}
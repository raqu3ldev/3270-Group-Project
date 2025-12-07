package UI;

import Model.User;
import Model.Flight;
import Service.FlightService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminManageFlightsController {

    @FXML
    private TableView<Flight> flightsTable;

    @FXML
    private TableColumn<Flight, String> flightNumberCol;

    @FXML
    private TableColumn<Flight, String> fromCityCol;

    @FXML
    private TableColumn<Flight, String> toCityCol;

    @FXML
    private TableColumn<Flight, String> departureCol;

    @FXML
    private TableColumn<Flight, Integer> seatsCol;

    @FXML
    private TableColumn<Flight, Double> priceCol;

    @FXML
    private TextField flightNumberField;

    @FXML
    private TextField fromCityField;

    @FXML
    private TextField toCityField;

    @FXML
    private TextField departureDateField;

    @FXML
    private TextField departureTimeField;

    @FXML
    private TextField arrivalDateField;

    @FXML
    private TextField arrivalTimeField;

    @FXML
    private TextField totalSeatsField;

    @FXML
    private TextField priceField;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    private User user;
    private Stage stage;
    private FlightService flightService;
    private ObservableList<Flight> flightsList;

    @FXML
    public void initialize() {
        flightService = new FlightService();
        flightsList = FXCollections.observableArrayList();

        setupTable();
        setupButtons();
        loadFlights();
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
        departureCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        flightsTable.setItems(flightsList);
    }

    private void setupButtons() {
        addButton.setOnAction(e -> addFlight());
        updateButton.setOnAction(e -> updateFlight());
        deleteButton.setOnAction(e -> deleteFlight());
        backButton.setOnAction(e -> goBack());
    }

    private void loadFlights() {
        try {
            List<Flight> flights = flightService.getAllFlights();
            flightsList.clear();
            flightsList.addAll(flights);
        } catch (Exception ex) {
            showError("Error loading flights: " + ex.getMessage());
        }
    }

    private void addFlight() {
        try {
            // Get input values
            String flightNumber = flightNumberField.getText();
            String fromCity = fromCityField.getText();
            String toCity = toCityField.getText();
            String departureDate = departureDateField.getText();
            String departureTime = departureTimeField.getText();
            String arrivalDate = arrivalDateField.getText();
            String arrivalTime = arrivalTimeField.getText();
            int totalSeats = Integer.parseInt(totalSeatsField.getText());
            double price = Double.parseDouble(priceField.getText());

            // Create departure and arrival times
            String departureString = departureDate + " " + departureTime;
            String arrivalString = arrivalDate + " " + arrivalTime;

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime departure = LocalDateTime.parse(departureString, formatter);
            LocalDateTime arrival = LocalDateTime.parse(arrivalString, formatter);

            // Create flight object
            Flight flight = new Flight();
            flight.setFlightNumber(flightNumber);
            flight.setFromCity(fromCity);
            flight.setToCity(toCity);
            flight.setDepartureTime(departure);
            flight.setArrivalTime(arrival);
            flight.setTotalSeats(totalSeats);
            flight.setAvailableSeats(totalSeats);
            flight.setPrice(price);

            // Add to database
            boolean success = flightService.addFlight(flight);

            if (success) {
                showMessage("Flight added successfully!");
                clearFields();
                loadFlights();
            } else {
                showError("Failed to add flight.");
            }

        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers for seats and price.");
        } catch (Exception ex) {
            showError("Error adding flight: " + ex.getMessage());
        }
    }

    private void updateFlight() {
        Flight selectedFlight = flightsTable.getSelectionModel().getSelectedItem();

        if (selectedFlight == null) {
            showError("Please select a flight to update.");
            return;
        }

        try {
            selectedFlight.setFlightNumber(flightNumberField.getText());
            selectedFlight.setFromCity(fromCityField.getText());
            selectedFlight.setToCity(toCityField.getText());
            selectedFlight.setTotalSeats(Integer.parseInt(totalSeatsField.getText()));
            selectedFlight.setPrice(Double.parseDouble(priceField.getText()));

            boolean success = flightService.updateFlight(selectedFlight);

            if (success) {
                showMessage("Flight updated successfully!");
                clearFields();
                loadFlights();
            } else {
                showError("Failed to update flight.");
            }

        } catch (Exception ex) {
            showError("Error updating flight: " + ex.getMessage());
        }
    }

    private void deleteFlight() {
        Flight selectedFlight = flightsTable.getSelectionModel().getSelectedItem();

        if (selectedFlight == null) {
            showError("Please select a flight to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete Flight");
        confirm.setContentText("Are you sure you want to delete this flight?");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                boolean success = flightService.deleteFlight(selectedFlight.getFlightId());

                if (success) {
                    showMessage("Flight deleted successfully!");
                    clearFields();
                    loadFlights();
                } else {
                    showError("Failed to delete flight.");
                }

            } catch (Exception ex) {
                showError("Error deleting flight: " + ex.getMessage());
            }
        }
    }

    private void goBack() {
        try {
            AdminMenu adminMenu = new AdminMenu(stage, user);
            adminMenu.show();
        } catch (Exception ex) {
            showError("Error going back: " + ex.getMessage());
        }
    }

    private void clearFields() {
        flightNumberField.clear();
        fromCityField.clear();
        toCityField.clear();
        departureDateField.clear();
        departureTimeField.clear();
        arrivalDateField.clear();
        arrivalTimeField.clear();
        totalSeatsField.clear();
        priceField.clear();
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

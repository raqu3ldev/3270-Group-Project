package UI;

import Model.Flight;
import Service.FlightService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

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
        showMessage("Add Flight - Coming soon!");
    }

    private void updateFlight() {
        Flight selected = flightsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Please select a flight to update.");
            return;
        }

        showMessage("Update Flight - Coming soon!");
    }

    private void deleteFlight() {
        Flight selected = flightsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Please select a flight to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete Flight");
        confirm.setContentText("Are you sure you want to delete this flight?");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                boolean success = flightService.deleteFlight(selected.getFlightId());

                if (success) {
                    showMessage("Flight deleted successfully!");
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
            Stage stage = (Stage) backButton.getScene().getWindow();
            AdminMenu adminMenu = new AdminMenu(stage);
            adminMenu.show();
        } catch (Exception ex) {
            showError("Error going back: " + ex.getMessage());
        }
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
package UI;

import Model.Booking;
import Service.BookingService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.io.IOException;

public class AdminBookingsController {

    @FXML
    private TableView<Booking> bookingsTable;

    @FXML
    private TableColumn<Booking, Integer> customerIdCol;

    @FXML
    private TableColumn<Booking, Integer> bookingIdCol;

    @FXML
    private TableColumn<Booking, String> fromCol;

    @FXML
    private TableColumn<Booking, String> toCol;

    @FXML
    private TableColumn<Booking, String> dateCol;

    @FXML
    private TableColumn<Booking, String> bookedOnCol;

    @FXML
    private Button refreshButton;

    @FXML
    private Button backButton;

    private BookingService bookingService;
    private ObservableList<Booking> bookingsList;

    @FXML
    public void initialize() {
        bookingService = new BookingService();
        bookingsList = FXCollections.observableArrayList();

        setupTable();
        setupButtons();
        loadBookings();
    }

    private void setupTable() {
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        bookingIdCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        fromCol.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        bookedOnCol.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));

        bookingsTable.setItems(bookingsList);
    }

    private void setupButtons() {
        refreshButton.setOnAction(e -> loadBookings());
        backButton.setOnAction(e -> goBack());
    }

    private void loadBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            bookingsList.clear();
            bookingsList.addAll(bookings);
        } catch (Exception ex) {
            showError("Error loading bookings: " + ex.getMessage());
        }
    }

    private void goBack() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            AdminMenu adminMenu = new AdminMenu(stage);
            adminMenu.show();
        } catch (IOException ex) {
            showError("Error going back: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
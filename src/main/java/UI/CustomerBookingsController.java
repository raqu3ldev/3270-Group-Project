package UI;

import Model.User;
import Model.Booking;
import Service.BookingService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.util.List;

public class CustomerBookingsController {

    @FXML
    private TableView<Booking> bookingsTable;

    @FXML
    private TableColumn<Booking, String> flightNumberCol;

    @FXML
    private TableColumn<Booking, String> fromCityCol;

    @FXML
    private TableColumn<Booking, String> toCityCol;

    @FXML
    private TableColumn<Booking, String> departureTimeCol;

    @FXML
    private TableColumn<Booking, String> arrivalTimeCol;

    @FXML
    private TableColumn<Booking, String> statusCol;

    @FXML
    private Button backButton;

    @FXML
    private Button cancelButton;

    private User user;
    private Stage stage;
    private BookingService bookingService;
    private ObservableList<Booking> bookingsList;

    @FXML
    public void initialize() {
        bookingService = new BookingService();
        bookingsList = FXCollections.observableArrayList();

        setupTable();
        setupButtons();
    }

    public void setUser(User user) {
        this.user = user;
        loadBookings();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadBookings() {
        if (user != null) {
            try {
                List<Booking> bookings = bookingService.getUserBookings(user.getUserId());
                bookingsList.clear();
                bookingsList.addAll(bookings);
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error",
                        "Error loading bookings: " + ex.getMessage());
            }
        }
    }

    private void setupTable() {
        flightNumberCol.setCellValueFactory(new PropertyValueFactory<>("flightNumber"));
        fromCityCol.setCellValueFactory(new PropertyValueFactory<>("fromCity"));
        toCityCol.setCellValueFactory(new PropertyValueFactory<>("toCity"));
        departureTimeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        arrivalTimeCol.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));

        bookingsTable.setItems(bookingsList);
    }

    private void setupButtons() {
        backButton.setOnAction(e -> goBack());
        cancelButton.setOnAction(e -> cancelBooking());
    }

    private void cancelBooking() {
        Booking selected = bookingsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection",
                    "Please select a booking to cancel.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Cancellation");
        confirm.setHeaderText("Cancel Booking");
        confirm.setContentText("Are you sure you want to cancel this booking?");

        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                boolean success = bookingService.cancelBooking(selected.getBookingId());

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success",
                            "Booking cancelled successfully!");
                    loadBookings();
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

    private void goBack() {
        try {
            CustomerMenu customerMenu = new CustomerMenu(stage, user);
            customerMenu.show();
        } catch (IOException ex) {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Error going back: " + ex.getMessage());
            ex.printStackTrace();
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
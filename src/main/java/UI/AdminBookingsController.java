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
import java.util.List;

public class AdminBookingsController {

    @FXML
    private TableView<Booking> bookingsTable;

    @FXML
    private TableColumn<Booking, String> flightNumberCol;

    @FXML
    private TableColumn<Booking, String> fromCityCol;

    @FXML
    private TableColumn<Booking, String> toCityCol;

    @FXML
    private TableColumn<Booking, String> departureCol;

    @FXML
    private TableColumn<Booking, String> statusCol;

    @FXML
    private Button refreshButton;

    @FXML
    private Button backButton;

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
        loadBookings();
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
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

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
            AdminMenu adminMenu = new AdminMenu(stage, user);
            adminMenu.show();
        } catch (Exception ex) {
            showError("Error going back: " + ex.getMessage());
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

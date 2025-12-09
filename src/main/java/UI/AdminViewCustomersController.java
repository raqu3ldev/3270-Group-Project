package UI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Model.User;
import Service.UserService;
import java.io.IOException;
import java.util.List;

public class AdminViewCustomersController {

    @FXML
    private TableView<User> customersTable;

    @FXML
    private TableColumn<User, Integer> userIdColumn;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> firstNameColumn;

    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> addressColumn;

    @FXML
    private TableColumn<User, String> stateColumn;

    @FXML
    private Button refreshButton;

    @FXML
    private Button backButton;

    @FXML
    private Label messageLabel;

    @FXML
    private Label totalCustomersLabel;

    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserService();

        // Set up table columns - matching your User.java fields
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        // Set button actions
        refreshButton.setOnAction(e -> loadCustomers());
        backButton.setOnAction(e -> goBack());

        // Load customers on start
        loadCustomers();
    }

    private void loadCustomers() {
        try {
            List<User> users = userService.getAllUsers();
            customersTable.getItems().setAll(users);

            // Count only customers (not admins) - FIXED LINE
            long customerCount = users.stream()
                    .filter(u -> !"ADMIN".equalsIgnoreCase(u.getRole()))
                    .count();

            totalCustomersLabel.setText("Total Users: " + users.size() + " (Customers: " + customerCount + ")");
            showMessage("Users loaded successfully.");
        } catch (Exception ex) {
            showError("Error loading users: " + ex.getMessage());
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
package UserInterface;

import Controller.ReservationController;
import DAO.ReservationDAO;
import Model.Reservations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class reservationsUI {

    @FXML
    private Button addButton, searchButton, editButton, deleteButton, backButton;

    @FXML
    private TextField searchReservations;
    
    @FXML
    private TableView<ReservationDisplay> tableView;
    
    @FXML
    private TableColumn<ReservationDisplay, String> idColumn;
    
    @FXML
    private TableColumn<ReservationDisplay, String> tableIdColumn;
    
    @FXML
    private TableColumn<ReservationDisplay, String> reserveNameColumn;
    
    @FXML
    private TableColumn<ReservationDisplay, String> dateAndTimeColumn;
    
    @FXML
    private TableColumn<ReservationDisplay, String> isActiveColumn;
    
    private ObservableList<ReservationDisplay> reservationsList = FXCollections.observableArrayList();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    private void initialize() {
        // Setup TableView columns
        setupTableViewColumns();
        
        // Load all reservations
        loadAllReservations();

        addButton.setOnAction(e -> {
            handleAdd();
            loadAllReservations(); // Refresh table after add
        });
        searchButton.setOnAction(e -> handleSearch());
        editButton.setOnAction(e -> {
            handleEdit();
            loadAllReservations(); // Refresh table after edit
        });
        deleteButton.setOnAction(e -> {
            handleDelete();
            loadAllReservations(); // Refresh table after delete
        });

        if (backButton != null) {
            backButton.setOnAction(e ->
                    SceneNavigator.switchScene(backButton, "/Resources/Transactions/transactionMenu.fxml"));
        }
    }
    
    private void setupTableViewColumns() {
        if (idColumn != null) {
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        }
        if (tableIdColumn != null) {
            tableIdColumn.setCellValueFactory(new PropertyValueFactory<>("tableId"));
        }
        if (reserveNameColumn != null) {
            reserveNameColumn.setCellValueFactory(new PropertyValueFactory<>("reserveName"));
        }
        if (dateAndTimeColumn != null) {
            dateAndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));
        }
        if (isActiveColumn != null) {
            isActiveColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        }
        if (tableView != null) {
            tableView.setItems(reservationsList);
        }
    }
    
    private void loadAllReservations() {
        reservationsList.clear();
        ArrayList<Reservations> allReservations = ReservationController.getAllReservations();
        
        for (Reservations r : allReservations) {
            String id = String.valueOf(r.getRequestId());
            String tableId = String.valueOf(r.getTableId());
            String reserveName = r.getReserveName();
            String dateAndTime = r.getDateAndTime().format(DATE_FORMATTER);
            String isActive = r.getIsActive() ? "Active" : "Inactive";
            
            reservationsList.add(new ReservationDisplay(id, tableId, reserveName, dateAndTime, isActive, r));
        }
    }

    // ===================== ADD =====================
    @FXML
    private void handleAdd() {
        TextInputDialog tableDialog = new TextInputDialog();
        tableDialog.setTitle("Add Reservation");
        tableDialog.setHeaderText("Enter Table ID:");
        Optional<String> tableInput = tableDialog.showAndWait();

        tableInput.ifPresent(tableStr -> {
            int tableID;
            try {
                tableID = Integer.parseInt(tableStr);
            } catch (NumberFormatException e) {
                SceneNavigator.showError("Table ID must be a number.");
                return;
            }

            TextInputDialog nameDialog = new TextInputDialog();
            nameDialog.setTitle("Add Reservation");
            nameDialog.setHeaderText("Enter Customer Name:");
            Optional<String> nameInput = nameDialog.showAndWait();

            nameInput.ifPresent(name -> {
                LocalDateTime time = LocalDateTime.now().plusHours(1);
                Reservations r = ReservationController.addReservation(tableID, name, time);

                if (r != null) {
                    SceneNavigator.showInfo(
                            "Reservation created successfully!\n" +
                                    "Reservation ID: " + r.getRequestId() + "\n" +
                                    "Name: " + r.getReserveName() + "\n" +
                                    "Table: " + r.getTableId() + "\n" +
                                    "Time: " + r.getDateAndTime().format(DATE_FORMATTER)
                    );
                } else {
                    SceneNavigator.showError("Reservation creation failed. Please try again.");
                }
            });
        });
    }

    // ===================== SEARCH =====================
    @FXML
    private void handleSearch() {
        String input = searchReservations.getText();
        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            SceneNavigator.showError("Enter a valid numeric Reservation ID to search.");
            return;
        }

        Reservations r = ReservationController.getReservation(id);

        if (r != null) {
            SceneNavigator.showInfo(
                    "Reservation Found:\n" +
                            "ID: " + r.getRequestId() + "\n" +
                            "Name: " + r.getReserveName() + "\n" +
                            "Table: " + r.getTableId() + "\n" +
                            "Time: " + r.getDateAndTime().format(DATE_FORMATTER)
            );
        } else {
            SceneNavigator.showError("No reservation found with ID: " + id);
        }
    }

    // ===================== EDIT =====================
    @FXML
    private void handleEdit() {
        String input = searchReservations.getText();
        int id;

        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            SceneNavigator.showError("Enter a valid numeric Reservation ID to edit.");
            return;
        }

        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Edit Reservation");
        nameDialog.setHeaderText("Enter new Customer Name:");
        Optional<String> nameInput = nameDialog.showAndWait();

        nameInput.ifPresent(newName -> {
            LocalDateTime newTime = LocalDateTime.now().plusHours(2); // simplified new time
            boolean success = ReservationController.editReservation(id, newName, newTime);

            if (success) {
                SceneNavigator.showInfo("Reservation " + id + " updated successfully.");
            } else {
                SceneNavigator.showError("Update failed. Reservation may not exist.");
            }
        });
    }

    // ===================== DELETE =====================
    @FXML
    private void handleDelete() {
        String input = searchReservations.getText();
        int id;
        try {
            id = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            SceneNavigator.showError("Enter a valid numeric Reservation ID to delete.");
            return;
        }

        boolean success = ReservationController.deleteReservation(id);

        if (success) {
            SceneNavigator.showInfo("Reservation " + id + " deleted successfully.");
        } else {
            SceneNavigator.showError("Deletion failed. Reservation may not exist.");
        }
    }
    
    /**
     * Display model for reservations in the TableView
     */
    public static class ReservationDisplay {
        private final String id;
        private final String tableId;
        private final String reserveName;
        private final String dateAndTime;
        private final String isActive;
        private final Reservations reservation; // Store reference to original Reservation
        
        public ReservationDisplay(String id, String tableId, String reserveName, String dateAndTime, String isActive, Reservations reservation) {
            this.id = id;
            this.tableId = tableId;
            this.reserveName = reserveName;
            this.dateAndTime = dateAndTime;
            this.isActive = isActive;
            this.reservation = reservation;
        }
        
        public String getId() { return id; }
        public String getTableId() { return tableId; }
        public String getReserveName() { return reserveName; }
        public String getDateAndTime() { return dateAndTime; }
        public String getIsActive() { return isActive; }
        public Reservations getReservation() { return reservation; }
    }
}

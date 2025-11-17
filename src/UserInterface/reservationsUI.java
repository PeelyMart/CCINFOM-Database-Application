package UserInterface;

import Controller.ReservationController;
import DAO.ReservationDAO;
import Model.Reservations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.geometry.Insets;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    private Integer currentTableId = null; // Store current table filter

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
            // Filter by table if currentTableId is set
            if (currentTableId != null && r.getTableId() != currentTableId) {
                continue;
            }
            
            String id = String.valueOf(r.getRequestId());
            String tableId = String.valueOf(r.getTableId());
            String reserveName = r.getReserveName();
            String dateAndTime = r.getDateAndTime().format(DATE_FORMATTER);
            String isActive = r.getIsActive() ? "Active" : "Inactive";
            
            reservationsList.add(new ReservationDisplay(id, tableId, reserveName, dateAndTime, isActive, r));
        }
    }
    
    /**
     * Called by SceneNavigator to set table filter
     */
    public void setData(Object data) {
        if (data instanceof Model.Table) {
            Model.Table table = (Model.Table) data;
            currentTableId = table.getTableId();
            loadAllReservations(); // Reload with table filter
        } else {
            currentTableId = null; // Show all reservations
            loadAllReservations();
        }
    }

    // ===================== ADD =====================
    @FXML
    private void handleAdd() {
        // Step 1: Load available tables
        List<Model.Table> allTables = DAO.TableDAO.getAllTables();
        if (allTables == null || allTables.isEmpty()) {
            SceneNavigator.showError("No tables available.");
            return;
        }

        // Step 2: Create readable table labels
        List<String> tableOptions = new ArrayList<>();
        for (Model.Table table : allTables) {
            tableOptions.add("Table " + table.getTableId() + " (Capacity: " + table.getCapacity() + ")");
        }

        // Step 3: Choose table
        ChoiceDialog<String> tableDialog = new ChoiceDialog<>(tableOptions.get(0), tableOptions);
        tableDialog.setTitle("Add Reservation");
        tableDialog.setHeaderText("Select Table:");
        tableDialog.setContentText("Choose a table:");

        Optional<String> tableResult = tableDialog.showAndWait();
        if (!tableResult.isPresent()) return;

        String selectedTableStr = tableResult.get();

        // --- NO REGEX --- simple split parsing ---
        // Split "Table 5 (Capacity: 4)" into: "Table 5"
        String beforeParen = selectedTableStr.split("\\(")[0].trim();
        // beforeParen = "Table 5"

        String[] tableWords = beforeParen.split(" ");
        int tableID = Integer.parseInt(tableWords[1].trim());
        final int finalTableID = tableID;

        // Step 4: Input customer name
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Add Reservation");
        nameDialog.setHeaderText("Enter Customer Name:");
        Optional<String> nameInput = nameDialog.showAndWait();

        if (!nameInput.isPresent()) return;
        String custName = nameInput.get();

        // Step 5: Date + Time Picker Dialog (same as EDIT)
        Dialog<Pair<LocalDate, LocalTime>> dateTimeDialog =
                createDateTimePickerDialog(LocalDate.now(), LocalTime.now().plusHours(1));

        dateTimeDialog.setTitle("Add Reservation");
        dateTimeDialog.setHeaderText("Select Date and Time:");

        Optional<Pair<LocalDate, LocalTime>> dateTimeResult = dateTimeDialog.showAndWait();
        if (!dateTimeResult.isPresent()) return;

        LocalDate date = dateTimeResult.get().getKey();
        LocalTime time = dateTimeResult.get().getValue();
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        // Step 6: Send to controller
        Reservations r = ReservationController.addReservation(finalTableID, custName, dateTime);

        // Step 7: User feedback
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
    }

    // ===================== SEARCH =====================
    @FXML
    private void handleSearch() {
        String input = searchReservations.getText().trim();
        if (input.isEmpty()) {
            // If empty, reload all
            loadAllReservations();
            return;
        }
        
        ArrayList<Reservations> allReservations = ReservationController.getAllReservations();
        ArrayList<Reservations> matchingReservations = new ArrayList<>();
        
        // Search by name, date, table ID, or any field (partial match)
        String searchLower = input.toLowerCase();
        for (Reservations r : allReservations) {
            // Filter by table if set
            if (currentTableId != null && r.getTableId() != currentTableId) {
                continue;
            }
            
            // Check if name matches (partial)
            if (r.getReserveName().toLowerCase().contains(searchLower)) {
                matchingReservations.add(r);
                continue;
            }
            
            // Check if date matches (partial)
            String dateStr = r.getDateAndTime().format(DATE_FORMATTER);
            if (dateStr.toLowerCase().contains(searchLower)) {
                matchingReservations.add(r);
                continue;
            }
            
            // Check if table ID matches
            if (String.valueOf(r.getTableId()).contains(input)) {
                matchingReservations.add(r);
                continue;
            }
            
            // Check if reservation ID matches
            if (String.valueOf(r.getRequestId()).contains(input)) {
                matchingReservations.add(r);
            }
        }
        
        if (matchingReservations.isEmpty()) {
            SceneNavigator.showInfo("No reservations found matching: " + input);
            reservationsList.clear();
            return;
        }
        
        // Filter table view to show only matching reservations
        reservationsList.clear();
        for (Reservations r : matchingReservations) {
            String id = String.valueOf(r.getRequestId());
            String tableId = String.valueOf(r.getTableId());
            String reserveName = r.getReserveName();
            String dateAndTime = r.getDateAndTime().format(DATE_FORMATTER);
            String isActive = r.getIsActive() ? "Active" : "Inactive";
            
            reservationsList.add(new ReservationDisplay(id, tableId, reserveName, dateAndTime, isActive, r));
        }
        
        SceneNavigator.showInfo("Found " + matchingReservations.size() + " reservation(s) matching: " + input);
    }

    // ===================== EDIT =====================
    @FXML
    private void handleEdit() {

        // Step 0: Get selected reservation
        ReservationDisplay selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            SceneNavigator.showError("Please select a reservation to edit.");
            return;
        }

        Reservations reservation = selected.getReservation();
        if (reservation == null) {
            SceneNavigator.showError("Could not find reservation to edit.");
            return;
        }

        // Step 1: Load all tables
        List<Model.Table> allTables = DAO.TableDAO.getAllTables();
        if (allTables == null || allTables.isEmpty()) {
            SceneNavigator.showError("No tables available.");
            return;
        }

        // Step 2: Build choice list
        List<String> tableOptions = new ArrayList<>();
        int currentTableIndex = 0;

        for (int i = 0; i < allTables.size(); i++) {
            Model.Table t = allTables.get(i);
            tableOptions.add("Table " + t.getTableId() + " (Capacity: " + t.getCapacity() + ")");
            if (t.getTableId() == reservation.getTableId()) {
                currentTableIndex = i;
            }
        }

        // Step 3: Ask for new table
        ChoiceDialog<String> tableDialog = new ChoiceDialog<>(tableOptions.get(currentTableIndex), tableOptions);
        tableDialog.setTitle("Edit Reservation");
        tableDialog.setHeaderText("Select Table:");
        tableDialog.setContentText("Choose a table:");

        Optional<String> tableResult = tableDialog.showAndWait();
        if (!tableResult.isPresent()) return;

        String selectedTableStr = tableResult.get();

        // 🚫 NO REGEX — SIMPLE CLEAN PARSING
        // Format: "Table 5 (Capacity: X)"

        // Extract left part before the '('
        String[] parts = selectedTableStr.split("\\(");
        // parts[0] = "Table 5 "

        String[] tableWords = parts[0].trim().split(" ");
        // ["Table", "5"]

        int newTableID = Integer.parseInt(tableWords[1].trim());

        // Step 4: Input customer name
        TextInputDialog nameDialog = new TextInputDialog(reservation.getReserveName());
        nameDialog.setTitle("Edit Reservation");
        nameDialog.setHeaderText("Enter Customer Name:");
        Optional<String> nameInput = nameDialog.showAndWait();

        if (!nameInput.isPresent()) return;
        String newName = nameInput.get();

        // Step 5: Date + Time Picker
        Dialog<Pair<LocalDate, LocalTime>> dateTimeDialog = createDateTimePickerDialog(
                reservation.getDateAndTime().toLocalDate(),
                reservation.getDateAndTime().toLocalTime()
        );
        dateTimeDialog.setTitle("Edit Reservation");
        dateTimeDialog.setHeaderText("Select Date and Time:");

        Optional<Pair<LocalDate, LocalTime>> dateTimeResult = dateTimeDialog.showAndWait();
        if (!dateTimeResult.isPresent()) return;

        LocalDate newDate = dateTimeResult.get().getKey();
        LocalTime newTime = dateTimeResult.get().getValue();
        LocalDateTime newDateTime = LocalDateTime.of(newDate, newTime);

        // Step 6: Status dialog
        ChoiceDialog<String> statusDialog = new ChoiceDialog<>(
                reservation.getIsActive() ? "Active" : "Inactive",
                "Active", "Inactive"
        );
        statusDialog.setTitle("Edit Reservation");
        statusDialog.setHeaderText("Select Status:");

        Optional<String> statusResult = statusDialog.showAndWait();
        if (!statusResult.isPresent()) return;

        boolean isActive = statusResult.get().equals("Active");

        // Step 7: Update reservation
        boolean success = ReservationController.editReservation(
                reservation.getRequestId(),
                newTableID,
                newName,
                newDateTime,
                isActive
        );

        // Step 8: Notify user
        if (success) {
            SceneNavigator.showInfo("Reservation " + reservation.getRequestId() + " updated successfully.");
        } else {
            SceneNavigator.showError("Update failed.");
        }
    }

    // ===================== DELETE =====================
    @FXML
    private void handleDelete() {
        ReservationDisplay selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            SceneNavigator.showError("Please select a reservation to delete.");
            return;
        }
        
        Reservations reservation = selected.getReservation();
        if (reservation == null) {
            SceneNavigator.showError("Could not find reservation to delete.");
            return;
        }

        // Confirm deletion
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Reservation");
        confirm.setHeaderText("Delete reservation?");
        confirm.setContentText("Are you sure you want to delete reservation for: " + reservation.getReserveName() + "?");
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean success = ReservationController.deleteReservation(reservation.getRequestId());

            if (success) {
                SceneNavigator.showInfo("Reservation " + reservation.getRequestId() + " deleted successfully.");
            } else {
                SceneNavigator.showError("Deletion failed. Reservation may not exist.");
            }
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
    
    // Helper method to create date/time picker dialog
    private Dialog<Pair<LocalDate, LocalTime>> createDateTimePickerDialog(LocalDate initialDate, LocalTime initialTime) {
        Dialog<Pair<LocalDate, LocalTime>> dialog = new Dialog<>();
        dialog.setTitle("Select Date and Time");
        
        // Create date picker
        DatePicker datePicker = new DatePicker(initialDate != null ? initialDate : LocalDate.now());
        
        // Create hour and minute ComboBoxes
        ComboBox<Integer> hourBox = new ComboBox<>();
        ComboBox<Integer> minuteBox = new ComboBox<>();
        
        for (int i = 0; i < 24; i++) {
            hourBox.getItems().add(i);
        }
        for (int i = 0; i < 60; i += 15) { // 15-minute intervals
            minuteBox.getItems().add(i);
        }
        
        // Set initial values
        if (initialTime != null) {
            hourBox.setValue(initialTime.getHour());
            minuteBox.setValue((initialTime.getMinute() / 15) * 15); // Round to nearest 15 minutes
        } else {
            LocalTime now = LocalTime.now();
            hourBox.setValue(now.getHour());
            minuteBox.setValue((now.getMinute() / 15) * 15);
        }
        
        // Create layout
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        grid.add(new Label("Date:"), 0, 0);
        grid.add(datePicker, 1, 0);
        grid.add(new Label("Hour:"), 0, 1);
        grid.add(hourBox, 1, 1);
        grid.add(new Label("Minute:"), 0, 2);
        grid.add(minuteBox, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        
        // Add OK and Cancel buttons
        javafx.scene.control.ButtonType okButtonType = new javafx.scene.control.ButtonType("OK", javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, javafx.scene.control.ButtonType.CANCEL);
        
        // Convert result to Pair
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                LocalDate date = datePicker.getValue();
                Integer hour = hourBox.getValue();
                Integer minute = minuteBox.getValue();
                if (date != null && hour != null && minute != null) {
                    LocalTime time = LocalTime.of(hour, minute);
                    return new Pair<>(date, time);
                }
            }
            return null;
        });
        
        return dialog;
    }
}

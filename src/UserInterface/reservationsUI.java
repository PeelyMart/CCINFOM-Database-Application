package UserInterface;

import Controller.ReservationController;
import DAO.ReservationDAO;
import Model.Reservations;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import java.time.LocalDateTime;
import java.util.Optional;

public class reservationsUI {

    @FXML
    private Button addButton, searchButton, editButton, deleteButton, backButton;

    @FXML
    private TextField searchReservations;

    @FXML
    private void initialize() {

        addButton.setOnAction(e -> handleAdd());
        searchButton.setOnAction(e -> handleSearch());
        editButton.setOnAction(e -> handleEdit());
        deleteButton.setOnAction(e -> handleDelete());

        if (backButton != null) {
            backButton.setOnAction(e ->
                    SceneNavigator.switchScene(backButton, "/Resources/Transactions/transactionMenu.fxml"));
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
                                    "Time: " + r.getDateAndTime()
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
                            "Time: " + r.getDateAndTime()
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
}

package UserInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class reservationsUI {

    @FXML
    private Button addButton, searchButton, editButton, deleteButton, backButton;

    @FXML
    private TextField searchReservations;

    @FXML
    private void initialize() {
        // Hook buttons to actions
        addButton.setOnAction(e -> openAddReservation());
        searchButton.setOnAction(e -> SceneNavigator.testClick("SEARCH"));
        editButton.setOnAction(e -> SceneNavigator.testClick("EDIT"));
        deleteButton.setOnAction(e -> SceneNavigator.testClick("DELETE"));

        if (backButton != null) {
            backButton.setOnAction(e ->
                    SceneNavigator.switchScene(backButton, "/Resources/Transactions/transactionMenu.fxml"));
        }
    }

    // Method for TextField onAction in FXML
    @FXML
    private void searchStaffEntries() {
        // Placeholder logic for search
        SceneNavigator.testClick("SEARCH TEXTFIELD: " + searchReservations.getText());
    }

    private void openAddReservation() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass()
                    .getResource("/Resources/Transactions/reservationConfirmation.fxml"));
            AnchorPane root = loader.load();
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

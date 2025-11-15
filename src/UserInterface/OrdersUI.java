package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class OrdersUI {

    @FXML
    private Button addButton, searchButton, deleteButton, editButton, payButton, backButton;

    @FXML
    private TextField searchOrder;

    @FXML
    private void initialize() {
        // Non-functional buttons show test popup
        addButton.setOnAction(e -> SceneNavigator.testClick("ADD"));
        searchButton.setOnAction(e -> SceneNavigator.testClick("SEARCH"));
        deleteButton.setOnAction(e -> SceneNavigator.testClick("DELETE"));
        editButton.setOnAction(e -> SceneNavigator.testClick("EDIT"));

        // PAY button -> open payment UI
        payButton.setOnAction(e -> openPaymentChoice());

        // BACK button -> go back to Transaction Menu
        backButton.setOnAction(e ->
                SceneNavigator.switchScene(backButton, "/Resources/Transactions/transactionMenu.fxml"));
    }

    // Match the TextField onAction in FXML
    @FXML
    private void searchStaffEntries() {
        SceneNavigator.testClick("SEARCH TEXTFIELD: " + searchOrder.getText());
    }

    private void openPaymentChoice() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Payment Type");
            alert.setHeaderText("Are you a loyalty member?");
            alert.setContentText("Choose your payment type:");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);

            ButtonType result = alert.showAndWait().orElse(noButton);

            String fxml = (result == yesButton) ?
                    "/Resources/Transactions/paymentLM.fxml" :
                    "/Resources/Transactions/paymentNormal.fxml";

            SceneNavigator.switchScene(payButton, fxml);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

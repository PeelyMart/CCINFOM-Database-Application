package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TransactionMenuUI {

    @FXML
    private Button ordersButton, reservationsButton, takenButton, availableButton, backButton;

    @FXML
    private void initialize() {
        // Scene navigation buttons
        if (ordersButton != null) {
            ordersButton.setOnAction(e ->
                    SceneNavigator.switchScene(ordersButton, "/Resources/Transactions/orders.fxml"));
        }

        if (reservationsButton != null) {
            reservationsButton.setOnAction(e ->
                    SceneNavigator.switchScene(reservationsButton, "/Resources/Transactions/reservations.fxml"));
        }

        // Non-functional buttons show test popup
        if (takenButton != null) {
            takenButton.setOnAction(e -> SceneNavigator.testClick("TAKEN"));
        }

        if (availableButton != null) {
            availableButton.setOnAction(e -> SceneNavigator.testClick("AVAILABLE"));
        }

        // Back button → returns to main or previous scene
        if (backButton != null) {
            backButton.setOnAction(e ->
                    SceneNavigator.switchScene(backButton, "/Resources/MainMenu/dashboard.fxml"));
        }
    }
}

package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ReportUI {

    @FXML
    private Button salesButton, staffButton, loyaltyButton, menuButton;

    @FXML
    private void initialize() {
        // assign the same handler to all buttons
        if (salesButton != null)
            salesButton.setOnAction(e -> SceneNavigator.switchScene(salesButton, "/Resources/Reports/reports-view.fxml"));

        if (staffButton != null)
            staffButton.setOnAction(e -> SceneNavigator.switchScene(staffButton, "/Resources/Reports/reports-view.fxml"));

        if (loyaltyButton != null)
            loyaltyButton.setOnAction(e -> SceneNavigator.switchScene(loyaltyButton, "/Resources/Reports/reports-view.fxml"));

        if (menuButton != null)
            menuButton.setOnAction(e -> SceneNavigator.switchScene(menuButton, "/Resources/Reports/reports-view.fxml"));

    }

}

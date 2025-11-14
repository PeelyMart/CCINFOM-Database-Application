package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ReportsMenuUI {

    @FXML
    private Button tablesButton, staffButton, loyaltyButton, menuButton, backButton;

    @FXML
    private void initialize() {
        // assign handlers to buttons
        tablesButton.setOnAction(e ->
                SceneNavigator.switchScene(tablesButton, "/Resources/Reports/TableOptionsUI.fxml"));

        staffButton.setOnAction(e ->
                SceneNavigator.switchScene(staffButton, "/Resources/StaffOptions/staff-options.fxml"));

        menuButton.setOnAction(e ->
                SceneNavigator.switchScene(menuButton, "/Resources/MenuOptions/menu-options.fxml"));

        loyaltyButton.setOnAction(e ->
                SceneNavigator.switchScene(loyaltyButton, "/Resources/LoyaltyMemberOptions/loyalty-options.fxml"));
    }

    @FXML
    private void goBack() {
        SceneNavigator.switchScene(backButton, "/Resources/MainMenu/dashboard.fxml");
    }
}

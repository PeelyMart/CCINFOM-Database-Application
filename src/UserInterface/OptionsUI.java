package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class OptionsUI {

    @FXML
    private Button menuButton, staffButton, loyaltyMemberButton, tableButton, backButton;

    @FXML
    private void initialize() {
        // assign the same handler to all buttons
        if (tableButton != null)
            tableButton.setOnAction(e -> SceneNavigator.switchScene(tableButton, "/Resources/TableOptions/table-options.fxml"));

        if (staffButton != null)
            staffButton.setOnAction(e -> SceneNavigator.switchScene(staffButton, "/Resources/StaffOptions/staff-options.fxml"));

        if (loyaltyMemberButton != null)
            loyaltyMemberButton.setOnAction(e -> SceneNavigator.switchScene(loyaltyMemberButton, "/Resources/LoyaltyMemberOptions/loyalty-options.fxml"));

        if (menuButton != null)
            menuButton.setOnAction(e -> SceneNavigator.switchScene(menuButton, "/Resources/MenuOptions/menu-options.fxml"));

        if (backButton != null) {
            backButton.setOnAction(e -> SceneNavigator.switchScene(backButton, "/Resources/Transactions/transactionMenu.fxml"));
        }
    }
}

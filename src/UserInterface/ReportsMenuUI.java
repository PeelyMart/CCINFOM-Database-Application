package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ReportsMenuUI {

    @FXML
    private Button menuButton;

    @FXML
    private void initialize() {
        // Assign action to the "More Info" button
        menuButton.setOnAction(e -> {
            // Go to another scene when clicked
            SceneNavigator.switchScene(menuButton, "/Resources/Reports/reports-view.fxml");
        });
    }
}

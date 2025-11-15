package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class StaffOptionsUI {

    @FXML
    private Button searchStaffButton;

    @FXML
    private Button addStaffButton;

    @FXML
    private Button removeStaffButton;

    @FXML
    private Button updateStaffButton;

    @FXML
    private TextField searchStaff;

    @FXML
    private ScrollPane scrollPane; // optional if you want to control scroll

    @FXML
    private AnchorPane anchorPane; // the content inside the scrollpane

    // --- BUTTON HANDLERS ---

    @FXML
    private Button backButton;

    @FXML
    public void goBack() {
        SceneNavigator.switchScene(backButton, "/Resources/MainMenu/dashboard.fxml");
    }

    @FXML
    private void searchStaffButtonClick() {
        System.out.println("Search button clicked!");
        System.out.println("Search term: " + searchStaff.getText());
    }

    @FXML
    private void addStaffButtonClick() {
        System.out.println("Add Staff button clicked!");
    }

    @FXML
    private void removeStaffButtonClick() {
        System.out.println("Remove Staff button clicked!");
    }

    @FXML
    private void updateStaffButtonClick() {
        System.out.println("Update Staff button clicked!");
    }

    // Triggered when ENTER is pressed in the text field
    @FXML
    private void searchStaffEntries() {
        System.out.println("ENTER search: " + searchStaff.getText());
    }
}

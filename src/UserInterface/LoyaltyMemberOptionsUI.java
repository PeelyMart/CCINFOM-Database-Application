package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoyaltyMemberOptionsUI {

    @FXML
    private Button searchLoyaltyButton;

    @FXML
    private Button addLoyaltyButton;

    @FXML
    private Button removeLoyaltyButton;

    @FXML
    private Button updateLoyaltyButton;

    @FXML
    private TextField searchMemberID;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button backButton;

    @FXML
    public void goBack() {
        SceneNavigator.switchScene(backButton, "/Resources/MainMenu/dashboard.fxml");
    }

    // ----- BUTTON HANDLERS -----

    @FXML
    private void searchLoyaltyButtonClick() {
        System.out.println("Search Loyalty clicked!");
        System.out.println("Search ID = " + searchMemberID.getText());
    }

    @FXML
    private void addLoyaltyButtonClick() {
        System.out.println("Add Loyalty clicked!");
    }

    @FXML
    private void removeLoyaltyButtonClick() {
        System.out.println("Remove Loyalty clicked!");
    }

    @FXML
    private void updateLoyaltyButtonClick() {
        System.out.println("Update Loyalty clicked!");
    }

    // Triggered when user presses ENTER in the text field
    @FXML
    private void searchMemberID() {
        System.out.println("ENTER pressed — searching for: " + searchMemberID.getText());
    }
}

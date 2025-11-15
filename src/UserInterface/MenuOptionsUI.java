package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MenuOptionsUI {

    @FXML
    private Button searchMenuButton;

    @FXML
    private Button addMenuButton;

    @FXML
    private Button removeMenuButton;

    @FXML
    private Button updateMenuButton;

    @FXML
    private TextField searchMenu;

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
    private void searchMenuButtonClick() {
        System.out.println("Search Menu clicked!");
        System.out.println("Search menuID = " + searchMenu.getText());
    }

    @FXML
    private void addMenuButtonClick() {
        System.out.println("Add Menu clicked!");
    }

    @FXML
    private void removeMenuButtonClick() {
        System.out.println("Remove Menu clicked!");
    }

    @FXML
    private void updateMenuButtonClick() {
        System.out.println("Update Menu clicked!");
    }

    // Triggered when ENTER is pressed in the search field
    @FXML
    private void searchMenuEntries() {
        System.out.println("ENTER search menuID: " + searchMenu.getText());
    }
}

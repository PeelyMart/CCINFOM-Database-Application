package UserInterface;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TableOptionsUI {

    @FXML
    private Button searchButton;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Object> tableView; // Fully type-safe with Object

    // ----- BUTTON HANDLERS -----
    @FXML
    private void searchButtonClick() {
        System.out.println("Search clicked! Term: " + searchField.getText());
        // Implement search logic for tableView
    }

    @FXML
    private void addButtonClick() {
        System.out.println("Add clicked!");
        // Implement add logic
    }

    @FXML
    private void removeButtonClick() {
        System.out.println("Remove clicked!");
        // Implement remove logic
    }

    @FXML
    private void updateButtonClick() {
        System.out.println("Update clicked!");
        // Implement update logic
    }

    @FXML
    private void searchFieldEnter() {
        System.out.println("ENTER pressed in search: " + searchField.getText());
        // Trigger search
    }

    @FXML
    private void goBack() {
        SceneNavigator.switchScene(backButton, "/Resources/MainMenu/dashboard.fxml");
    }

    // ----- DYNAMIC TABLE SETUP -----
    public void setTableData(TableView<?> table) {
        // Copy columns
        ObservableList<TableColumn<Object, ?>> columns = tableView.getColumns();
        columns.clear();
        for (TableColumn<?, ?> col : table.getColumns()) {
            columns.add((TableColumn<Object, ?>) col);
        }

        // Copy items
        tableView.setItems((ObservableList<Object>) table.getItems());
    }
}

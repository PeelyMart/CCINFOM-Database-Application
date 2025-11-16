package UserInterface;

import Controller.TableActions;
import DAO.TableDAO;
import Model.Table;
import Model.Staff;
import Controller.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TransactionsUI {

    @FXML
    private FlowPane tableContainer;

    private int currentStaffId;

    @FXML
    public void initialize() {
        Staff currentUser = UserService.getCurrentUser();
        if (currentUser != null) {
            currentStaffId = currentUser.getStaffId();
        } else {
            System.err.println("No user logged in!");
            return;
        }

        loadTablesFromDB();
    }

    private void loadTablesFromDB() {
        ArrayList<Table> tables = new ArrayList<>(TableDAO.getAllTables());
        tableContainer.getChildren().clear();

        for (Table t : tables) {
            Button tableButton = new Button("Table " + t.getTableId());
            tableButton.setPrefSize(62, 62);

            if (t.getTableStatus()) {
                tableButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            } else {
                tableButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
            }

            tableButton.setOnAction(e -> handleTableClick(t));
            tableContainer.getChildren().add(tableButton);
        }
    }

    private void handleTableClick(Table table) {

        // If table NOT available → block
        if (!table.getTableStatus()) {
            SceneNavigator.showInfo("Table " + table.getTableId() + " is already occupied!");
            return;
        }

        // Take the table
        TableActions.initateTable(table, currentStaffId);

        // Refresh UI
        loadTablesFromDB();

        // Go to transaction menu
        Stage stage = (Stage) tableContainer.getScene().getWindow(); // or any node
        SceneNavigator.switchNoButton(stage, "/Resources/Transactions/transactionMenu.fxml", table);


    }
}

package UserInterface;

import Controller.TableActions;
import DAO.OrderDB;
import DAO.TableDAO;
import Model.Order;
import Model.OrderStatus;
import Model.Table;
import Model.Staff;
import Controller.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TransactionsUI {

    @FXML
    private FlowPane tableContainer;
    
    private static final int COLUMNS = 5;

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
        
        // Sort tables by ID to ensure proper ordering (1, 2, 3, 4, 5, 6, 7, etc.)
        Collections.sort(tables, Comparator.comparingInt(Table::getTableId));
        
        tableContainer.getChildren().clear();
        // Force wrap at exactly 5 columns: 5 buttons * 60px + 4 gaps * 30px = 300 + 120 = 420px
        // Set both prefWidth and maxWidth to force wrapping at 5 columns
        double wrapWidth = COLUMNS * 60 + (COLUMNS - 1) * 30; // 420px for exactly 5 columns with 30px gaps
        tableContainer.setPrefWidth(wrapWidth);
        tableContainer.setMaxWidth(wrapWidth);
        
        // Debug: Print table info
        System.out.println("=== Loading Tables ===");
        for (Table t : tables) {
            Order activeOrder = OrderDB.getWholeOrderByTable(t.getTableId());
            System.out.println("Table " + t.getTableId() + " - Status: " + t.getTableStatus());
            if (activeOrder != null) {
                System.out.println("  Order ID: " + activeOrder.getOrderId() + ", Status: " + activeOrder.getStatus());
                if (activeOrder.getOrderItems() != null) {
                    long activeItems = activeOrder.getOrderItems().stream()
                        .filter(item -> item.getStatus() != null && item.getStatus())
                        .count();
                    System.out.println("  Active Items: " + activeItems);
                }
            } else {
                System.out.println("  No order found");
            }
        }

        for (Table t : tables) {
            Button tableButton = new Button(String.valueOf(t.getTableId()));
            tableButton.setPrefSize(60, 60);
            tableButton.setMinSize(60, 60);
            tableButton.setMaxSize(60, 60);
            tableButton.setFont(javafx.scene.text.Font.font(16));

            // Check if table has an active order (OPEN status AND has active order items)
            Order activeOrder = OrderDB.getWholeOrderByTable(t.getTableId());
            boolean hasActiveOrder = false;
            if (activeOrder != null && activeOrder.getStatus() == OrderStatus.OPEN) {
                // Also check if it has active order items
                if (activeOrder.getOrderItems() != null) {
                    hasActiveOrder = activeOrder.getOrderItems().stream()
                        .anyMatch(item -> item.getStatus() != null && item.getStatus());
                }
            }
            
            // Red = has active order, Green = no active order (available)
            if (hasActiveOrder) {
                // Red = has active order
                tableButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-background-radius: 50; -fx-border-radius: 50; -fx-font-weight: bold;");
            } else {
                // Green = no active order (available)
                tableButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 50; -fx-border-radius: 50;");
            }

            tableButton.setOnAction(e -> handleTableClick(t));
            tableContainer.getChildren().add(tableButton);
        }
    }

    private void handleTableClick(Table table) {
        Stage stage = (Stage) tableContainer.getScene().getWindow(); // get current stage

        if (!table.getTableStatus()) { // table occupied
            Order order = OrderDB.getWholeOrderByTable(table.getTableId());
            if (order != null) {
                SceneNavigator.switchNoButton(stage, "/Resources/Transactions/transactionMenu.fxml", order);
            } else {
                SceneNavigator.showInfo("No order found for Table " + table.getTableId());
            }
            return;
        }

        // table available → take it
        TableActions.initateTable(table, currentStaffId);
        loadTablesFromDB();

        // pass table to TransactionMenuUI
        SceneNavigator.switchNoButton(stage, "/Resources/Transactions/transactionMenu.fxml", table);
    }

}

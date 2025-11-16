package UserInterface;

import DAO.MenuItemDAO;
import DAO.OrderDB;
import Model.MenuItem;
import Model.Order;
import Model.OrderItem;
import Model.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class TransactionMenuUI {

    // Buttons
    @FXML private Button ordersButton, reservationsButton, takenButton, availableButton, backButton;

    // Orders Table
    @FXML private TableView<OrderDisplay> ordersTable;
    @FXML private TableColumn<OrderDisplay, String> menuItemColumn;
    @FXML private TableColumn<OrderDisplay, String> quantityColumn;

    // Reservations Table
    @FXML private TableView<ReservationDisplay> reservationsTable;
    @FXML private TableColumn<ReservationDisplay, String> reservationDateColumn;
    @FXML private TableColumn<ReservationDisplay, String> reservationQtyColumn;

    // ObservableLists for tables
    private final ObservableList<OrderDisplay> ordersList = FXCollections.observableArrayList();
    private final ObservableList<ReservationDisplay> reservationsList = FXCollections.observableArrayList();
    
    private MenuItemDAO menuItemDAO = new MenuItemDAO();
    private Order currentOrder;

    @FXML
    private void initialize() {
        setupTables();
        loadReservations();

        // Button actions
        ordersButton.setOnAction(e -> {
            // Navigate to orders screen with current order (if available)
            if (currentOrder != null) {
                Stage stage = (Stage) ordersButton.getScene().getWindow();
                SceneNavigator.switchNoButton(stage, "/Resources/Transactions/orders.fxml", currentOrder);
            } else {
                // Navigate to orders screen without order data
                SceneNavigator.switchScene(ordersButton, "/Resources/Transactions/orders.fxml");
            }
        });
        reservationsButton.setOnAction(e -> {
            // Navigate to reservations screen
            SceneNavigator.switchScene(reservationsButton, "/Resources/Transactions/reservations.fxml");
        });
        takenButton.setOnAction(e -> filterTables(true));
        availableButton.setOnAction(e -> filterTables(false));
        backButton.setOnAction(e -> SceneNavigator.switchScene(backButton, "/Resources/MainMenu/dashboard.fxml"));

        // Example: dynamic language change
        setLanguage("EN"); // or "ES", "FR", etc.
        
        // If there's already an order set, load it
        if (currentOrder != null) {
            loadCurrentTableOrder();
        }
    }

    private void setupTables() {
        menuItemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        ordersTable.setItems(ordersList);

        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        reservationQtyColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        reservationsTable.setItems(reservationsList);
    }

    /**
     * Called by SceneNavigator when navigating from table selection
     */
    public void setData(Object data) {
        System.out.println("TransactionMenuUI.setData() called with: " + (data != null ? data.getClass().getName() : "null"));
        
        if (data instanceof Order) {
            currentOrder = (Order) data;
            System.out.println("Order received - Order ID: " + currentOrder.getOrderId() + ", Table ID: " + currentOrder.getTableId());
            loadCurrentTableOrder();
        } else if (data instanceof Table) {
            Table table = (Table) data;
            System.out.println("Table received - Table ID: " + table.getTableId());
            // Fetch order for this table
            Order order = OrderDB.getWholeOrderByTable(table.getTableId());
            if (order != null) {
                currentOrder = order;
                loadCurrentTableOrder();
            } else {
                System.out.println("No order found for table " + table.getTableId());
                ordersList.clear();
            }
        }
    }
    
    private void loadCurrentTableOrder() {
        ordersList.clear();
        
        if (currentOrder == null || currentOrder.getOrderItems() == null || currentOrder.getOrderItems().isEmpty()) {
            System.out.println("No order items to display");
            return;
        }
        
        System.out.println("Loading order items for Order ID: " + currentOrder.getOrderId());
        
        for (OrderItem item : currentOrder.getOrderItems()) {
            // Get menu item name instead of just ID
            MenuItem menuItem = menuItemDAO.getMenuItemById(item.getMenuId());
            String itemName;
            if (menuItem != null) {
                itemName = menuItem.getMenuName();
            } else {
                itemName = "Menu ID: " + item.getMenuId(); // Fallback if menu item not found
            }
            
            ordersList.add(new OrderDisplay(itemName, String.valueOf(item.getQuantity())));
            System.out.println("Added: " + itemName + " x" + item.getQuantity());
        }
        
        System.out.println("Loaded " + ordersList.size() + " order items");
    }

    private void loadOrders() {
        ordersList.clear();
        List<Order> allOrders = OrderDB.getOrdersByStaffId(1); // TODO: Replace with currentStaffId

        for (Order o : allOrders) {
            if (o.getOrderItems() != null) {
                for (OrderItem item : o.getOrderItems()) {
                    // Get menu item name instead of just ID
                    MenuItem menuItem = menuItemDAO.getMenuItemById(item.getMenuId());
                    String itemName;
                    if (menuItem != null) {
                        itemName = menuItem.getMenuName();
                    } else {
                        itemName = "Menu ID: " + item.getMenuId();
                    }
                    ordersList.add(new OrderDisplay(itemName, String.valueOf(item.getQuantity())));
                }
            }
        }
    }

    private void loadReservations() {
        reservationsList.clear();
        // TODO: Replace with real DAO
        reservationsList.add(new ReservationDisplay("2025-11-16", "3"));
        reservationsList.add(new ReservationDisplay("2025-11-17", "2"));
    }

    private void filterTables(boolean showTaken) {
        SceneNavigator.testClick(showTaken ? "Showing TAKEN tables" : "Showing AVAILABLE tables");
    }

    /** Change UI language dynamically (buttons for now) */
    private void setLanguage(String lang) {
        switch (lang) {
            case "ES": // Spanish
                ordersButton.setText("PEDIDOS");
                reservationsButton.setText("RESERVAS");
                backButton.setText("ATRÁS");
                takenButton.setText("OCUPADAS");
                availableButton.setText("DISPONIBLES");
                break;
            case "FR": // French
                ordersButton.setText("COMMANDES");
                reservationsButton.setText("RÉSERVATIONS");
                backButton.setText("RETOUR");
                takenButton.setText("PRISES");
                availableButton.setText("DISPONIBLES");
                break;
            default: // English
                ordersButton.setText("Orders");
                reservationsButton.setText("Reservations");
                backButton.setText("← BACK");
                takenButton.setText("TAKEN");
                availableButton.setText("AVAILABLE");
        }
    }

    // TableView models
    public static class OrderDisplay {
        private final String itemName;
        private final String quantity;

        public OrderDisplay(String itemName, String quantity) {
            this.itemName = itemName;
            this.quantity = quantity;
        }

        public String getItemName() { return itemName; }
        public String getQuantity() { return quantity; }
    }

    public static class ReservationDisplay {
        private final String date;
        private final String quantity;

        public ReservationDisplay(String date, String quantity) {
            this.date = date;
            this.quantity = quantity;
        }

        public String getDate() { return date; }
        public String getQuantity() { return quantity; }
    }
}

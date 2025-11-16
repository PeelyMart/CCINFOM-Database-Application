package UserInterface;

import DAO.MenuItemDAO;
import DAO.OrderDB;
import DAO.OrderitemDAO;
import Model.MenuItem;
import Model.Order;
import Model.OrderItem;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdersUI {

    @FXML
    private Button addButton, searchButton, deleteButton, editButton, payButton, backButton;

    @FXML
    private TextField searchOrder;

    @FXML
    private TableView<OrderItemDisplay> orderItemsTable;

    @FXML
    private TableColumn<OrderItemDisplay, String> menuItemColumn;

    @FXML
    private TableColumn<OrderItemDisplay, String> quantityColumn;

    @FXML
    private TableColumn<OrderItemDisplay, String> activeColumn;

    private MenuItemDAO menuItemDAO = new MenuItemDAO();
    private OrderitemDAO orderitemDAO = new OrderitemDAO();
    private Order currentOrder; // Store the current order

    @FXML
    private void initialize() {
        System.out.println("OrdersUI.initialize() called");
        System.out.println("orderItemsTable is null: " + (orderItemsTable == null));
        System.out.println("menuItemColumn is null: " + (menuItemColumn == null));
        System.out.println("quantityColumn is null: " + (quantityColumn == null));
        System.out.println("activeColumn is null: " + (activeColumn == null));
        
        // Setup TableView columns FIRST - this is critical
        setupTableViewColumns();

        // Button actions
        if (addButton != null) {
            addButton.setOnAction(e -> handleAdd());
        }
        if (searchButton != null) {
            searchButton.setOnAction(e -> handleSearch());
        }
        if (deleteButton != null) {
            deleteButton.setOnAction(e -> handleDelete());
        }
        if (editButton != null) {
            editButton.setOnAction(e -> handleEdit());
        }

        // PAY button -> open payment UI
        if (payButton != null) {
            payButton.setOnAction(e -> openPaymentChoice());
        }

        // BACK button -> go back to Transaction Menu
        if (backButton != null) {
            backButton.setOnAction(e ->
                    SceneNavigator.switchScene(backButton, "/Resources/Transactions/transactionMenu.fxml"));
        }

        // If there's an order already set, load it now
        if (currentOrder != null) {
            System.out.println("Loading currentOrder in initialize()");
            loadOrderItems(currentOrder);
        }
    }

    private void setupTableViewColumns() {
        System.out.println("setupTableViewColumns() called");
        if (menuItemColumn != null) {
            menuItemColumn.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));
            System.out.println("menuItemColumn cellValueFactory set");
        } else {
            System.out.println("ERROR: menuItemColumn is null!");
        }
        if (quantityColumn != null) {
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            System.out.println("quantityColumn cellValueFactory set");
        } else {
            System.out.println("ERROR: quantityColumn is null!");
        }
        if (activeColumn != null) {
            activeColumn.setCellValueFactory(new PropertyValueFactory<>("active"));
            System.out.println("activeColumn cellValueFactory set");
        } else {
            System.out.println("ERROR: activeColumn is null!");
        }
        
        // Verify columns are added to TableView
        if (orderItemsTable != null) {
            System.out.println("TableView has " + orderItemsTable.getColumns().size() + " columns");
        }
    }

    /**
     * Called by SceneNavigator to set order data when navigating from table selection
     */
    public void setData(Object data) {
        System.out.println("OrdersUI.setData() called with: " + (data != null ? data.getClass().getName() : "null"));
        if (data instanceof Order) {
            Order order = (Order) data;
            currentOrder = order;
            System.out.println("Order received - Order ID: " + order.getOrderId() + ", Table ID: " + order.getTableId());
            System.out.println("Order items count: " + (order.getOrderItems() != null ? order.getOrderItems().size() : 0));
            
            // If TableView columns are set up, load immediately
            if (orderItemsTable != null) {
                setupTableViewColumns(); // Ensure columns are set up
                loadOrderItems(order);
            }
        } else {
            System.out.println("Data is not an Order instance: " + (data != null ? data.getClass().getName() : "null"));
        }
    }

    private void loadOrderItems(Order order) {
        System.out.println("========== loadOrderItems() called ==========");
        if (order == null) {
            System.out.println("ERROR: Order is null!");
            if (orderItemsTable != null) {
                orderItemsTable.setItems(FXCollections.observableArrayList());
            }
            return;
        }

        System.out.println("Order ID: " + order.getOrderId() + ", Table ID: " + order.getTableId());
        System.out.println("Order items list is null: " + (order.getOrderItems() == null));
        
        if (order.getOrderItems() == null) {
            System.out.println("ERROR: order.getOrderItems() returned null!");
            if (orderItemsTable != null) {
                orderItemsTable.setItems(FXCollections.observableArrayList());
            }
            return;
        }
        
        if (order.getOrderItems().isEmpty()) {
            System.out.println("WARNING: Order has no items (empty list)!");
            if (orderItemsTable != null) {
                orderItemsTable.setItems(FXCollections.observableArrayList());
            }
            return;
        }

        System.out.println("Loading " + order.getOrderItems().size() + " order items");
        List<OrderItemDisplay> displayItems = new ArrayList<>();
        
        for (OrderItem item : order.getOrderItems()) {
            System.out.println("Processing OrderItem - MenuID: " + item.getMenuId() + ", Qty: " + item.getQuantity() + ", Status: " + item.getStatus());
            
            MenuItem menuItem = menuItemDAO.getMenuItemById(item.getMenuId());
            String menuName;
            if (menuItem != null) {
                menuName = menuItem.getMenuName();
                System.out.println("Found menu item: " + menuName);
            } else {
                menuName = "Unknown (ID: " + item.getMenuId() + ")";
                System.out.println("WARNING: Menu item not found for menu_id: " + item.getMenuId());
            }
            
            String quantity = String.valueOf(item.getQuantity());
            
            // Get status string - show as "active" or "completed"
            String active;
            if (item.getStatus() != null && item.getStatus()) {
                active = "active";
            } else {
                active = "completed";
            }

            OrderItemDisplay displayItem = new OrderItemDisplay(menuName, quantity, active, item);
            displayItems.add(displayItem);
            System.out.println("Added display item: " + menuName + " x" + quantity + " (" + active + ")");
        }

        System.out.println("Total display items created: " + displayItems.size());

        if (orderItemsTable != null) {
            System.out.println("Setting items on TableView...");
            // Use Platform.runLater to ensure UI updates on JavaFX thread
            Platform.runLater(() -> {
                try {
                    orderItemsTable.setItems(FXCollections.observableArrayList(displayItems));
                    System.out.println("✓ TableView updated with " + displayItems.size() + " items");
                    System.out.println("✓ TableView.items.size() = " + orderItemsTable.getItems().size());
                    
                    // Force refresh
                    orderItemsTable.refresh();
                    
                    // Verify columns
                    System.out.println("✓ TableView has " + orderItemsTable.getColumns().size() + " columns");
                    if (orderItemsTable.getColumns().size() > 0) {
                        System.out.println("✓ First column: " + orderItemsTable.getColumns().get(0).getText());
                    }
                    
                    // Check if items are visible
                    if (orderItemsTable.getItems().size() > 0) {
                        System.out.println("✓ First item: " + orderItemsTable.getItems().get(0).getMenuItemName());
                    }
                } catch (Exception e) {
                    System.err.println("ERROR setting TableView items: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        } else {
            System.out.println("ERROR: orderItemsTable is null!");
        }
        System.out.println("========== loadOrderItems() finished ==========");
    }

    // Match the TextField onAction in FXML
    @FXML
    private void searchStaffEntries() {
        handleSearch();
    }
    
    // ===================== ADD =====================
    private void handleAdd() {
        if (currentOrder == null) {
            SceneNavigator.showError("No order selected. Please search for an order first.");
            return;
        }
        
        // Get all menu items for dropdown
        ArrayList<MenuItem> allMenuItems = menuItemDAO.getAllMenuItems();
        if (allMenuItems == null || allMenuItems.isEmpty()) {
            SceneNavigator.showError("No menu items available.");
            return;
        }
        
        // Create list of menu item display strings (name + price)
        List<String> menuItemOptions = new ArrayList<>();
        for (MenuItem item : allMenuItems) {
            if (item.getStatus() != null && item.getStatus()) { // Only show available items
                menuItemOptions.add(item.getMenuName() + " - $" + String.format("%.2f", item.getPrice()));
            }
        }
        
        if (menuItemOptions.isEmpty()) {
            SceneNavigator.showError("No available menu items.");
            return;
        }
        
        // Show dropdown for menu selection
        ChoiceDialog<String> menuDialog = new ChoiceDialog<>(menuItemOptions.get(0), menuItemOptions);
        menuDialog.setTitle("Add Order Item");
        menuDialog.setHeaderText("Select Menu Item:");
        menuDialog.setContentText("Choose a menu item:");
        Optional<String> menuResult = menuDialog.showAndWait();
        
        menuResult.ifPresent(selectedMenu -> {
            // Extract menu name from selection (format: "Name - $price")
            String menuName = selectedMenu.split(" - ")[0];
            
            // Find the menu item
            MenuItem selectedMenuItem = null;
            for (MenuItem item : allMenuItems) {
                if (item.getMenuName().equals(menuName)) {
                    selectedMenuItem = item;
                    break;
                }
            }
            
            if (selectedMenuItem == null) {
                SceneNavigator.showError("Menu item not found.");
                return;
            }
            
            // Store in final variable for use in nested lambda
            final MenuItem finalSelectedMenuItem = selectedMenuItem;
            
            // Get quantity
            TextInputDialog qtyDialog = new TextInputDialog("1");
            qtyDialog.setTitle("Add Order Item");
            qtyDialog.setHeaderText("Enter quantity:");
            Optional<String> qtyInput = qtyDialog.showAndWait();
            
            qtyInput.ifPresent(qtyStr -> {
                int quantity;
                try {
                    quantity = Integer.parseInt(qtyStr);
                    if (quantity <= 0) {
                        SceneNavigator.showError("Quantity must be greater than 0.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    SceneNavigator.showError("Quantity must be a number.");
                    return;
                }
                
                // Calculate subtotal
                BigDecimal subtotal = BigDecimal.valueOf(finalSelectedMenuItem.getPrice()).multiply(BigDecimal.valueOf(quantity));
                
                // Create order item
                OrderItem newItem = new OrderItem();
                newItem.setOrderId(currentOrder.getOrderId());
                newItem.setMenuId(finalSelectedMenuItem.getMenuId());
                newItem.setQuantity(quantity);
                newItem.setSubtotal(subtotal);
                newItem.setStatus(true); // Active by default
                
                // Add to database
                boolean success = orderitemDAO.addOrderItem(newItem);
                if (success) {
                    SceneNavigator.showInfo("Order item added successfully!");
                    // Reload order from database
                    refreshCurrentOrder();
                } else {
                    SceneNavigator.showError("Failed to add order item. Please try again.");
                }
            });
        });
    }
    
    // ===================== SEARCH =====================
    private void handleSearch() {
        String input = searchOrder.getText().trim();
        if (input.isEmpty()) {
            SceneNavigator.showError("Please enter an Order ID or Menu Item name to search.");
            return;
        }
        
        // Try to parse as Order ID first
        try {
            int orderId = Integer.parseInt(input);
            Order order = OrderDB.getWholeOrder(orderId);
            if (order != null) {
                currentOrder = order;
                loadOrderItems(order);
                SceneNavigator.showInfo("Order " + orderId + " loaded successfully!");
                return;
            } else {
                SceneNavigator.showError("No order found with ID: " + orderId);
                currentOrder = null;
                orderItemsTable.setItems(FXCollections.observableArrayList());
                return;
            }
        } catch (NumberFormatException e) {
            // Not a number, search by menu item name
        }
        
        // Search by menu item name - find orders containing this menu item
        ArrayList<MenuItem> matchingMenuItems = new ArrayList<>();
        ArrayList<MenuItem> allMenuItems = menuItemDAO.getAllMenuItems();
        
        if (allMenuItems != null) {
            String searchLower = input.toLowerCase();
            for (MenuItem item : allMenuItems) {
                if (item.getMenuName().toLowerCase().contains(searchLower)) {
                    matchingMenuItems.add(item);
                }
            }
        }
        
        if (matchingMenuItems.isEmpty()) {
            SceneNavigator.showError("No menu items found matching: " + input);
            return;
        }
        
        // If multiple matches, let user choose
        if (matchingMenuItems.size() > 1) {
            List<String> menuNames = new ArrayList<>();
            for (MenuItem item : matchingMenuItems) {
                menuNames.add(item.getMenuName());
            }
            
            ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(menuNames.get(0), menuNames);
            choiceDialog.setTitle("Multiple Matches");
            choiceDialog.setHeaderText("Multiple menu items found:");
            choiceDialog.setContentText("Select the menu item:");
            Optional<String> result = choiceDialog.showAndWait();
            
            if (!result.isPresent()) {
                return;
            }
            
            // Find the selected menu item
            MenuItem selectedMenuItem = null;
            for (MenuItem item : matchingMenuItems) {
                if (item.getMenuName().equals(result.get())) {
                    selectedMenuItem = item;
                    break;
                }
            }
            
            if (selectedMenuItem != null) {
                searchOrdersByMenuItem(selectedMenuItem);
            }
        } else {
            // Single match
            searchOrdersByMenuItem(matchingMenuItems.get(0));
        }
    }
    
    private void searchOrdersByMenuItem(MenuItem menuItem) {
        // Get all order items with this menu item
        List<OrderItem> allOrderItems = OrderitemDAO.getAllOrderItems();
        List<Integer> orderIds = new ArrayList<>();
        
        for (OrderItem item : allOrderItems) {
            if (item.getMenuId() == menuItem.getMenuId() && !orderIds.contains(item.getOrderId())) {
                orderIds.add(item.getOrderId());
            }
        }
        
        if (orderIds.isEmpty()) {
            SceneNavigator.showError("No orders found containing: " + menuItem.getMenuName());
            return;
        }
        
        // If multiple orders, let user choose
        if (orderIds.size() > 1) {
            List<String> orderIdStrings = new ArrayList<>();
            for (Integer id : orderIds) {
                orderIdStrings.add("Order ID: " + id);
            }
            
            ChoiceDialog<String> choiceDialog = new ChoiceDialog<>(orderIdStrings.get(0), orderIdStrings);
            choiceDialog.setTitle("Multiple Orders");
            choiceDialog.setHeaderText("Multiple orders found with " + menuItem.getMenuName() + ":");
            choiceDialog.setContentText("Select an order:");
            Optional<String> result = choiceDialog.showAndWait();
            
            if (result.isPresent()) {
                String selected = result.get();
                int orderId = Integer.parseInt(selected.replace("Order ID: ", ""));
                Order order = OrderDB.getWholeOrder(orderId);
                if (order != null) {
                    currentOrder = order;
                    loadOrderItems(order);
                    SceneNavigator.showInfo("Order " + orderId + " loaded successfully!");
                }
            }
        } else {
            // Single order found
            Order order = OrderDB.getWholeOrder(orderIds.get(0));
            if (order != null) {
                currentOrder = order;
                loadOrderItems(order);
                SceneNavigator.showInfo("Order " + orderIds.get(0) + " loaded successfully!");
            }
        }
    }
    
    // ===================== DELETE =====================
    private void handleDelete() {
        OrderItemDisplay selected = orderItemsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            SceneNavigator.showError("Please select an order item to delete.");
            return;
        }
        
        OrderItem itemToDelete = selected.getOrderItem();
        if (itemToDelete == null || itemToDelete.getOrderItemId() <= 0) {
            SceneNavigator.showError("Could not find order item to delete.");
            return;
        }
        
        int currentQuantity = itemToDelete.getQuantity();
        
        // Ask for quantity to delete
        TextInputDialog qtyDialog = new TextInputDialog(String.valueOf(currentQuantity));
        qtyDialog.setTitle("Delete Order Item");
        qtyDialog.setHeaderText("Delete quantity for: " + selected.getMenuItemName());
        qtyDialog.setContentText("Current quantity: " + currentQuantity + "\nEnter quantity to delete:");
        Optional<String> qtyInput = qtyDialog.showAndWait();
        
        qtyInput.ifPresent(qtyStr -> {
            int deleteQuantity;
            try {
                deleteQuantity = Integer.parseInt(qtyStr);
                if (deleteQuantity <= 0) {
                    SceneNavigator.showError("Quantity must be greater than 0.");
                    return;
                }
                if (deleteQuantity > currentQuantity) {
                    SceneNavigator.showError("Cannot delete more than current quantity (" + currentQuantity + ").");
                    return;
                }
            } catch (NumberFormatException e) {
                SceneNavigator.showError("Quantity must be a number.");
                return;
            }
            
            boolean success = false;
            
            if (deleteQuantity == currentQuantity) {
                // Delete entire item
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Delete Order Item");
                confirm.setHeaderText("Delete entire order item?");
                confirm.setContentText("Are you sure you want to delete all " + currentQuantity + " of " + selected.getMenuItemName() + "?");
                Optional<ButtonType> result = confirm.showAndWait();
                
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    success = orderitemDAO.deleteOrderItem(itemToDelete.getOrderItemId());
                } else {
                    return; // User cancelled
                }
            } else {
                // Partial deletion - update quantity
                int newQuantity = currentQuantity - deleteQuantity;
                
                // Get menu item to recalculate subtotal
                MenuItem menuItem = menuItemDAO.getMenuItemById(itemToDelete.getMenuId());
                if (menuItem == null) {
                    SceneNavigator.showError("Menu item not found.");
                    return;
                }
                
                BigDecimal newSubtotal = BigDecimal.valueOf(menuItem.getPrice()).multiply(BigDecimal.valueOf(newQuantity));
                
                // Update order item with new quantity
                itemToDelete.setQuantity(newQuantity);
                itemToDelete.setSubtotal(newSubtotal);
                success = orderitemDAO.updateOrderItem(itemToDelete);
            }
            
            if (success) {
                SceneNavigator.showInfo("Order item updated successfully!");
                refreshCurrentOrder();
            } else {
                SceneNavigator.showError("Failed to update order item.");
            }
        });
    }
    
    // ===================== EDIT =====================
    private void handleEdit() {
        OrderItemDisplay selected = orderItemsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            SceneNavigator.showError("Please select an order item to edit.");
            return;
        }
        
        OrderItem itemToEdit = selected.getOrderItem();
        if (itemToEdit == null || itemToEdit.getOrderItemId() <= 0) {
            SceneNavigator.showError("Could not find order item to edit.");
            return;
        }
        
        // Show dialog to toggle between active/completed
        List<String> statusOptions = new ArrayList<>();
        statusOptions.add("active");
        statusOptions.add("completed");
        
        String currentStatus = itemToEdit.getStatus() != null && itemToEdit.getStatus() ? "active" : "completed";
        
        ChoiceDialog<String> statusDialog = new ChoiceDialog<>(currentStatus, statusOptions);
        statusDialog.setTitle("Edit Order Item Status");
        statusDialog.setHeaderText("Change status for: " + selected.getMenuItemName() + " (Qty: " + itemToEdit.getQuantity() + ")");
        statusDialog.setContentText("Select status:");
        Optional<String> statusResult = statusDialog.showAndWait();
        
        statusResult.ifPresent(newStatus -> {
            boolean isActive = newStatus.equals("active");
            
            // Update status
            itemToEdit.setStatus(isActive);
            
            // Update in database
            boolean success = orderitemDAO.updateOrderItem(itemToEdit);
            if (success) {
                SceneNavigator.showInfo("Order item status updated to " + newStatus + "!");
                refreshCurrentOrder();
            } else {
                SceneNavigator.showError("Failed to update order item status.");
            }
        });
    }
    
    private void refreshCurrentOrder() {
        if (currentOrder != null) {
            // Reload order from database
            Order refreshedOrder = OrderDB.getWholeOrder(currentOrder.getOrderId());
            if (refreshedOrder != null) {
                currentOrder = refreshedOrder;
                loadOrderItems(refreshedOrder);
            }
        }
    }

    private void openPaymentChoice() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Payment Type");
            alert.setHeaderText("Are you a loyalty member?");
            alert.setContentText("Choose your payment type:");

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);

            ButtonType result = alert.showAndWait().orElse(noButton);

            String fxml = (result == yesButton) ?
                    "/Resources/Transactions/paymentLM.fxml" :
                    "/Resources/Transactions/paymentNormal.fxml";

            SceneNavigator.switchScene(payButton, fxml);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Simple display model for order items in the TableView
     */
    public static class OrderItemDisplay {
        private final SimpleStringProperty menuItemName;
        private final SimpleStringProperty quantity;
        private final SimpleStringProperty active;
        private final OrderItem orderItem; // Store reference to original OrderItem

        public OrderItemDisplay(String menuItemName, String quantity, String active, OrderItem orderItem) {
            this.menuItemName = new SimpleStringProperty(menuItemName);
            this.quantity = new SimpleStringProperty(quantity);
            this.active = new SimpleStringProperty(active);
            this.orderItem = orderItem;
        }

        public String getMenuItemName() {
            return menuItemName.get();
        }

        public String getQuantity() {
            return quantity.get();
        }

        public String getActive() {
            return active.get();
        }
        
        public OrderItem getOrderItem() {
            return orderItem;
        }
    }
}

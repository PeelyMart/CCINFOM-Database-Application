package Model;

public class OrderItem {
    // Private attributes
    private int orderItemId;
    private int orderId;
    private int menuId;
    private int quantity;
    private double subtotal;
    private String status;

    // Constructors
    public OrderItem() {
        
    }

    public OrderItem(int orderItemId, int orderId, int menuId, int quantity, double subtotal, String status) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.menuId = menuId;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.status = status;
    }

    // Getters
    public int getOrderItemId() {
        return orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getMenuId() {
        return menuId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

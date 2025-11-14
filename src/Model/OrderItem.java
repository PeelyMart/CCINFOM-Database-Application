package Model;

import java.math.BigDecimal;

public class OrderItem {
    // Private attributes
    private int orderItemId;
    private int orderId;
    private int menuId;
    private int quantity;
    private BigDecimal subtotal;
    private boolean isActive;
    private String statusAsAtring;

    // Constructors
    public OrderItem() {
        
    }

    public OrderItem(int orderItemId, int orderId, int menuId, int quantity, BigDecimal subtotal, Boolean status) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.menuId = menuId;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.isActive= status;


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

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public Boolean getStatus() {
        return isActive;
    }

    public String getStatusAsAtring(){
        return statusAsAtring;
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

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public void setStatus(Boolean status) {
        this.isActive = status;
        statusAsAtring = isActive ? "active" : "inactive";
    }


}

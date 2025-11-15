package Model;

import javafx.scene.layout.BorderRepeat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Order {
    // Private attributes
    private int orderId;
    private int tableId;
    private int staffId;
    private LocalDateTime orderTime; 
    private BigDecimal totalCost;
    private OrderStatus status;
    private List<OrderItem> orderItems;
    private Payment.PaymentMethod paymentMethod;

    // Constructors
    public Order() {
        
    }

    public Order(int orderId, int tableId, int staffId, LocalDateTime orderTime, BigDecimal totalCost, OrderStatus status) {
        this.orderId = orderId;
        this.tableId = tableId;
        this.staffId = staffId;
        this.orderTime = orderTime;
        this.totalCost = totalCost;
        this.status = status;
    }

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public int getTableId() {
        return tableId;
    }

    public int getStaffId() {
        return staffId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public Payment.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    //Setters 

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setPaymentMethod(Payment.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}

package Model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    // Private attributes
    private int orderId;
    private int tableId;
    private int staffId;
    private LocalDateTime orderTime; 
    private double totalCost;          
    private String status;
    private List<OrderItem> orderItems;

    // Constructors
    public Order() {
        
    }

    public Order(int orderId, int tableId, int staffId, LocalDateTime orderTime, double totalCost,
                 String status, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.tableId = tableId;
        this.staffId = staffId;
        this.orderTime = orderTime;
        this.totalCost = totalCost;
        this.status = status;
        this.orderItems = orderItems;
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

    public double getTotalCost() {
        return totalCost;
    }

    public String getStatus() {
        return status;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
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

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}

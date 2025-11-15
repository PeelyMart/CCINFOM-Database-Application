package Model;

import java.time.LocalDateTime;

public class Payment {

    public enum PaymentMethod {
        CASH,
        DEBIT,
        CREDIT
    }

    // Private attributes
    private int transactionId;           
    private int orderId;
    private double amountPaid;
    private PaymentMethod paymentMethod;
    private LocalDateTime paymentDate;
    private int staffId;
    private Integer loyalCustomerId;     
    private String unknownCustomerName;  
    private boolean isActive;

    // Constructors
    public Payment() {
        
    }

    public Payment(int transactionId, int orderId, double amountPaid, PaymentMethod paymentMethod,
                   LocalDateTime paymentDate, int staffId, Integer loyalCustomerId,
                   String unknownCustomerName, boolean isActive) {
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.amountPaid = amountPaid;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.staffId = staffId;
        this.loyalCustomerId = loyalCustomerId;
        this.unknownCustomerName = unknownCustomerName;
        this.isActive = isActive;
    }

    // Getters 
    public int getTransactionId() {
        return transactionId;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public PaymentMethod getPaymentMethod () {
        return paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public int getStaffId() {
        return staffId;
    }

    public Integer getLoyalCustomerId() {
        return loyalCustomerId;
    }

    public String getUnknownCustomerName() {
        return unknownCustomerName;
    }

    public boolean isActive() {
        return isActive;
    }

    // Setters
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public void setLoyalCustomerId(Integer loyalCustomerId) {
        this.loyalCustomerId = loyalCustomerId;
    }

    public void setUnknownCustomerName(String unknownCustomerName) {
        this.unknownCustomerName = unknownCustomerName;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }
}
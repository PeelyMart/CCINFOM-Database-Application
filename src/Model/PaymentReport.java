package Model;

import java.math.BigDecimal;

public class PaymentReport {
    String paymentType;
    BigDecimal totalCollected;
    int numberOfTransactions;

    public PaymentReport(String type, BigDecimal totalCollected, int transactions){
        this.paymentType = type;
        this.totalCollected = totalCollected;
        this.numberOfTransactions = transactions;
    }

    public String getType(){
        return paymentType;
    }
    public BigDecimal getTotal(){
        return totalCollected;
    }
    public int getTransactions(){
        return numberOfTransactions;
    }
}

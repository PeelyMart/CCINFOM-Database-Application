package Controller;

import DAO.LoyaltymemberDAO;
import Model.Order;
import Model.Payment;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PaymentControl {
    /**
     * @param currentOrder
     * @return 1 - successful, 0 - unsuccessful, -1 - the bill was 0.00 early return
     */
    public static int initiatePayment(Order currentOrder, int customerId){
        if(currentOrder.getTotalCost().equals(BigDecimal.valueOf(0.00))) {return -1;}

        //creating the record for the payment
        Payment currPay = new Payment();
        //TODO: PaymentDAO.recordPayment();
        //reflection of points
        int points = calculateLoyaltyPoints(currentOrder.getTotalCost());
        LoyaltymemberDAO.addPoints(customerId, points);
        return 1;
    }

    public static int calculateLoyaltyPoints(BigDecimal totalSpent){
        if(totalSpent == null || totalSpent.compareTo(BigDecimal.ZERO) <= 0){
            return 0;
        }
        return totalSpent.divide(BigDecimal.valueOf(1000), 0, RoundingMode.DOWN).intValue();
    }

    public static void uploadPaymentInfo(){

    }



}

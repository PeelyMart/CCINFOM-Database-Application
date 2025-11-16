package Controller;

import DAO.LoyaltymemberDAO;
import DAO.PaymentDAO;
import Model.Order;
import Model.Payment;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class PaymentControl {

    public static int initiatePayment(Order currentOrder, int customerId){
        if(currentOrder.getTotalCost().equals(BigDecimal.valueOf(0.00))) {
            return -1;
        }

        //creating the record for the payment
        Payment currPay = new Payment();
        currPay.setOrderId(currentOrder.getOrderId());
        currPay.setAmountPaid(currentOrder.getTotalCost().doubleValue());
        currPay.setPaymentMethod(currentOrder.getPaymentMethod());
        currPay.setPaymentDate(LocalDateTime.now());
        currPay.setStaffId(currentOrder.getStaffId());
        currPay.setLoyalCustomerId(customerId);
        currPay.setActive(true);
        
        PaymentDAO paymentDAO = new PaymentDAO();
        boolean success = paymentDAO.recordPayment(currPay);

        if (!success) {
            return 0;
        }

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

}

package Controller;

import Model.Payment;
import DAO.PaymentDAO;

import java.time.LocalDateTime;
import java.util.Map;


public class PaymentTest {
    public static void main(String[] args) {
        SalesReportController.getTransactionReport(PaymentDAO.getPaymentReport());
    }
}

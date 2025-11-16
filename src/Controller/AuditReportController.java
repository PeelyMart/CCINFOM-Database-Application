package Controller;

import DAO.OrderDB;
import DAO.PaymentDAO;
import DAO.StaffDB;
import DAO.StaffTrackDAO;
import Model.Order;
import Model.Payment;
import Model.Staff;
import Model.StaffTracker;
import java.util.List;

public class AuditReportController {

    public void printAuditReportForStaff(int staffId) {

        // getting staff info
        Staff staff = StaffDB.findById(staffId);
        if (staff == null) {
            System.out.println("No staff member found with the ID: " + staffId);
            return;
        }

        // staff session (login / logout) details
        List<StaffTracker> sessions = StaffTrackDAO.getSessionsByStaffId(staffId);
        System.out.println("\nSessions of:"  + StaffDB.findById(staffId).getFirstName() + " " + StaffDB.findById(staffId).getLastName() );
        System.out.printf("%-10s %-20s %-20s %-10s%n",
                "StaffID", "Login", "Logout", "Minutes");

        for (StaffTracker tracker : sessions) {
            System.out.printf("%-10d %-20s %-20s %-10d%n",
                    tracker.getStaffId(),
                    tracker.getTimeIn(),
                    tracker.getTimeOut(),
                    tracker.getSessionMinutes());
        }

        // orders by staff
        List<Order> orders = OrderDB.getOrdersByStaffId(staffId);
        System.out.println("\nOrders processed by staff:");
        System.out.printf("%-10s %-20s %-10s%n", "Order ID", "Order Time", "Total");
        for (Order order : orders) {
            System.out.printf("%-10d %-20s %-10.2f%n",
                    order.getOrderId(), order.getOrderTime(),
                    order.getTotalCost() != null ? order.getTotalCost() : 0.0);
        }

        // payments by staff
        List<Payment> payments = PaymentDAO.getPaymentsByStaffId(staffId);
        System.out.println("\nPayments processed by staff:");
        System.out.printf("%-10s %-12s %-20s %-10s%n", "Pay ID", "Amount", "Date", "Method");
        for (Payment payment : payments) {
            System.out.printf("%-10d %-12.2f %-20s %-10s%n",
                    payment.getTransactionId(), payment.getAmountPaid(),
                    payment.getPaymentDate(), payment.getPaymentMethod());
        }
    }
}

package DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
/*
public class PaymentDAO {

    public boolean recordPayment(Payment payment) {
        String sql = "INSERT INTO payments (order_id, amount_paid, payment_method, payment_date, staff_id, loyal_customer_id, unknown_customer_name, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, payment.getOrderId());
            stmt.setDouble(2, payment.getAmountPaid());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setTimestamp(4, Timestamp.valueOf(payment.getPaymentDate()));
            stmt.setInt(5, payment.getStaffId());

            if (payment.getLoyalCustomerId() != null) {
                stmt.setInt(6, payment.getLoyalCustomerId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            if (payment.getUnknownCustomerName() != null) {
                stmt.setString(7, payment.getUnknownCustomerName());
            } else {
                stmt.setNull(7, Types.VARCHAR);
            }

            stmt.setBoolean(8, payment.isActive());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    payment.setTransactionId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Payment getPaymentById(int transactionId) {
        String sql = "SELECT * FROM payments WHERE transaction_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int orderId = rs.getInt("order_id");
                double amountPaid = rs.getDouble("amount_paid");
                String paymentMethod = rs.getString("payment_method");
                LocalDateTime paymentDate = rs.getTimestamp("payment_date").toLocalDateTime();
                int staffId = rs.getInt("staff_id");

                Integer loyalCustomerId = rs.getObject("loyal_customer_id") != null ? rs.getInt("loyal_customer_id") : null;
                String unknownCustomerName = rs.getString("unknown_customer_name");
                boolean isActive = rs.getBoolean("is_active");

                return new Payment(transactionId, orderId, amountPaid, paymentMethod, paymentDate,
                        staffId, loyalCustomerId, unknownCustomerName, isActive);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePayment(Payment payment) {
        String sql = "UPDATE payments SET order_id = ?, amount_paid = ?, payment_method = ?, payment_date = ?, staff_id = ?, loyal_customer_id = ?, unknown_customer_name = ?, is_active = ? WHERE transaction_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, payment.getOrderId());
            stmt.setDouble(2, payment.getAmountPaid());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setTimestamp(4, Timestamp.valueOf(payment.getPaymentDate()));
            stmt.setInt(5, payment.getStaffId());

            if (payment.getLoyalCustomerId() != null) {
                stmt.setInt(6, payment.getLoyalCustomerId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            if (payment.getUnknownCustomerName() != null) {
                stmt.setString(7, payment.getUnknownCustomerName());
            } else {
                stmt.setNull(7, Types.VARCHAR);
            }

            stmt.setBoolean(8, payment.isActive());
            stmt.setInt(9, payment.getTransactionId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePayment(int transactionId) {
        String sql = "DELETE FROM payments WHERE transaction_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Payment> getAllPayments() {
        ArrayList<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments ORDER BY transaction_id";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Payment p = new Payment();

                p.setTransactionId(rs.getInt("transaction_id"));
                p.setOrderId(rs.getInt("order_id"));
                p.setAmountPaid(rs.getDouble("amount_paid"));
                p.setPaymentMethod(rs.getString("payment_method"));
                p.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
                p.setStaffId(rs.getInt("staff_id"));

                Integer loyalCustomerId = rs.getObject("loyal_customer_id") != null ? rs.getInt("loyal_customer_id") : null;
                p.setLoyalCustomerId(loyalCustomerId);

                p.setUnknownCustomerName(rs.getString("unknown_customer_name"));
                p.setActive(rs.getBoolean("is_active"));

                payments.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public ArrayList<Payment> getPaymentsByOrderId(int orderId) {
        ArrayList<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE order_id = ? ORDER BY payment_date";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Payment p = new Payment();

                p.setTransactionId(rs.getInt("transaction_id"));
                p.setOrderId(rs.getInt("order_id"));
                p.setAmountPaid(rs.getDouble("amount_paid"));
                p.setPaymentMethod(rs.getString("payment_method"));
                p.setPaymentDate(rs.getTimestamp("payment_date").toLocalDateTime());
                p.setStaffId(rs.getInt("staff_id"));

                Integer loyalCustomerId = rs.getObject("loyal_customer_id") != null ? rs.getInt("loyal_customer_id") : null;
                p.setLoyalCustomerId(loyalCustomerId);

                p.setUnknownCustomerName(rs.getString("unknown_customer_name"));
                p.setActive(rs.getBoolean("is_active"));

                payments.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }
}
*/
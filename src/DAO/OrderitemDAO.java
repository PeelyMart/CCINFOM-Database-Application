package DAO;

import Model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class OrderitemDAO {

    /**
     * Inserts a NEW order item record and then writes it on the parameter
     * @param orderItem - stores the new order item here
     * @return if successful
     */

    public boolean addOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (order_id, menu_id, quantity, subtotal, is_active) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getMenuId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setBigDecimal(4, orderItem.getSubtotal());
            stmt.setBoolean(5, orderItem.getStatus());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    orderItem.setOrderItemId(keys.getInt(1)); 
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * finds order items based on the order header and returns a list
     * @param orderId
     * @return - an array of the order's items - empty if none
     */
    public static List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_item WHERE order_id = ? ORDER BY order_item_id";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem i = new OrderItem();
                i.setOrderItemId(rs.getInt("order_item_id"));
                i.setOrderId(rs.getInt("order_id"));
                i.setMenuId(rs.getInt("menu_id"));
                i.setQuantity(rs.getInt("quantity"));
                i.setSubtotal(rs.getBigDecimal("subtotal"));
                i.setStatus(rs.getBoolean("is_active"));
                orderItems.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItems; // Returns empty list if no rows found
    }


    /**
     * changes the order item
     * @param
     */
    public boolean updateOrderItem(OrderItem orderItem) {
        String sql = "UPDATE order_items SET order_id = ?, menu_id = ?, quantity = ?, subtotal = ?, status = ? WHERE order_item_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getMenuId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setBigDecimal(4, orderItem.getSubtotal());
            stmt.setBoolean(5, orderItem.getStatus());
            stmt.setInt(6, orderItem.getOrderItemId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteOrderItem(int orderItemId) {
        String sql = "DELETE FROM order_items WHERE order_item_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderItemId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /*
     *
     *
     *
     *
     */

    public static ArrayList<OrderItem> getAllOrderItems() {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_item ORDER BY order_item_id";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrderItem i = new OrderItem();
                i.setOrderItemId(rs.getInt("order_item_id"));
                i.setOrderId(rs.getInt("order_id"));
                i.setMenuId(rs.getInt("menu_id"));
                i.setQuantity(rs.getInt("quantity"));
                i.setSubtotal(rs.getBigDecimal("subtotal"));
                i.setStatus(rs.getBoolean("is_active"));
                orderItems.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }



}
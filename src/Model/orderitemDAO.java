import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class orderitemDAO {

    public boolean addOrderItem(OrderItem orderItem) {
        String sql = "INSERT INTO order_items (order_id, menu_id, quantity, subtotal, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getMenuId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setDouble(4, orderItem.getSubtotal());
            stmt.setString(5, orderItem.getStatus());

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

        public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        List<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ? ORDER BY order_item_id";
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
                    i.setSubtotal(rs.getDouble("subtotal"));
                    i.setStatus(rs.getString("status"));

                    orderItems.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    public boolean updateOrderItem(OrderItem orderItem) {
        String sql = "UPDATE order_items SET order_id = ?, menu_id = ?, quantity = ?, subtotal = ?, status = ? WHERE order_item_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getMenuId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setDouble(4, orderItem.getSubtotal());
            stmt.setString(5, orderItem.getStatus());
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

    public ArrayList<OrderItem> getAllOrderItems() {
        ArrayList<OrderItem> orderItems = new ArrayList<>();
        String sql = "SELECT * FROM order_items ORDER BY order_item_id";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrderItem i = new OrderItem();
                i.setOrderItemId(rs.getInt("order_item_id"));
                i.setOrderId(rs.getInt("order_id"));
                i.setMenuId(rs.getInt("menu_id"));
                i.setQuantity(rs.getInt("quantity"));
                i.setSubtotal(rs.getDouble("subtotal"));
                i.setStatus(rs.getString("status"));
                orderItems.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }
}

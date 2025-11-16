package DAO;

import Model.MenuItem;

import java.sql.*;
import java.util.ArrayList;

public class MenuItemDAO {

    public boolean addMenuItem(MenuItem menuItem) {
        String sql = "INSERT INTO menu_items (name, description, price, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, menuItem.getMenuName());
            stmt.setString(2, menuItem.getDescription());
            stmt.setDouble(3, menuItem.getPrice());
            stmt.setBoolean(4, menuItem.getStatus());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    menuItem.setMenuId(keys.getInt(1));
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public MenuItem getMenuItemById(int menuId) {
        String sql = "SELECT * FROM menu_items WHERE menu_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, menuId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String menuName = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                boolean status = rs.getBoolean("is_available");

                return new MenuItem(menuId, menuName, description, price, status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateMenuItem(MenuItem menuItem) {
        String sql = "UPDATE menu_items SET name = ?, description = ?, price = ?, status = ? WHERE menu_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, menuItem.getMenuName());
            stmt.setString(2, menuItem.getDescription());
            stmt.setDouble(3, menuItem.getPrice());
            stmt.setBoolean(4, menuItem.getStatus());
            stmt.setInt(5, menuItem.getMenuId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteMenuItem(int menuId) {
        String sql = "DELETE FROM menu_items WHERE menu_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, menuId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<MenuItem> getAllMenuItems() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM menu_items ORDER BY menu_id";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                MenuItem i = new MenuItem();

                i.setMenuId(rs.getInt("menu_id"));
                i.setMenuName(rs.getString("menu_name"));
                i.setDescription(rs.getString("description"));
                i.setPrice(rs.getDouble("price"));
                i.setStatus(rs.getBoolean("status"));

                menuItems.add(i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItems;
    }

    public boolean resetAutoIncrement(){
        String sql = "ALTER TABLE loyalty_members AUTO_INCREMENT = 1";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

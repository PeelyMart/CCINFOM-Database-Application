package DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import Model.Reservations;

public class ReservationDAO {

    public boolean addReservation(Reservations reservation) {
        String sql = "INSERT INTO reservations (table_id, reserve_name, date_and_time, subtotal, is_active) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, reservation.getTableId());
            stmt.setString(2, reservation.getReserveName());
            stmt.setTimestamp(3, Timestamp.valueOf(reservation.getDateAndTime()));
            stmt.setDouble(4, reservation.getSubtotal());
            stmt.setBoolean(5, reservation.getIsActive());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    reservation.setRequestId(keys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Reservations getReservationById(int requestId) {
        String sql = "SELECT * FROM reservations WHERE request_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, requestId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int tableId = rs.getInt("table_id");
                String reserveName = rs.getString("reserve_name");
                LocalDateTime dateAndTime = rs.getTimestamp("date_and_time").toLocalDateTime();
                double subtotal = rs.getDouble("subtotal");
                boolean isActive = rs.getBoolean("is_active");

                return new Reservations(requestId, tableId, reserveName, dateAndTime, subtotal, isActive);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateReservation(Reservations reservation) {
        String sql = "UPDATE reservations SET table_id = ?, reserve_name = ?, date_and_time = ?, subtotal = ?, is_active = ? WHERE request_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservation.getTableId());
            stmt.setString(2, reservation.getReserveName());
            stmt.setTimestamp(3, Timestamp.valueOf(reservation.getDateAndTime()));
            stmt.setDouble(4, reservation.getSubtotal());
            stmt.setBoolean(5, reservation.getIsActive());
            stmt.setInt(6, reservation.getRequestId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteReservation(int requestId) {
        String sql = "DELETE FROM reservations WHERE request_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, requestId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Reservations> getAllReservations() {
        ArrayList<Reservations> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations ORDER BY request_id";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Reservations r = new Reservations(
                        rs.getInt("request_id"),
                        rs.getInt("table_id"),
                        rs.getString("reserve_name"),
                        rs.getTimestamp("date_and_time").toLocalDateTime(),
                        rs.getDouble("subtotal"),
                        rs.getBoolean("is_active")
                );
                reservations.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }
}
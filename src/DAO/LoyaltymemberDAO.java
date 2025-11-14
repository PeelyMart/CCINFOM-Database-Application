package DAO;

import Model.LoyaltyMember;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
public class LoyaltymemberDAO {
    /**
     * Increment loyalty points for a member by a given amount.
     *
     * @param customerId The ID of the loyalty member.
     * @param points     The number of points to add (must be positive).
     * @return true if the update succeeded, false otherwise.
     */
    public static boolean addPoints(int customerId, int points) {
        if (points <= 0) return false; // nothing to add

        String sql = "UPDATE loyalty_members SET points = points + ? WHERE customer_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, points);
            stmt.setInt(2, customerId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Decrement loyalty points for a member by a given amount.
     * Points will not go below 0.
     *
     * @param customerId The ID of the loyalty member.
     * @param points     The number of points to subtract (must be positive).
     * @return true if the update succeeded, false otherwise.
     */
    public boolean subtractPoints(int customerId, int points) {
        if (points <= 0) return false; // nothing to subtract

        String sql = "UPDATE loyalty_members SET points = GREATEST(points - ?, 0) WHERE customer_id = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, points);
            stmt.setInt(2, customerId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    public boolean addLoyaltyMember(LoyaltyMember member) {
        String sql = "INSERT INTO loyalty_members (name, contact, join_date, points, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, member.getName());
            stmt.setString(2, member.getContact());
            stmt.setDate(3, Date.valueOf(member.getJoinDate()));
            stmt.setInt(4, member.getPoints());
            stmt.setString(5, member.getStatus());

            int affected = stmt.executeUpdate();
            if (affected > 0) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    member.setCustomerId(keys.getInt(1));
                }

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public LoyaltyMember getLoyaltyMemberById(int memberId) {
        String sql = "SELECT * FROM loyalty_members WHERE customer_id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String contact = rs.getString("contact");
                LocalDate joinDate = rs.getDate("join_date").toLocalDate();
                int points = rs.getInt("points");
                String status = rs.getString("status");

                return new LoyaltyMember(memberId, name, contact, joinDate, points, status);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateLoyaltyMember(LoyaltyMember member) {
        String sql = "UPDATE loyalty_members SET name = ?, contact = ?, join_date = ?, points = ?, status = ? WHERE customer_id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, member.getName());
            stmt.setString(2, member.getContact());
            stmt.setDate(3, Date.valueOf(member.getJoinDate()));
            stmt.setInt(4, member.getPoints());
            stmt.setString(5, member.getStatus());
            stmt.setInt(6, member.getCustomerId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteLoyaltyMember(int memberId) {
        String sql = "DELETE FROM loyalty_members WHERE customer_id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<LoyaltyMember> getAllLoyaltyMembers() {
        ArrayList<LoyaltyMember> members = new ArrayList<>();
        String sql = "SELECT * FROM loyalty_members ORDER BY customer_id";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LoyaltyMember m = new LoyaltyMember();

                m.setCustomerId(rs.getInt("customer_id"));
                m.setName(rs.getString("name"));
                m.setContact(rs.getString("contact"));
                m.setJoinDate(rs.getDate("join_date").toLocalDate());
                m.setPoints(rs.getInt("points"));
                m.setStatus(rs.getString("status"));

                members.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
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

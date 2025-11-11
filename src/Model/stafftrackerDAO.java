import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class stafftrackerDAO {

    public boolean addStaffTracker(StaffTracker tracker) {
        String sql = "INSERT INTO staff_tracker (staff_id, date, time_in, time_out, hours_worked) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, tracker.getStaffId());
            stmt.setDate(2, new java.sql.Date(tracker.getDate().getTime()));
            stmt.setTime(3, new java.sql.Time(tracker.getTimeIn().getTime()));
            stmt.setTime(4, new java.sql.Time(tracker.getTimeOut().getTime()));
            stmt.setBigDecimal(5, tracker.getHoursWorked());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public StaffTracker getStaffTrackerById(int staffId, Date date) {
        String sql = "SELECT * FROM staff_tracker WHERE staff_id = ? AND date = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, staffId);
            stmt.setDate(2, new java.sql.Date(date.getTime()));

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Date timeIn = new Date(rs.getTime("time_in").getTime());
                Date timeOut = new Date(rs.getTime("time_out").getTime());
                BigDecimal hoursWorked = rs.getBigDecimal("hours_worked");

                return new StaffTracker(staffId, date, timeIn, timeOut, hoursWorked);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStaffTracker(StaffTracker tracker) {
        String sql = "UPDATE staff_tracker SET time_in = ?, time_out = ?, hours_worked = ? WHERE staff_id = ? AND date = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTime(1, new java.sql.Time(tracker.getTimeIn().getTime()));
            stmt.setTime(2, new java.sql.Time(tracker.getTimeOut().getTime()));
            stmt.setBigDecimal(3, tracker.getHoursWorked());
            stmt.setInt(4, tracker.getStaffId());
            stmt.setDate(5, new java.sql.Date(tracker.getDate().getTime()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteStaffTracker(int staffId, Date date) {
        String sql = "DELETE FROM staff_tracker WHERE staff_id = ? AND date = ?";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, staffId);
            stmt.setDate(2, new java.sql.Date(date.getTime()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<StaffTracker> getAllStaffTrackers() {
        ArrayList<StaffTracker> trackers = new ArrayList<>();
        String sql = "SELECT * FROM staff_tracker ORDER BY staff_id, date";
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int staffId = rs.getInt("staff_id");
                Date date = rs.getDate("date");
                Date timeIn = new Date(rs.getTime("time_in").getTime());
                Date timeOut = new Date(rs.getTime("time_out").getTime());
                BigDecimal hoursWorked = rs.getBigDecimal("hours_worked");

                trackers.add(new StaffTracker(staffId, date, timeIn, timeOut, hoursWorked));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trackers;
    }
}

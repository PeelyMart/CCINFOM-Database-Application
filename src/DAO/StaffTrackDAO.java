package DAO;


import Model.StaffTracker;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class StaffTrackDAO {

    public static void uploadSession(StaffTracker input){
        int idInput = input.getStaffId();
        LocalDate dateInput = input.getTimeIn().toLocalDate();
        LocalDateTime inInput = input.getTimeIn();
        LocalDateTime outInput = input.getTimeOut();
        int minutesInput = input.getSessionMinutes();


        String query = "INSERT INTO staff_tracker (staff_id, date, time_in, time_out, session_minutes) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DB.getConnection(); //joint try parameter these instances of a resource is closed once done with try block
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, idInput);
            stmt.setDate(2, java.sql.Date.valueOf(dateInput));
            stmt.setTimestamp(3, Timestamp.valueOf(inInput));
            stmt.setTimestamp(4, Timestamp.valueOf(outInput));
            stmt.setInt(5, minutesInput);
            stmt.executeUpdate();
        }
        catch(SQLException e ){
            e.printStackTrace();
        }






    }

}

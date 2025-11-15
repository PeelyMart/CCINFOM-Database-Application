package Model;
import java.math.BigDecimal;
import java.util.Date;
import java.time.LocalDateTime;

public class StaffTracker {
    private int staffId;
    private Date date;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private int session_minutes;

    // Constructor
    public StaffTracker(int staffId){
        this.staffId = staffId;
        
    }

    // Getters
    public int getStaffId() {
        return staffId;
    }

    public Date getDate() {
        return date;
    }

    public LocalDateTime getTimeIn() {
        return timeIn;
    }

    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    public int getSessionMinutes() {
        return session_minutes;
    }

    // Setters
    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTimeIn(LocalDateTime timeIn) {
        this.timeIn = timeIn;
    }

    public void setTimeOut(LocalDateTime timeOut) {
        this.timeOut = timeOut;
    }

    public void setSessionMinutes(int session_minutes) {
        this.session_minutes = session_minutes;
    }
}

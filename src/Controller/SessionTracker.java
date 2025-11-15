package Controller;

import DAO.StaffTrackDAO;
import Model.Staff;
import Model.StaffTracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SessionTracker {
    private static StaffTracker currentTracker = null;
    private static int session_minutes;


    public static void startSession(Staff user){
        currentTracker = new StaffTracker(user.getStaffId());
        currentTracker.setTimeIn(LocalDateTime.now());
    }

    /*
     *
     *
     */
    private static void calculateMinutes(){
        session_minutes = (int) Duration.between(currentTracker.getTimeIn(), currentTracker.getTimeOut()).toMinutes();
        currentTracker.setSessionMinutes(session_minutes);
    }
    private static void resetTracker(){
        currentTracker = null;
        session_minutes = 0;
    }

    public static void endSession(){
        currentTracker.setTimeOut(LocalDateTime.now());
        calculateMinutes();
        StaffTrackDAO.uploadSession(currentTracker);
        printSession(currentTracker);
        resetTracker();
    }

    private static void printSession(StaffTracker tracker) {
        if (tracker == null) {
            System.out.println("No active session.");
            return;
        }

        // Print header
        System.out.printf("%-15s %-20s %-20s %-10s%n",
                "Staff ID", "Time In", "Time Out", "Minutes");
        System.out.println("===============================================================");

        // Print data
        String timeOut = tracker.getTimeOut() != null ? tracker.getTimeOut().toString() : "N/A";

        System.out.printf("%-15d %-20s %-20s %-10d%n",
                tracker.getStaffId(),
                tracker.getTimeIn(),
                timeOut,
                tracker.getSessionMinutes());
    }
}

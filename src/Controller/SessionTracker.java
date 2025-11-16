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
        System.out.println("[LOCAL MODEL|UNSAVED] Session Started by:ID" + currentTracker.getStaffId() + "\nTime In: " + currentTracker.getTimeIn());
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
        System.out.println("[LOCAL MODEL|UNSAVED] Session Started by:ID" + currentTracker.getStaffId() + "\nTime In: " + currentTracker.getTimeIn() + "\n Time out: " + currentTracker.getTimeOut());
        calculateMinutes();
        System.out.println("Sending data to the database");
        StaffTrackDAO.uploadSession(currentTracker);
        System.out.println("Successfully Uploaded Session Minutes: " );
        printSession(currentTracker);
        resetTracker();
    }

    private static void printSession(StaffTracker tracker) {
        if (tracker == null) {
            System.out.println("No active session.");
            return;
        }

        // Print header
        System.out.printf("%-15s %-20s \t %-20s \t %-10s%n",
                "Staff ID", "Time In", "Time Out", "Minutes");
        System.out.println("===============================================================");

        // Print data
        String timeOut = tracker.getTimeOut() != null ? tracker.getTimeOut().toString() : "N/A";

        System.out.printf("%-15d %-20s \t %-20s \t %-10d%n",
                tracker.getStaffId(),
                tracker.getTimeIn(),
                timeOut,
                tracker.getSessionMinutes());
    }
}

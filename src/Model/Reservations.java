
package Model;
import java.time.LocalDateTime;

public class Reservations {

    //private attributes

    private int requestId;       
    private int tableId;         
    private String reserveName;  
    private LocalDateTime dateAndTime;  
    private boolean isActive;


    //constructor

    public Reservations(int requestId, int tableId, String reserveName, LocalDateTime dateAndTime, double subtotal, boolean isActive) {
        this.requestId = requestId;
        this.tableId = tableId;
        this.reserveName = reserveName;
        this.dateAndTime = dateAndTime;
        this.isActive = isActive;
    }

    // getters and setters

    public int getRequestId() {
        return requestId;
    }

    public int getTableId() {
        return tableId;
    }

    public String getReserveName() {
        return reserveName;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }


    public boolean getIsActive() {
        return isActive;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public void setReserveName(String reserveName) {
        this.reserveName = reserveName;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}

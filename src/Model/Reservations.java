import java.time.LocalDateTime;

public class Reservations {

    //private attributes

    private int requestId;       
    private int tableId;         
    private String reserveName;  
    private LocalDateTime dateAndTime;  
    private double subtotal; 
    private boolean isActive;    


    //constructor

    public Reservations(int requestId, int tableId, String reserveName, LocalDateTime dateAndTime, double subtotal, boolean isActive) {
        this.requestId = requestId;
        this.tableId = tableId;
        this.reserveName = reserveName;
        this.dateAndTime = dateAndTime;
        this.subtotal = subtotal;
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

    public double getSubtotal() {
        return subtotal;
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

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}

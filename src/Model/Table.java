package Model;

public class Table {
    // Private attributes
    private int tableId;
    private int capacity;
    private boolean isAvailable;

    // Constructors

    public Table(){

    }
    public Table(int capacity, boolean tableStatus) {
        this.capacity = capacity;
        this.isAvailable = tableStatus;
    }
/*
    public Table(int tableId, int capacity, boolean tableStatus) {
        this.tableId = tableId;
        this.capacity = capacity;
        this.isAvailable = tableStatus;
    }
*/
    // Getters
    public int getTableId() {
        return tableId;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean getTableStatus(){
        return isAvailable;
    }

    // Setters
    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setTableStatus(boolean tableStatus) {
        this.isAvailable = tableStatus;
    }
}

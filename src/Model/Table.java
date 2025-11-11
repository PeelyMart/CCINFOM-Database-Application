package Model;

public class Table {
    // Private attributes
    private int tableId;
    private int capacity;
    private String tableStatus;

    // Constructors
    public Table() {
    }

    public Table(int tableId, int capacity, String tableStatus) {
        this.tableId = tableId;
        this.capacity = capacity;
        this.tableStatus = tableStatus;
    }

    // Getters
    public int getTableId() {
        return tableId;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getTableStatus() {
        return tableStatus;
    }

    // Setters
    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }
}

package Model;

public class MenuItem {
    // Private Attributes
    private int menuId;
    private String menuName;
    private String description;  
    private Double price;
    private Boolean status;

    // Constructors
    public MenuItem() {
        
    }

    public MenuItem(int menuId, String menuName, String description, Double price, Boolean status) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    // Getters
    public int getMenuId() {
        return menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getStatus() {
        return status;
    }

    // Setters
    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}

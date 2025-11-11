package Model;

import java.time.LocalDate;

public class LoyaltyMember {

    // Private attributes
    private int customerId;    
    private String name;
    private String contact;
    private LocalDate joinDate;
    private int points;
    private String status;

    // Constructors
    public LoyaltyMember() {
        
    }

    public LoyaltyMember(int customerId, String name, String contact, LocalDate joinDate, int points, String status) {
        this.customerId = customerId;
        this.name = name;
        this.contact = contact;
        this.joinDate = joinDate;
        this.points = points;
        this.status = status;
    }

    // Getters

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public int getPoints() {
        return points;
    }

    public String getStatus() {
        return status;
    }

    //Setters

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

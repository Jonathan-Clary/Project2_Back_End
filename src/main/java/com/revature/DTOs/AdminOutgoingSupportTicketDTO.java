package com.revature.DTOs;


import com.revature.enums.TicketStatus;
import com.revature.enums.TicketType;

public class AdminOutgoingSupportTicketDTO {

    //Model Variables
    private int supportTicketId;
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private TicketStatus status;
    private TicketType type;
    private int adminId;

    //Constructors
    public AdminOutgoingSupportTicketDTO() {
    }

    public AdminOutgoingSupportTicketDTO(int supportTicketId, int userId, String firstName, String lastName,
                                         String email, String description, TicketStatus status, TicketType type, int adminId) {
        this.supportTicketId = supportTicketId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.status = status;
        this.type = type;
        this.adminId = adminId;
    }

    public int getSupportTicketId() {
        return supportTicketId;
    }

    public void setSupportTicketId(int supportTicketId) {
        this.supportTicketId = supportTicketId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "AdminOutgoingSupportTicketDTO{" +
                "supportTicketId=" + supportTicketId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", adminId=" + adminId +
                '}';
    }
}

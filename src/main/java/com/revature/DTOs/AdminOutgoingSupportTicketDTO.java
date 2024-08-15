package com.revature.DTOs;

public class AdminOutgoingSupportTicketDTO {

    //Model Variables
    private int supportTicketId;
    private String description;
    private int userId;
    private int adminId;

    //Constructors
    public AdminOutgoingSupportTicketDTO() {
    }

    public AdminOutgoingSupportTicketDTO(int supportTicketId, String description, int userId, int adminId) {
        this.supportTicketId = supportTicketId;
        this.description = description;
        this.userId = userId;
        this.adminId = adminId;
    }

    //Getters and Setters
    public int getSupportTicketId() {
        return supportTicketId;
    }

    public void setSupportTicketId(int supportTicketId) {
        this.supportTicketId = supportTicketId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    //toString
    @Override
    public String toString() {
        return "UserOutgoingSupportTicketDTO{" +
                "supportTicketId=" + supportTicketId +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", adminId=" + adminId +
                '}';
    }
}

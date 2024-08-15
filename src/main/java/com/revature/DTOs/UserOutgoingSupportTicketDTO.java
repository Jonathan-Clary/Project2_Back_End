package com.revature.DTOs;

public class UserOutgoingSupportTicketDTO {

    //Model Variables
    private int supportTicketId;
    private String description;
    private int userId;
    private String email;


    //Constructors
    public UserOutgoingSupportTicketDTO() {
    }

    public UserOutgoingSupportTicketDTO(int supportTicketId, String description, int userId, String email) {
        this.supportTicketId = supportTicketId;
        this.description = description;
        this.userId = userId;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //toString
    @Override
    public String toString() {
        return "UserOutgoingSupportTicketDTO{" +
                "supportTicketId=" + supportTicketId +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                '}';
    }
}

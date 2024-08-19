package com.revature.DTOs;


import com.revature.enums.TicketType;

public class IncomingSupportTicketDTO {

    //Model Variables
    private int userId;
    private String description;
    private TicketType type;

    public IncomingSupportTicketDTO() {
    }

    public IncomingSupportTicketDTO(int userId, String description, TicketType type) {
        this.userId = userId;
        this.description = description;
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IncomingSupportTicketDTO{" +
                "userId=" + userId +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }
}

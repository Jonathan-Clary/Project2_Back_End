package com.revature.DTOs;

import java.util.UUID;

public class IncomingSupportTicketDTO {

    //Model Variables
    private UUID userId;
    private String description;
    private String type;

    public IncomingSupportTicketDTO() {
    }

    public IncomingSupportTicketDTO(UUID userId, String description, String type) {
        this.userId = userId;
        this.description = description;
        this.type = type;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "IncomingSupportTicketDTO{" +
                "userId=" + userId +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

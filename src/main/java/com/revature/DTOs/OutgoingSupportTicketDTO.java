package com.revature.DTOs;


import com.revature.enums.TicketStatus;
import com.revature.enums.TicketType;

import java.util.Date;
import java.util.UUID;

public class OutgoingSupportTicketDTO {

    //Model Variables
    private UUID supportTicketId;
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String description;
    private TicketStatus status;
    private TicketType type;
    private Date createdAt;


    //Constructors
    public OutgoingSupportTicketDTO() {
    }

    public OutgoingSupportTicketDTO(UUID supportTicketId, UUID userId, String firstName, String lastName, String email, String description, TicketStatus status, TicketType type, Date createdAt) {
        this.supportTicketId = supportTicketId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.description = description;
        this.status = status;
        this.type = type;
        this.createdAt = createdAt;
    }

    public UUID getSupportTicketId() {
        return supportTicketId;
    }

    public void setSupportTicketId(UUID supportTicketId) {
        this.supportTicketId = supportTicketId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OutgoingSupportTicketDTO{" +
                "supportTicketId=" + supportTicketId +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }
}

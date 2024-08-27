package com.revature.models;

import com.revature.enums.TicketType;
import com.revature.enums.TicketStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "support_tickets")
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID supportTicketId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    // Ensures that the enum values are stored as strings
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @Column(nullable = false)
    private String description;

    // Ensures that the enum values are stored as strings
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketType type;

    @Column(nullable = false)
    private Date createdAt;

    @Column
    private Date resolvedAt;


    // This method is executed before persisting the ticket into the database
    @PrePersist
    private void onCreate(){
        // Sets the timestamps for when the ticket is created
        createdAt = new Date();

        // Sets PENDING as the default status when the ticket is created for the first time
        if(status == null){
            status = TicketStatus.PENDING;
        }

        // Sets GENERAL as the default type if none is specified
        if(type == null){
           type = TicketType.GENERAL;
        }

    }

    public SupportTicket() {}

    public SupportTicket(UUID supportTicketId, User user, TicketStatus status, String description, TicketType type) {
        this.supportTicketId = supportTicketId;
        this.user = user;
        this.status = status;
        this.description = description;
        this.type = type;
    }

    public UUID getSupportTicketId() {
        return supportTicketId;
    }

    public void setSupportTicketId(UUID supportTicketId) {
        this.supportTicketId = supportTicketId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(Date resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    @Override
    public String toString() {
        return "SupportTicket{" +
                "supportTicketId=" + supportTicketId +
                ", user=" + user +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                ", resolvedAt=" + resolvedAt +
                '}';
    }

}

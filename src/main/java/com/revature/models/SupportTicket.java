package com.revature.models;

import com.revature.enums.TicketType;
import com.revature.enums.TicketStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "support_tickets")
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supportTicketId;

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

    private long createdAt;
    private long resolvedAt;


    // This method is executed before persisting the ticket into the database
    @PrePersist
    private void onCreate(){
        // Sets the timestamps for when the ticket is created
        createdAt = Instant.now().toEpochMilli();

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

    public SupportTicket(int supportTicketId, User user, TicketStatus status, String description, TicketType type) {
        this.supportTicketId = supportTicketId;
        this.user = user;
        this.status = status;
        this.description = description;
        this.type = type;
    }

    public int getSupportTicketId() {
        return supportTicketId;
    }

    public void setSupportTicketId(int supportTicketId) {
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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(long resolvedAt) {
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

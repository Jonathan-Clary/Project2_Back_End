package com.revature.mappers;

import com.revature.DTOs.OutgoingSupportTicketDTO;
import com.revature.enums.TicketStatus;
import com.revature.enums.TicketType;
import com.revature.models.SupportTicket;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class OutgoingSupportTicketMapper {

    public OutgoingSupportTicketMapper() {
    }

    public OutgoingSupportTicketDTO toDto(SupportTicket supportTicket){
        UUID supportTicketId = supportTicket.getSupportTicketId();
        UUID userId = supportTicket.getUser().getUserId();
        String firstName = supportTicket.getUser().getFirstName();
        String lastName = supportTicket.getUser().getLastName();
        String email = supportTicket.getUser().getEmail();
        String description = supportTicket.getDescription();
        TicketStatus status = supportTicket.getStatus();
        TicketType type = supportTicket.getType();
        Date createdAt = supportTicket.getCreatedAt();

        return new OutgoingSupportTicketDTO(
                supportTicketId, userId, firstName, lastName, email, description, status, type, createdAt
        );
    }
}

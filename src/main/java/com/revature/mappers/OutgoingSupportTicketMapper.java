package com.revature.mappers;

import com.revature.DTOs.OutgoingSupportTicketDTO;
import com.revature.enums.TicketStatus;
import com.revature.enums.TicketType;
import com.revature.models.SupportTicket;
import org.springframework.stereotype.Component;

@Component
public class OutgoingSupportTicketMapper {

    public OutgoingSupportTicketMapper() {
    }

    public OutgoingSupportTicketDTO toDto(SupportTicket supportTicket){
        int supportTicketId = supportTicket.getSupportTicketId();
        int userId = supportTicket.getUser().getUserId();
        String firstName = supportTicket.getUser().getFirstName();
        String lastName = supportTicket.getUser().getLastName();
        String email = supportTicket.getUser().getEmail();
        String description = supportTicket.getDescription();
        TicketStatus status = supportTicket.getStatus();
        TicketType type = supportTicket.getType();

        return new OutgoingSupportTicketDTO(
                supportTicketId, userId, firstName, lastName, email, description, status, type
        );
    }
}

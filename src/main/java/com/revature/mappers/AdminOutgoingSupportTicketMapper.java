package com.revature.mappers;

import com.revature.enums.TicketStatus;
import com.revature.enums.TicketType;
import com.revature.models.SupportTicket;
import com.revature.DTOs.AdminOutgoingSupportTicketDTO;
import org.springframework.stereotype.Component;

@Component
public class AdminOutgoingSupportTicketMapper {
    public AdminOutgoingSupportTicketDTO toDto(SupportTicket supportTicket, int adminId) {
        int supportTicketId = supportTicket.getSupportTicketId();
        int userId = supportTicket.getUser().getUserId();
        String firstName = supportTicket.getUser().getFirstName();
        String lastName = supportTicket.getUser().getLastName();
        String email = supportTicket.getUser().getEmail();
        String description = supportTicket.getDescription();
        TicketStatus status = supportTicket.getStatus();
        TicketType type = supportTicket.getType();

        return new AdminOutgoingSupportTicketDTO(
                supportTicketId, userId, firstName, lastName, email, description, status, type, adminId
        );
    }
}

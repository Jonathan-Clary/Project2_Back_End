package com.revature.mappers;

import com.revature.DTOs.UserOutgoingSupportTicketDTO;
import com.revature.models.SupportTicket;

public class UserOutgoingSupportTicketMapper {
    public UserOutgoingSupportTicketDTO toDto(SupportTicket supportTicket){
        int supportTicketId = supportTicket.getSupportTicketId();
        String description = supportTicket.getDescription();
        int userId = supportTicket.getUser().getUserId();
        String email = supportTicket.getUser().getEmail();

        return new UserOutgoingSupportTicketDTO(supportTicketId, description, userId, email);
    }
}

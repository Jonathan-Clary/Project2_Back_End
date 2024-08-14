package com.revature.mappers;

import com.revature.models.SupportTicket;
import com.revature.DTOs.AdminOutgoingSupportTicketDTO;

public class AdminOutgoingSupportTicketMapper {
    public AdminOutgoingSupportTicketDTO toDto(SupportTicket supportTicket, int admins) {
        int supportTicketId = supportTicket.getSupportTicketId();
        String description = supportTicket.getDescription();
        int userId = supportTicket.getUser().getUserId();

        int adminID = (int)(Math.random() * admins) + 1;

        return new AdminOutgoingSupportTicketDTO(supportTicketId, description, userId, adminID);
    }
}

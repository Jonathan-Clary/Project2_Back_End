package com.revature.mappers;

import com.revature.models.Admin;
import com.revature.models.SupportTicket;
import com.revature.DTOs.AdminOutgoingSupportTicketDTO;


public class AdminOutgoingSupportTicketMapper {
    public AdminOutgoingSupportTicketDTO toDto(SupportTicket supportTicket, int adminId) {
        int supportTicketId = supportTicket.getSupportTicketId();
        String description = supportTicket.getDescription();
        int userId = supportTicket.getUser().getUserId();
        String firstName = supportTicket.getUser().getFirstName();
        String lastName = supportTicket.getUser().getLastName();
        String email = supportTicket.getUser().getEmail();

        return new AdminOutgoingSupportTicketDTO(
                supportTicketId, description, userId, firstName, lastName, email, adminId
        );
    }
}

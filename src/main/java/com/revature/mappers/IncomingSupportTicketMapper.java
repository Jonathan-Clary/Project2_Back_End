package com.revature.mappers;

import com.revature.DAOs.UserDAO;
import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.models.SupportTicket;
import com.revature.models.User;

import java.util.Optional;

public class IncomingSupportTicketMapper {
    private UserDAO uDao;

    public SupportTicket toDto(IncomingSupportTicketDTO incomingSupportTicket) {

        SupportTicket returnedSupportTicket = new SupportTicket(
                0,
                null,
                null,
                incomingSupportTicket.getDescription(),
                incomingSupportTicket.getType()
        );

        Optional<User> foundUser = uDao.findById(incomingSupportTicket.getUserId());
        foundUser.ifPresent(returnedSupportTicket::setUser);

        return returnedSupportTicket;
    }
}

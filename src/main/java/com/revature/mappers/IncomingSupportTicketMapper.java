package com.revature.mappers;

import com.revature.DAOs.UserDAO;
import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.enums.TicketType;
import com.revature.exceptions.InvalidTypeException;
import com.revature.models.SupportTicket;
import com.revature.models.User;

import java.util.Optional;

public class IncomingSupportTicketMapper {
    private UserDAO uDao;
    private TypeMapper mapperType;

    public SupportTicket toDto(IncomingSupportTicketDTO incomingSupportTicket) throws InvalidTypeException {

        TicketType incomingType = mapperType.tDto(incomingSupportTicket.getType());

        SupportTicket returnedSupportTicket = new SupportTicket(
                0,
                null,
                null,
                incomingSupportTicket.getDescription(),
                incomingType
        );

        Optional<User> foundUser = uDao.findById(incomingSupportTicket.getUserId());
        foundUser.ifPresent(returnedSupportTicket::setUser);

        return returnedSupportTicket;
    }
}

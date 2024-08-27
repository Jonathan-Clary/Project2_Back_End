package com.revature.mappers;

import com.revature.DAOs.UserDAO;
import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.enums.TicketType;
import com.revature.exceptions.InvalidTypeException;
import com.revature.models.SupportTicket;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IncomingSupportTicketMapper {
    private UserDAO uDao;
    private TypeMapper mapperType;

    public IncomingSupportTicketMapper() {
    }

    @Autowired
    public IncomingSupportTicketMapper(UserDAO uDao, TypeMapper mapperType) {
        this.uDao = uDao;
        this.mapperType = mapperType;
    }

    public SupportTicket toDto(IncomingSupportTicketDTO incomingSupportTicket) throws InvalidTypeException {

        TicketType incomingType = mapperType.tDto(incomingSupportTicket.getType());

        SupportTicket returnedSupportTicket = new SupportTicket(
                null,
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

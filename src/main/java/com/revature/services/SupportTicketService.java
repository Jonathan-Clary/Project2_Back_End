package com.revature.services;

import com.revature.DAOs.SupportTicketDAO;
import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.DTOs.OutgoingSupportTicketDTO;
import com.revature.exceptions.*;
import com.revature.mappers.*;
import com.revature.models.SupportTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupportTicketService {

    //Model Variable
    private SupportTicketDAO stDao;

    //Mappers
    private OutgoingSupportTicketMapper mapperUser;
    private IncomingSupportTicketMapper mapperIncoming;

    //Constructor
    @Autowired
    public SupportTicketService(SupportTicketDAO stDao, OutgoingSupportTicketMapper mapperUser, IncomingSupportTicketMapper mapperIncoming) {
        this.stDao = stDao;
        this.mapperUser = mapperUser;
        this.mapperIncoming = mapperIncoming;
    }


    //-------------Get Methods------------
    //
    //

    //Return a SupportTicket using its Id
    public OutgoingSupportTicketDTO getSupportTicketById(int id) throws SupportTicketNotFoundException {

        Optional<SupportTicket> st = stDao.findById(id);

        if (st.isPresent()) {

             return mapperUser.toDto(st.get());

        } else {
            throw new SupportTicketNotFoundException(id);

        }

    }

    //Return all SupportTickets
    public List<OutgoingSupportTicketDTO> getAllSupportTickets() {

        //Instantiate Lists
        List<SupportTicket> stl = stDao.findAll();
        List<OutgoingSupportTicketDTO> returnList = new ArrayList<OutgoingSupportTicketDTO>();

        for (SupportTicket st: stl) {

            returnList.add(mapperUser.toDto(st));

        }

        return returnList;

    }

    //-------------Post Methods------------
    //
    //

    //Method to register a new support ticket
    public OutgoingSupportTicketDTO register(IncomingSupportTicketDTO incomingTicket) throws InvalidDescriptionException, InvalidTypeException, UserNotFoundException {

        if (incomingTicket.getDescription().equals("") || incomingTicket.getDescription() == null) {
            throw new InvalidDescriptionException();
        }

        if (incomingTicket.getUserId() <= 0) {
            throw new UserNotFoundException(incomingTicket.getUserId());
        }

        SupportTicket toSaveTicket = mapperIncoming.toDto(incomingTicket);
        OutgoingSupportTicketDTO outgoingTicket = mapperUser.toDto(toSaveTicket);

        stDao.save(toSaveTicket);

        return outgoingTicket;

    }

    //-------------Delete Methods------------
    //
    //

    //Method to delete a Support Ticket from the Database
    public OutgoingSupportTicketDTO delete(int id) throws SupportTicketNotFoundException {

        Optional<SupportTicket> toDeleteTicket = stDao.findById(id);

        if (toDeleteTicket.isPresent()) {

            stDao.delete(toDeleteTicket.get());
            return mapperUser.toDto(toDeleteTicket.get());

        } else {
            throw new SupportTicketNotFoundException(id);

        }

    }

}//End of SupportTicketService

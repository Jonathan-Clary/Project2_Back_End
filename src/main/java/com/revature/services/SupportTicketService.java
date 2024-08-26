package com.revature.services;

import com.revature.DAOs.SupportTicketDAO;
import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.DTOs.OutgoingSupportTicketDTO;
import com.revature.exceptions.*;
import com.revature.mappers.*;
import com.revature.models.Stay;
import com.revature.models.SupportTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupportTicketService {

    Logger log = LoggerFactory.getLogger(SupportTicketService.class);

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
        log.debug("Method 'getSupportTicketById' invoked with id: {}",id);
        Optional<SupportTicket> st = stDao.findById(id);
        if (st.isPresent()) {

            OutgoingSupportTicketDTO outgoingSupportTicketDTO = mapperUser.toDto(st.get());
            log.debug("Method 'getSupportTicketById' returning: {}",outgoingSupportTicketDTO.toString());
            return outgoingSupportTicketDTO;

        } else {
            throw new SupportTicketNotFoundException(id);

        }

    }

    //Return all SupportTickets
    public List<OutgoingSupportTicketDTO> getAllSupportTickets() {
        log.debug("Method 'getAllSupportTickets' invoked");

        //Instantiate Lists
        List<SupportTicket> stl = stDao.findAll();
        List<OutgoingSupportTicketDTO> returnList = new ArrayList<OutgoingSupportTicketDTO>();

        for (SupportTicket st: stl) {

            returnList.add(mapperUser.toDto(st));

        }

        //Append id's to string for logging, because printing every object is excessive
        StringBuilder sb = new StringBuilder();
        for(OutgoingSupportTicketDTO o: returnList){
            sb.append(o.getSupportTicketId()).append(", ");
        }
        log.debug("Method 'getAllSupportTickets' returning OutgoingSupportTicketDTO list with supportTicket_ids: {}", sb.toString());

        return returnList;

    }

    //-------------Post Methods------------
    //
    //

    //Method to register a new support ticket
    public OutgoingSupportTicketDTO register(IncomingSupportTicketDTO incomingTicket) throws InvalidDescriptionException, InvalidTypeException, UserNotFoundException {
        log.debug("Method 'register' invoked with incomingTicket: {}",incomingTicket.toString());

        if (incomingTicket.getDescription().equals("") || incomingTicket.getDescription() == null) {
            throw new InvalidDescriptionException();
        }

        if (incomingTicket.getUserId() <= 0) {
            throw new UserNotFoundException(incomingTicket.getUserId());
        }

        SupportTicket toSaveTicket = mapperIncoming.toDto(incomingTicket);
        OutgoingSupportTicketDTO outgoingTicket = mapperUser.toDto(toSaveTicket);

        stDao.save(toSaveTicket);

        log.debug("Method 'register' returning: {}",outgoingTicket.toString());
        return outgoingTicket;

    }

    //-------------Delete Methods------------
    //
    //

    //Method to delete a Support Ticket from the Database
    public OutgoingSupportTicketDTO delete(int id) throws SupportTicketNotFoundException {
        log.debug("Method 'delete' invoked with id: {}",id);
        Optional<SupportTicket> toDeleteTicket = stDao.findById(id);

        if (toDeleteTicket.isPresent()) {

            stDao.delete(toDeleteTicket.get());
            OutgoingSupportTicketDTO outgoingSupportTicketDTO = mapperUser.toDto(toDeleteTicket.get());
            log.debug("Method 'delete' returning: {}",outgoingSupportTicketDTO.toString());
            return mapperUser.toDto(toDeleteTicket.get());

        } else {
            throw new SupportTicketNotFoundException(id);

        }

    }

}//End of SupportTicketService

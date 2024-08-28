package com.revature.services;

import com.revature.DAOs.SupportTicketDAO;
import com.revature.DAOs.UserDAO;
import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.DTOs.OutgoingSupportTicketDTO;
import com.revature.exceptions.*;
import com.revature.mappers.*;
import com.revature.models.SupportTicket;
import com.revature.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupportTicketService {

    Logger log = LoggerFactory.getLogger(SupportTicketService.class);

    //Model Variable
    private SupportTicketDAO stDao;
    private UserDAO uDao;

    //Mappers
    private OutgoingSupportTicketMapper mapperUser;
    private IncomingSupportTicketMapper mapperIncoming;

    //Constructor
    @Autowired
    public SupportTicketService(SupportTicketDAO stDao, UserDAO uDao, OutgoingSupportTicketMapper mapperUser, IncomingSupportTicketMapper mapperIncoming) {
        this.stDao = stDao;
        this.uDao = uDao;
        this.mapperUser = mapperUser;
        this.mapperIncoming = mapperIncoming;
    }


    //-------------Get Methods------------
    //
    //

    //Return a SupportTicket using its Id
    public OutgoingSupportTicketDTO getSupportTicketById(UUID id) throws SupportTicketNotFoundException {
        log.debug("Method 'getSupportTicketById' invoked with id: {}",id);
        Optional<SupportTicket> st = stDao.findById(id);
        if (st.isPresent()) {

            OutgoingSupportTicketDTO outgoingSupportTicketDTO = mapperUser.toDto(st.get());
            log.debug("Method 'getSupportTicketById' returning: {}",outgoingSupportTicketDTO.toString());
            return outgoingSupportTicketDTO;

        } else {
            log.error("Method 'getSupportTicketById' throwing: {}",SupportTicketNotFoundException.class);
            throw new SupportTicketNotFoundException(id);

        }

    }

    //Return a list of all Support Tickets submitted by a User
    public List<OutgoingSupportTicketDTO> getAllSupportTicketsByUserId(UUID id) throws UserNotFoundException{
        log.debug("Method 'getAllSupportTicketsByUserId' invoked with id: {}", id);
        Optional<User> user = uDao.findById(id);

        if (user.isPresent()){

            List<SupportTicket> stList = stDao.findAllByUserUserId(id);
            List<OutgoingSupportTicketDTO> outgoingTicketList = new ArrayList<OutgoingSupportTicketDTO>();
            for (SupportTicket st: stList) {

                outgoingTicketList.add(mapperUser.toDto(st));

            }

            log.debug("Method 'getAllSupportTicketsByUserId' returning: {}",outgoingTicketList.toString());
            return outgoingTicketList;

        } else {
            log.error("Method 'getAllSupportTicketsByUserId' throwing: {}", UserNotFoundException.class);
            throw new UserNotFoundException(id);

        }


    }

    //-------------Post Methods------------
    //
    //

    //Method to register a new support ticket
    public OutgoingSupportTicketDTO register(IncomingSupportTicketDTO incomingTicket) throws InvalidDescriptionException, InvalidTypeException, UserNotFoundException {
        log.debug("Method 'register' invoked with incomingTicket: {}",incomingTicket.toString());

        if (incomingTicket.getDescription().equals("") || incomingTicket.getDescription() == null) {
            log.error("Method 'register' throwing: {}", InvalidDescriptionException.class);
            throw new InvalidDescriptionException();
        }

        if (incomingTicket.getUserId() == null) {
            log.error("Method 'register' throwing: {}", UserNotFoundException.class);
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
    public OutgoingSupportTicketDTO delete(UUID id) throws SupportTicketNotFoundException {
        log.debug("Method 'delete' invoked with id: {}",id);
        Optional<SupportTicket> toDeleteTicket = stDao.findById(id);

        if (toDeleteTicket.isPresent()) {

            stDao.delete(toDeleteTicket.get());
            OutgoingSupportTicketDTO outgoingSupportTicketDTO = mapperUser.toDto(toDeleteTicket.get());
            log.debug("Method 'delete' returning: {}",outgoingSupportTicketDTO.toString());
            return mapperUser.toDto(toDeleteTicket.get());

        } else {
            log.error("Method 'delete' throwing: {}", SupportTicketNotFoundException.class);
            throw new SupportTicketNotFoundException(id);

        }

    }

}//End of SupportTicketService

package com.revature.services;

import com.revature.DAOs.AdminDAO;
import com.revature.DAOs.NoteDAO;
import com.revature.DAOs.SupportTicketDAO;
import com.revature.DTOs.IncomingSupportTicketDTO;
import com.revature.DTOs.UserOutgoingSupportTicketDTO;
import com.revature.exceptions.*;
import com.revature.mappers.*;
import com.revature.models.Note;
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
    private AdminDAO aDao;
    private NoteDAO nDao;

    //Mappers
    //private AdminOutgoingSupportTicketMapper mapperAdmin;
    private UserOutgoingSupportTicketMapper mapperUser;
    private IncomingSupportTicketMapper mapperIncoming;
    private TypeMapper mapperType;
    private StatusMapper mapperStatus;

    //Mappers need
    //Constructor
    @Autowired
    public SupportTicketService(SupportTicketDAO stDao, AdminDAO aDao, NoteDAO nDao ,
                                UserOutgoingSupportTicketMapper mapperUser, IncomingSupportTicketMapper mapperIncoming, TypeMapper mapperType, StatusMapper mapperStatus) {
        this.stDao = stDao;
        this.aDao = aDao;
        this.nDao = nDao;
        this.mapperUser = mapperUser;
        this.mapperIncoming = mapperIncoming;
        this.mapperType = mapperType;
        this.mapperStatus = mapperStatus;
    }


    //-------------Get Methods------------
    //
    //

    //Method to return a SupportTicket by its id with the associated User using userId and email
    public UserOutgoingSupportTicketDTO getSupportTicketById(int id) throws SupportTicketNotFoundException {

        Optional<SupportTicket> st = stDao.findById(id);

        if (st.isPresent()) {

             return mapperUser.toDto(st.get());

        } else {
            throw new SupportTicketNotFoundException(id);

        }

    }

    //Methods to return all SupportTickets for Users
    public List<UserOutgoingSupportTicketDTO> getAllSupportTickets() {

        //Instantiate Lists
        List<SupportTicket> stl = stDao.findAll();
        List<UserOutgoingSupportTicketDTO> returnList = new ArrayList<UserOutgoingSupportTicketDTO>();

        for (SupportTicket st: stl) {

            returnList.add(mapperUser.toDto(st));

        }

        return returnList;

    }

    //Methods to return all SupportTickets for Admins
    // --- Method has been migrated to Admin microservice ---

    //Method to return all tickets assigned to an admin
    // --- Method has been migrated to Admin microservice ---

    //-------------Post Methods------------
    //
    //

    //Method to register a new support ticket
    public UserOutgoingSupportTicketDTO register(IncomingSupportTicketDTO incomingTicket) throws InvalidDescriptionException, InvalidTypeException, UserNotFoundException {

        if (incomingTicket.getDescription().equals("") || incomingTicket.getDescription() == null) {
            throw new InvalidDescriptionException();
        }

        if (incomingTicket.getUserId() <= 0) {
            throw new UserNotFoundException(incomingTicket.getUserId());
        }

        SupportTicket toSaveTicket = mapperIncoming.toDto(incomingTicket);
        UserOutgoingSupportTicketDTO outgoingTicket = mapperUser.toDto(toSaveTicket);

        stDao.save(toSaveTicket);

        return outgoingTicket;

    }

    //-------------Patch Methods------------
    //
    //

    //Method to update the Support Ticket's description
    public UserOutgoingSupportTicketDTO updateDescription(int id, String description) throws SupportTicketNotFoundException {

        Optional<SupportTicket> foundTicket = stDao.findById(id);

        if (foundTicket.isPresent()) {

            SupportTicket updatedTicket = foundTicket.get();
            updatedTicket.setDescription(description);
            stDao.save(updatedTicket);
            return mapperUser.toDto(updatedTicket);

        } else {
            throw new SupportTicketNotFoundException(id);

        }

    }

    //Method to update the Support Ticket Status
    public UserOutgoingSupportTicketDTO updateStatus(int id, String status) throws SupportTicketNotFoundException, InvalidStatusException {

        Optional<SupportTicket> foundTicket = stDao.findById(id);

        if (foundTicket.isPresent()) {

            SupportTicket updatedTicket = foundTicket.get();
            updatedTicket.setStatus(mapperStatus.tDto(status));
            stDao.save(updatedTicket);
            return mapperUser.toDto(updatedTicket);

        } else {
            throw new SupportTicketNotFoundException(id);

        }

    }

    //Method to update the Support Ticket Type
    public UserOutgoingSupportTicketDTO updateType(int id, String type) throws SupportTicketNotFoundException, InvalidTypeException {

        Optional<SupportTicket> foundTicket = stDao.findById(id);

        if (foundTicket.isPresent()) {

            SupportTicket updatedTicket = foundTicket.get();
            updatedTicket.setType(mapperType.tDto(type));
            stDao.save(updatedTicket);
            return mapperUser.toDto(updatedTicket);

        } else {
            throw new SupportTicketNotFoundException(id);

        }

    }

    //-------------Delete Methods------------
    //
    //

    //Method to delete a Support Ticket from the Database
    public UserOutgoingSupportTicketDTO delete(int id) throws SupportTicketNotFoundException {

        Optional<SupportTicket> toDeleteTicket = stDao.findById(id);

        if (toDeleteTicket.isPresent()) {

            stDao.delete(toDeleteTicket.get());
            return mapperUser.toDto(toDeleteTicket.get());

        } else {
            throw new SupportTicketNotFoundException(id);

        }

    }

}//End of SupportTicketService

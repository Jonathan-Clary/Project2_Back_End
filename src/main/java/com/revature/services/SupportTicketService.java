package com.revature.services;

import com.revature.DAOs.AdminDAO;
import com.revature.DAOs.NoteDAO;
import com.revature.DAOs.SupportTicketDAO;
import com.revature.DTOs.AdminOutgoingSupportTicketDTO;
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
    private AdminOutgoingSupportTicketMapper mapperAdmin;
    private UserOutgoingSupportTicketMapper mapperUser;
    private IncomingSupportTicketMapper mapperIncoming;
    private TypeMapper mapperType;
    private StatusMapper mapperStatus;

    //Constructor
    @Autowired
    public SupportTicketService(SupportTicketDAO stDao, AdminDAO aDao, NoteDAO nDao) {
        this.stDao = stDao;
        this.aDao = aDao;
        this.nDao = nDao;
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
    public List<AdminOutgoingSupportTicketDTO> getAlSupportTicketsAdmin() {

        //Instantiate Lists
        List<Note> nl = nDao.findAll();
        List<AdminOutgoingSupportTicketDTO> returnList = new ArrayList<AdminOutgoingSupportTicketDTO>();

        for (Note n: nl) {

            returnList.add(mapperAdmin.toDto(n.getSupportTicket(), n.getAdmin().getAdminId()));

        }

        return returnList;

    }

    //Method to return all tickets assigned to an admin
    public List<AdminOutgoingSupportTicketDTO> getAllToAdminId(int id) throws AdminNotFoundException,
            SupportTicketNotFoundException{

        //Check if Admin exists
        if (!(aDao.existsById(id))) {
            throw new AdminNotFoundException(id);
        }

        //Instantiate Lists
        List<Note> nl = nDao.findAllByAdminAdminId(id);
        List<SupportTicket> stl = new ArrayList<SupportTicket>();
        List<AdminOutgoingSupportTicketDTO> returnList = new ArrayList<AdminOutgoingSupportTicketDTO>();

        //Find SupportTickets that are assigned to Admin
        for (Note n: nl) {

            Optional<SupportTicket> optST = stDao.findById(n.getSupportTicket().getSupportTicketId());

            if (optST.isPresent()) {

                stl.add(optST.get());

            } else {
                throw new SupportTicketNotFoundException();

            }

        }

        //Add AdminOutgoingSupportTicketDTO to returnList
        for (SupportTicket st: stl) {

            returnList.add(mapperAdmin.toDto(st,id));

        }

        return returnList;

    }

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
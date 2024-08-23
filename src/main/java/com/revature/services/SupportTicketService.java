package com.revature.services;

import com.revature.DAOs.AdminDAO;
import com.revature.DAOs.NoteDAO;
import com.revature.DAOs.SupportTicketDAO;
import com.revature.DTOs.AdminOutgoingSupportTicketDTO;
import com.revature.DTOs.UserOutgoingSupportTicketDTO;
import com.revature.exceptions.AdminNotFoundException;
import com.revature.exceptions.SupportTicketNotFoundException;
import com.revature.mappers.AdminOutgoingSupportTicketMapper;
import com.revature.mappers.UserOutgoingSupportTicketMapper;
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

    //Constructor
    @Autowired
    public SupportTicketService(SupportTicketDAO stDao, AdminDAO aDao, NoteDAO nDao, AdminOutgoingSupportTicketMapper mapperAdmin,
                                UserOutgoingSupportTicketMapper mapperUser) {
        this.stDao = stDao;
        this.aDao = aDao;
        this.nDao = nDao;
        this.mapperAdmin = mapperAdmin;
        this.mapperUser = mapperUser;
    }

    //Methods

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

        //Create AdminOutgoingSupportTicketDTO List
        for (SupportTicket st: stl) {

            returnList.add(mapperAdmin.toDto(st,id));

        }

        return returnList;

    }

}//End of SupportTicketService

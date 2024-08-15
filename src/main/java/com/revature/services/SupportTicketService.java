package com.revature.services;

import com.revature.DAOs.AdminDAO;
import com.revature.DAOs.NoteDAO;
import com.revature.DAOs.SupportTicketDAO;
import com.revature.DTOs.AdminOutgoingSupportTicketDTO;
import com.revature.DTOs.UserOutgoingSupportTicketDTO;
import com.revature.exceptions.UserNotFoundException;
import com.revature.mappers.AdminOutgoingSupportTicketMapper;
import com.revature.mappers.UserOutgoingSupportTicketMapper;
import com.revature.models.Note;
import com.revature.models.SupportTicket;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SupportTicketService {

    //Model Variable
    private final SupportTicketDAO stDao;
    private final AdminDAO aDao;
    private final NoteDAO nDao;

    //Mappers
    private final AdminOutgoingSupportTicketMapper mapperAdmin;
    private final UserOutgoingSupportTicketMapper mapperUser;

    //Methods

    //Method to return a SupportTicket by its id with the associated User using userId and email
    public UserOutgoingSupportTicketDTO getSupportTicketById(int id) throws UserNotFoundException, Exception{

        Optional<SupportTicket> st = stDao.findById(id);

        if (st.isPresent()) {

             return mapperUser.toDto(st.get());

        } else {
            throw new Exception();  //TODO::Create SupportTicketNotFoundException

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

            returnList.add(mapperAdmin.toDto(n.getSupportTicket(), n.getAdmin().getAdmin_id()));

        }

        return returnList;

    }

    //Method to return all tickets assigned to an admin
    public List<AdminOutgoingSupportTicketDTO> getAllToAdminId(int id)  throws UserNotFoundException, Exception{

        //Check if Admin exists
        if (!(aDao.existsById(id))) {
            throw new UserNotFoundException(id); //TODO::Create AdminNotFoundException
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

                throw new Exception(""); //TODO::Create SupportTicketNotFoundException

            }

        }

        //Create AdminOutgoingSupportTicketDTO List
        for (SupportTicket st: stl) {

            returnList.add(mapperAdmin.toDto(st,id));

        }

        return returnList;

    }

}//End of SupportTicketService

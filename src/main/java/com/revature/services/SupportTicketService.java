package com.revature.services;

import com.revature.DAOs.AdminDAO;
import com.revature.DAOs.SupportTicketDAO;
import com.revature.DTOs.AdminOutgoingSupportTicketDTO;
import com.revature.DTOs.UserOutgoingSupportTicketDTO;
import com.revature.exceptions.UserNotFoundException;
import com.revature.mappers.AdminOutgoingSupportTicketMapper;
import com.revature.mappers.UserOutgoingSupportTicketMapper;
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
    private NoteDAO nDao;  //TODO::Resolve when Note MVC is instantiated

    //Mappers
    private AdminOutgoingSupportTicketMapper mapperAdmin;
    private UserOutgoingSupportTicketMapper mapperUser;

    //Constructor
    @Autowired
    public SupportTicketService(SupportTicketDAO stDao, AdminDAO aDao, NoteDAO nDao) {
        this.stDao = stDao;
        this.aDao = aDao;
        this.nDao = nDao;
    }

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

    //Method to return all tickets assigned to an admin //TODO::How valid is this method?
    public List<AdminOutgoingSupportTicketDTO> getAllToAdminId(int id)  throws UserNotFoundException{

        //Check if Admin exists
        if (!(aDao.existsById(id))) {
            throw new UserNotFoundException(id); //TODO::Create AdminNotFoundException
        }

        //Find total number of admin
        int total = (int)aDao.count();

        //Instantiate Lists
        List<Note> nl = nDao.findAllByAdminAdminId(); //TODO::Create NoteDAO and method
        List<SupportTicket> stl = new ArrayList<SupportTicket>();
        List<AdminOutgoingSupportTicketDTO> returnList = new ArrayList<AdminOutgoingSupportTicketDTO>();

        //Find SupportTickets that are assigned to Admin
        for (Note n: nl) {
            stl.add(stDao.findBy(n.getSupportTicket().getSupportTicketId())); //TODO::Note getter
        }

        //Create AdminOutgoingSupportTicketDTO List
        for (SupportTicket st: stl) {
            returnList.add(mapperAdmin.toDto(st,total));
        }

        return returnList;

    }

}//End of SupportTicketService

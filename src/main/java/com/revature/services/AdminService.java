package com.revature.services;

import com.revature.DAOs.AdminDAO;
import com.revature.mappers.AdminMapper;
import com.revature.models.Admin;
import com.revature.DTOs.OutgoingAdminDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AdminService {

    //DAOs
    private AdminDAO aDao;

    //Mapper
    private AdminMapper am;

    //Constructor
    @Autowired
    public AdminService(AdminDAO aDao) {
        this.aDao = aDao;
    }

    //Service Methods

    //Method to return OutgoingAdmins to the controller
    public List<OutgoingAdminDTO> getAllAdmins() {

        List<Admin> al = aDao.findAll();
        List<OutgoingAdminDTO> returnedList = new ArrayList<OutgoingAdminDTO>();

        for (Admin oa : al) {
            returnedList.add(am.toDto(oa));
        }

        return returnedList;

    }
}





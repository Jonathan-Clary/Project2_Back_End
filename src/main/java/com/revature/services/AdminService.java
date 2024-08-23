package com.revature.services;

import com.revature.DAOs.AdminDAO;
import com.revature.exceptions.AdminNotFoundException;
import com.revature.exceptions.CustomException;
import com.revature.models.Admin;
import com.revature.security.PasswordEncoderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AdminService {

    //DAOs
    private AdminDAO aDao;

    /* TODO: uncomment when admin authcontroller is made
    @Autowired
    AdminAuthController authController;
    */

    @Autowired
    private PasswordEncoderProvider passwordEncoder;

    //Constructor
    @Autowired
    public AdminService(AdminDAO aDao) {
        this.aDao = aDao;
    }

    //Service Methods

    public Admin getAdminById(int adminId) throws CustomException {
        Optional<Admin> admin = aDao.findById(adminId);
        if(admin.isPresent()){
            return admin.get();
        }
        throw new AdminNotFoundException("Admin with id: " + adminId+" was not found.");
    }




}





package com.revature.services;

import com.revature.DAOs.AdminDAO;
import com.revature.exceptions.AdminNotFoundException;
import com.revature.exceptions.CustomException;
import com.revature.models.Admin;
import com.revature.security.PasswordEncoderProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class AdminService {

    //Logger
    Logger log = LoggerFactory.getLogger(AdminService.class);
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

    public Admin getAdminById(UUID adminId) throws CustomException {
        log.debug("Method 'getAdminById' invoked with adminId: {}", adminId);
        Optional<Admin> admin = aDao.findById(adminId);
        if(admin.isPresent()){
            log.debug("Method 'getAdminById' returning: {}", admin.get().toString());
            return admin.get();
        }
        throw new AdminNotFoundException("Admin with id: " + adminId+" was not found.");
    }




}





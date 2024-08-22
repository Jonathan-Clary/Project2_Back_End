package com.revature.controllers;

import com.revature.DTOs.OutgoingAdminDTO;
import com.revature.exceptions.CustomException;
import com.revature.models.Admin;
import com.revature.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    //Service Variable(s)
    private AdminService as;

    /* TODO: uncomment when admin authcontroller is made
    @Autowired
    AdminAuthController authController;
    */
    //Constructor
    @Autowired
    public AdminController(AdminService as) {
        this.as = as;
    }

    //Mappings
    @GetMapping
    public List<OutgoingAdminDTO> getAllAdmins() {
        return as.getAllAdmins();
    }

    @PostMapping("/register")
    public ResponseEntity<Admin> createAdmin(@RequestBody @Valid Admin admin)throws CustomException {
        try{
            Admin returningAdmin =  as.createAdmin(admin);
            return ResponseEntity.status(201).body(returningAdmin);
        }catch(Exception e){
            return ResponseEntity.status(400).body(null);
        }
    }

    @PatchMapping
    public ResponseEntity<Object> updateLoggedInAdminProfile(@RequestBody Map<String,String> newAdmin) throws CustomException {
        try{
            Admin admin = as.updateAdminById(loggedInAdminId(), newAdmin);
            return ResponseEntity.ok(admin);
        }catch(Exception e){
            return ResponseEntity.status(400).body(null);
        }
    }

    private int loggedInAdminId() throws CustomException {

        Admin authAdmin = null;// authController.getAuthenticatedAdmin(); TODO: uncomment when adminauthcontroller is made
        if(authAdmin != null){
            return authAdmin.getAdminId();
        }
        return 0;

    }



}

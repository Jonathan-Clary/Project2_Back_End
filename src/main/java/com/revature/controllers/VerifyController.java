package com.revature.controllers;

import com.revature.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/verify")
@CrossOrigin
public class VerifyController {
    Logger log = LoggerFactory.getLogger(VerifyController.class);

    private final EmailService emailService;

    @Autowired
    public VerifyController(EmailService emailService){this.emailService = emailService;}

    @DeleteMapping("/{email}")
    public ResponseEntity<Object> deleteFavorite(@PathVariable UUID email){
        log.debug("Endpoint VERIFY {}", email);
        try{
            emailService.verifyEmail(email);
            return ResponseEntity.status(200).body("deleted");
        }catch(Exception e) {
            log.warn("Email already verified");
            return ResponseEntity.status(404).body(null);
        }
    }


}

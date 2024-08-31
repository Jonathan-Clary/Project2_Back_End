package com.revature.services;

import com.revature.DAOs.EmailDAO;
import com.revature.models.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {

    Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    EmailDAO emailDAO;

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String email, String subject, String body) {
        log.debug("Method 'sendEmail' invoked with email: {}, subject: {}, body: {}",email,subject,body);
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email); //email which will be sent a email
        message.setSubject(subject); //title of email
        message.setText(body); //body of email
        log.info("Sending email with subject: {}, to: {}",subject,email);
        mailSender.send(message);
    }

    public void sendVerificationEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();

        String frontendlink = "http://localhost:3000/verify/";

        Email e = emailDAO.save(new Email(email));
        if(e.getEmailId() != null){
            message.setTo(email); //email which will be sent a email
            message.setSubject("Email Verification for Travel Planner"); //title of email
            message.setText(frontendlink+e.getEmailId()); //body of email
        }

        mailSender.send(message);
    }

    public void verifyEmail(UUID emailId){

        emailDAO.deleteById(emailId);

    }

    public boolean checkEmailVerified(String email){

        if(emailDAO.findByEmail(email).isPresent()){
            return false;
        }else{
            return true;
        }

    }



}
package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String email, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email); //email which will be sent a email
        message.setSubject(subject); //title of email
        message.setText(body); //body of email

        mailSender.send(message);
    }
}
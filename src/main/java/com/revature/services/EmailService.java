package com.revature.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    Logger log = LoggerFactory.getLogger(EmailService.class);

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
}
package com.revature.exceptions;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class SupportTicketNotFoundException extends CustomException{
    public SupportTicketNotFoundException(UUID id){super("Support Ticket with Id: " + id + " Not Found.");}
    public SupportTicketNotFoundException(String message){super(message);}
    public SupportTicketNotFoundException(){super();}

    @Override
    public int getStatus() {
        return HttpStatus.NOT_FOUND.value();
    }
}

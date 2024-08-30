package com.revature.exceptions;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class AdminNotFoundException extends CustomException {
    public AdminNotFoundException(UUID id){super("Admin with Id: " + id + " Not Found.");}
    public AdminNotFoundException(String message){super(message);}

    @Override
    public int getStatus() {
        return HttpStatus.NOT_FOUND.value();
    }
}

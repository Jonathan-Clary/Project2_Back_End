package com.revature.exceptions;

import org.springframework.http.HttpStatus;

public class AdminNotFoundException extends CustomException{
    public AdminNotFoundException(int id){super("Admin with Id: " + id + " Not Found.");}
    public AdminNotFoundException(String message){super(message);}

    @Override
    public int getStatus() {
        return HttpStatus.NOT_FOUND.value();
    }
}

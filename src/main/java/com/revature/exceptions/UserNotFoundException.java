package com.revature.exceptions;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends CustomException{
    public UserNotFoundException withEmail(String email){
        return new UserNotFoundException("User with email "+email+" Not Found.");
    }
    public UserNotFoundException(){}
    public UserNotFoundException(UUID id){
        super("User with ID:"+id+" Not Found.");
    }
    public UserNotFoundException(String message){
        super(message);
    }

    @Override
    public int getStatus() {
        return HttpStatus.NOT_FOUND.value();
    }
}

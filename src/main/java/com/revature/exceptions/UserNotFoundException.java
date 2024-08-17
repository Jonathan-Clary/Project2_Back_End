package com.revature.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException{
    public UserNotFoundException(int id){
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

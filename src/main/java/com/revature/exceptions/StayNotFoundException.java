package com.revature.exceptions;

import org.springframework.http.HttpStatus;

public class StayNotFoundException extends CustomException{
    public StayNotFoundException(int id){
        super("Stay with ID:"+id+" Not Found.");
    }
    public StayNotFoundException(String message){
        super(message);
    }

    @Override
    public int getStatus() {
        return HttpStatus.NOT_FOUND.value();
    }
}

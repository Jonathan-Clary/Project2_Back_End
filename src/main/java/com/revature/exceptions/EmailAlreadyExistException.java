package com.revature.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistException extends CustomException{
    public EmailAlreadyExistException(String email){
        super("email {"+email+"} already exists.");
    }

    @Override
    public int getStatus() {
        return HttpStatus.CONFLICT.value();
    }
}

package com.revature.exceptions;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ReviewNotFoundException extends CustomException {
    public ReviewNotFoundException(UUID id){super("Review with Id: " + id + " Not Found.");}
    public ReviewNotFoundException(String message){super(message);}

    @Override
    public int getStatus() {
        return HttpStatus.NOT_FOUND.value();
    }
}

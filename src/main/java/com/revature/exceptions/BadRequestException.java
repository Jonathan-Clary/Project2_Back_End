package com.revature.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends CustomException {
    public BadRequestException(int loc){super("Request for location :" + loc+" could not be found.");}
    public BadRequestException(String message){super(message);}

    @Override
    public int getStatus() {
        return HttpStatus.NOT_FOUND.value();
    }
}

package com.revature.exceptions;

import org.springframework.http.HttpStatus;

public class HotelNotFoundException extends CustomException{
    public HotelNotFoundException(int id){
        super("Hotel with ID:"+id+" Was Not Found.");
    }
    public HotelNotFoundException(String message){
        super(message);
    }

    @Override
    public int getStatus() {
        return HttpStatus.NOT_FOUND.value();
    }
}

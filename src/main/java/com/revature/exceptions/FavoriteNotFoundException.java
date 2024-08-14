package com.revature.exceptions;

import org.springframework.http.HttpStatus;

public class FavoriteNotFoundException extends CustomException{
    public FavoriteNotFoundException(int id){
        super("User with ID:"+id+" Not Found.");
    }
    public FavoriteNotFoundException(String message){
        super(message);
    }

    @Override
    public int getStatus() {
        return HttpStatus.NOT_FOUND.value();
    }
}

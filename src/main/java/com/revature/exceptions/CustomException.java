package com.revature.exceptions;

import org.springframework.http.HttpStatus;

// the parent of all other custom exceptions
public class CustomException extends Exception{
    public CustomException(String msg){
        super(msg);
    }
    public CustomException(){
        super("Something Went Wrong.");
    }

    public Object getMsg() {
        return new CustomMessage(super.getMessage());
    }

    // we should override this to return status code based on the case(if user not found -> HttpStatus.NOT_FOUND.value())
    public int getStatus() {
        return HttpStatus.BAD_REQUEST.value();
    }

    private record CustomMessage(String message){}
}
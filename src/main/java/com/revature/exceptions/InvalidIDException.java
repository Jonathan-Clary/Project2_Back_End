package com.revature.exceptions;

public class InvalidIDException extends CustomException{
    public InvalidIDException(){
        super("Please Enter an Integer Positive Number.");
    }
}

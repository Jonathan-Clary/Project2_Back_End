package com.revature.exceptions;

public class InvalidEmailFormatException extends CustomException{
    public InvalidEmailFormatException(){
        super("Please enter a valid email address");
    }
}


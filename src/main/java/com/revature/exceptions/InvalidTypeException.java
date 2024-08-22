package com.revature.exceptions;

public class InvalidTypeException extends CustomException {
    public InvalidTypeException(String type){super(type + " is an Invalid Type.");}

}

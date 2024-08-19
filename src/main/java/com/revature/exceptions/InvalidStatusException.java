package com.revature.exceptions;

public class InvalidStatusException extends CustomException{
    public InvalidStatusException(String status){super(status + " is not a Valid Status.");}

}

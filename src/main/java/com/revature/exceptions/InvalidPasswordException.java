package com.revature.exceptions;

public class InvalidPasswordException extends CustomException {
    public InvalidPasswordException(String requirement) {
        super("Password does not meet requirement" + requirement);
    }
}
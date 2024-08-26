package com.revature.exceptions;

public class InvalidDateFormatException extends CustomException{
    public InvalidDateFormatException() {
        super("Error: Invalid Date Format. " +
                "Please use the following format: EEE MMM dd HH:mm:ss zzz yyyy.");
    }
}

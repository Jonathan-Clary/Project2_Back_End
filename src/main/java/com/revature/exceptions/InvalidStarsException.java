package com.revature.exceptions;

public class InvalidStarsException extends CustomException {
    public InvalidStarsException() {
        super("Stars must be between 1 and 5.");
    }
}

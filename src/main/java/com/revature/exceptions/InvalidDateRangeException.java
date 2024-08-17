package com.revature.exceptions;

import java.time.LocalDate;

public class InvalidDateRangeException extends CustomException{
    public InvalidDateRangeException(LocalDate start, LocalDate end){
        super("End date of: " + end + ", is either before or after start date: " + start);
    }
}
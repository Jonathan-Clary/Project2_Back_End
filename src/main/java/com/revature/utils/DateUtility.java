package com.revature.utils;

import com.revature.exceptions.InvalidDateFormatException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtility {

    public static Date parse(String dateString) throws InvalidDateFormatException{
        // Define the format used by Date.toString()
        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

        try {
            Date date = formatter.parse(dateString); // Parse the string back to Date
            System.out.println("Parsed Date: " + date);
            return date;
        } catch (ParseException e) {
            //e.printStackTrace(); // Handle the exception if parsing fails
            throw new InvalidDateFormatException();
        }
    }

}

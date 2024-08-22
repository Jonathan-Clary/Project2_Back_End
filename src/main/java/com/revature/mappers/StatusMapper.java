package com.revature.mappers;


import com.revature.enums.TicketStatus;
import com.revature.exceptions.InvalidStatusException;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper {
    public TicketStatus tDto(String status) throws InvalidStatusException {
        try {
            return TicketStatus.valueOf(status.toUpperCase());

        } catch (IllegalArgumentException e){
            throw new InvalidStatusException(status);

        }
    }
}

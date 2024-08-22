package com.revature.mappers;

import com.revature.enums.TicketType;
import com.revature.exceptions.InvalidTypeException;
import org.springframework.stereotype.Component;

@Component
public class TypeMapper {
    public TypeMapper() {
    }

    public TicketType tDto(String type) throws InvalidTypeException{
        try {
            return TicketType.valueOf(type.toUpperCase());

        } catch (IllegalArgumentException e){
            throw new InvalidTypeException(type);

        }
    }
}

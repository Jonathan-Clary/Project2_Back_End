package com.revature.mappers;

import com.revature.enums.TicketType;
import com.revature.exceptions.InvalidTypeException;
import org.springframework.stereotype.Component;

@Component
public class TypeMapper {

    public TypeMapper() {
    }

    public TicketType toEnum(String type) throws InvalidTypeException{
        try {
            return TicketType.valueOf(type.toUpperCase().trim());

        } catch (IllegalArgumentException e){
            throw new InvalidTypeException(type);

        }
    }
}

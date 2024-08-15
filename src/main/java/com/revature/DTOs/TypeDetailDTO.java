package com.revature.DTOs;

import com.revature.models.SupportTicket;
import com.revature.models.SupportTicket.Type;

public class TypeDetailDTO {

    //Variable
    private SupportTicket.Type type;

    //Constructors
    public TypeDetailDTO() {
    }

    public TypeDetailDTO(SupportTicket.Type type) {
        this.type = type;
    }

    //Getter and Setter
    public SupportTicket.Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    //toString
    @Override
    public String toString() {
        return "TypeDetailDTO{" +
                "type=" + type +
                '}';
    }
}

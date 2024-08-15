package com.revature.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOutgoingSupportTicketDTO {

    //Getters and Setters
    //Model Variables
    private int supportTicketId;
    private String description;
    private int userId;
    private String firstName;
    private String lastName;
    private String email;


    //Constructors
    public UserOutgoingSupportTicketDTO() {
    }

    public UserOutgoingSupportTicketDTO(int supportTicketId, String description, int userId, String firstName, String lastName, String email) {
        this.supportTicketId = supportTicketId;
        this.description = description;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    //toString
    @Override
    public String toString() {
        return "UserOutgoingSupportTicketDTO{" +
                "supportTicketId=" + supportTicketId +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

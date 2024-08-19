package com.revature.DTOs;


public class AdminOutgoingSupportTicketDTO {

    //Model Variables
    private int supportTicketId;
    private String description;
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private int adminId;

    //Constructors
    public AdminOutgoingSupportTicketDTO() {
    }

    public AdminOutgoingSupportTicketDTO(int supportTicketId, String description, int userId, String firstName, String lastName, String email, int adminId) {
        this.supportTicketId = supportTicketId;
        this.description = description;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.adminId = adminId;
    }

    //toString
    @Override
    public String toString() {
        return "AdminOutgoingSupportTicketDTO{" +
                "supportTicketId=" + supportTicketId +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", adminId=" + adminId +
                '}';
    }
}

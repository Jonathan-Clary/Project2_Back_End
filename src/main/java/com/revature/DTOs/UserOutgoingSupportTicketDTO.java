package com.revature.DTOs;


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

    //Getter and Setter
    public int getSupportTicketId() {
        return supportTicketId;
    }

    public void setSupportTicketId(int supportTicketId) {
        this.supportTicketId = supportTicketId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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

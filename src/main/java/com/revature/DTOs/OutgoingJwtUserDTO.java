package com.revature.DTOs;


public class OutgoingJwtUserDTO {

    private String token;
    private int userId;
    private String email;

    public OutgoingJwtUserDTO(String token, int userId, String email) {
        this.token = token;
        this.userId = userId;
        this.email = email;
    }

    //Getter and Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

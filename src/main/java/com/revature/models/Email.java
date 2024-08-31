package com.revature.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID emailId;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "email is required")
    @jakarta.validation.constraints.Email(message = "please enter a valid email")
    private String email;

    public Email() {
    }

    public Email(String email) {
        this.email = email;
    }
    public Email(UUID emailId, String email) {
        this.emailId = emailId;
        this.email = email;
    }

    public UUID getEmailId() {
        return emailId;
    }

    public void setEmailId(UUID emailId) {
        this.emailId = emailId;
    }

    public @NotEmpty(message = "email is required") @jakarta.validation.constraints.Email(message = "please enter a valid email") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "email is required") @jakarta.validation.constraints.Email(message = "please enter a valid email") String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Email{" +
                "emailId=" + emailId +
                ", email='" + email + '\'' +
                '}';
    }
}

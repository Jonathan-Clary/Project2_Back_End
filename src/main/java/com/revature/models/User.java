package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false)
    @Size(min = 3, message = "please enter at least 3 characters")
    private String firstName;

    @Column(nullable = false)
    @Size(min = 3, message = "please enter at least 3 characters")
    private String lastName;

    @Column(nullable = false)
    @NotEmpty(message = "email is required")
    @Email(message = "please enter a valid email")
    private String email;

    //prevents returning password in json
    //more secure
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /*TODO::Date() creates Date obj w/ current date and time. Date().getTime() returns a long value of the generated Date.
        Potentially easier to implement then creating a String when an account is created. */
    @Column(nullable = false)
    //private String dateCreated;
    private long dateCreated;
    // This method is executed before persisting the user account into the database
    @PrePersist
    private void onCreate(){
        // Sets the timestamps for when the user account is created
        Date epoch = new Date();
        dateCreated = epoch.getTime();
    }

    /*STAYS-HISTORY: This list allows for us to keep track of a user's stay history in the DB
    (potentially unnecessary)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Stay> stays;
    */
    public User() {}

    public User(int userId, String firstName, String lastName, String password, String email, long dateCreated) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.dateCreated = dateCreated;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(dateCreated, user.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email, password, dateCreated);
    }
}

package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "admins")
@Component
public class Admin {

    //Getter and Setters
    //Model Variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int admin_id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    //Relational Variable(s)
    @JsonIgnore
    @OneToMany(mappedBy = "admin", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Note> notes;

    //Constructors
    public Admin() {
    }

    public Admin(int admin_id, String firstName, String lastName, String email, String password) {
        this.admin_id = admin_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    //toString
    @Override
    public String toString() {
        return "Admin{" +
                "admin_id=" + admin_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

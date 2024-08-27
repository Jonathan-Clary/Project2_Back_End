package com.revature.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID hotelId;

    @Column(nullable = false)
    private String hotelName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Date createdAt;

    @PrePersist
    private void onCreate(){
        createdAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // Constructors
    public Hotel() {
    }

    public Hotel(UUID hotelId, String hotelName, String address) {
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.address = address;
    }

    // Getters and Setters


    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // toString

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", hotelName='" + hotelName + '\'' +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

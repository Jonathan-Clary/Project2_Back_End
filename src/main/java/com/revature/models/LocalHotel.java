package com.revature.models;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "hotels")
public class LocalHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hotel_id")
    private UUID hotelId;

    @Column(nullable = false)
    private String hotelName;

    @Column(nullable = false)
    private String address;

    @Column(name = "api_hotel_id", nullable = false) // Ensure this field exists
    private String apiHotelId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors
    public LocalHotel() {
        // Default constructor
    }

    public LocalHotel(String hotelName, String address, User user) {
        this.hotelName = hotelName;
        this.address = address;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getApiHotelId() {
        return apiHotelId;
    }

    public void setApiHotelId(String apiHotelId) {
        this.apiHotelId = apiHotelId;
    }

    @Override
    public String toString() {
        return "LocalHotel{" +
                "hotelId=" + hotelId +
                ", hotelName='" + hotelName + '\'' +
                ", address='" + address + '\'' +
                ", user=" + user +
                '}';
    }
}

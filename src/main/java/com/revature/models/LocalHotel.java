package com.revature.models;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "hotels")
public class LocalHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hotelId;

    @Column(nullable = false)
    private String hotelName;

    @Column(nullable = false)
    private String address;

    @Column(name = "api_hotel_id", nullable = false) // Ensure this field exists
    private String apiHotelId;



    // Constructors
    public LocalHotel() {
        // Default constructor
    }

    public LocalHotel(String hotelName, String address) {
        this.hotelName = hotelName;
        this.address = address;
    }

    // Getters and Setters
    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
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
                '}';
    }
}
package com.revature.models;



import com.revature.DTOs.HotelDTO;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    private UUID hotelId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private  String address;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private double rating;



    public Hotel() {}

    public Hotel(HotelDTO hotelDTO) {
        this.hotelId = hotelDTO.getHotelId();
        this.name = hotelDTO.getName();
        this.address = hotelDTO.getAddress();
        this.image = hotelDTO.getImage();
        this.rating = hotelDTO.getRating();
    }

    public Hotel(UUID hotelId, String name, String address, String image, double rating) {
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.image = image;
        this.rating = rating;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "LocalHotel{" +
                "hotelId=" + hotelId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", rating=" + rating +
                '}';
    }
}

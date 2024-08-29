package com.revature.DTOs;

import com.revature.models.Hotel;

import java.util.UUID;

public class HotelDTO {

    private String name;
    private double rating;
    private String image;
    private String address;
    private UUID hotelId;

    public HotelDTO() {
    }

    public HotelDTO(Hotel hotel) {
        this.name = hotel.getName();
        this.rating = hotel.getRating();
        this.image = hotel.getImage();
        this.address = hotel.getAddress();
        this.hotelId = hotel.getHotelId();
    }

    public HotelDTO(String name, double rating, String image, String address, UUID hotelId) {
        this.name = name;
        this.rating = rating;
        this.image = image;
        this.address = address;
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelID(UUID hotelID) {
        this.hotelId = hotelID;
    }

    @Override
    public String toString() {
        return "HotelDTO{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                ", hotelId='" + hotelId + '\'' +
                '}';
    }
}

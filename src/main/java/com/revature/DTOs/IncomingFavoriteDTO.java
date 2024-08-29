package com.revature.DTOs;

import java.util.UUID;

public class IncomingFavoriteDTO {
    //private String dateAdded;
    private UUID userId;
    private HotelDTO hotel;

    public IncomingFavoriteDTO() {
    }

    public IncomingFavoriteDTO(UUID userId, HotelDTO hotel) {
        this.userId = userId;
        this.hotel = hotel;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public HotelDTO getHotel() {
        return hotel;
    }

    public void setHotel(HotelDTO hotelId) {
        this.hotel = hotelId;
    }

    @Override
    public String toString() {
        return "IncomingFavoriteDTO{" +
                "userId=" + userId +
                ", HotelDTO=" + hotel +
                '}';
    }
}

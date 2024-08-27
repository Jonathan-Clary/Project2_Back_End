package com.revature.DTOs;

import java.util.UUID;

public class IncomingFavoriteDTO {
    //private String dateAdded;
    private UUID userId;
    private UUID hotelId;

    public IncomingFavoriteDTO() {
    }

    public IncomingFavoriteDTO(UUID userId, UUID hotelId) {
        this.userId = userId;
        this.hotelId = hotelId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "IncomingFavoriteDTO{" +
                "userId=" + userId +
                ", hotelId=" + hotelId +
                '}';
    }
}

package com.revature.DTOs;

public class IncomingFavoriteDTO {
    //private String dateAdded;
    private int userId;
    private int hotelId;

    public IncomingFavoriteDTO() {
    }

    public IncomingFavoriteDTO(int userId, int hotelId) {
        this.userId = userId;
        this.hotelId = hotelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
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

package com.revature.DTOs;

import java.util.UUID;

public class IncomingReviewDTO {

    private UUID userId;
    private HotelDTO hotel;
    private int stars;
    private String reviewText;

    public IncomingReviewDTO() {
    }

    public IncomingReviewDTO(UUID userId, HotelDTO hotel, int stars, String reviewText) {
        this.userId = userId;
        this.hotel = hotel;
        this.stars = stars;
        this.reviewText = reviewText;
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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    @Override
    public String toString() {
        return "IncomingReviewDTO{" +
                "userId=" + userId +
                ", hotel=" + hotel +
                ", stars=" + stars +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }
}

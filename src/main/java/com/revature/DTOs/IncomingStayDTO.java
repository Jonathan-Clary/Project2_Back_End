package com.revature.DTOs;

import java.util.Date;
import java.util.UUID;

public class IncomingStayDTO {

    private UUID userId;
    private HotelDTO hotel;
    private Date bookedDate;
    private Date endDate;

    public IncomingStayDTO() {
    }

    public IncomingStayDTO(UUID userId, HotelDTO hotel, Date bookedDate, Date endDate) {
        this.userId = userId;
        this.hotel = hotel;
        this.bookedDate = bookedDate;
        this.endDate = endDate;
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

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }

    public Date getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(Date bookedDate) {
        this.bookedDate = bookedDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "IncomingStayDTO{" +
                "userId=" + userId +
                ", hotel=" + hotel +
                ", bookedDate=" + bookedDate +
                ", endDate=" + endDate +
                '}';
    }
}

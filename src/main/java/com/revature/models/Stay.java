package com.revature.models;



import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "stays")
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID stayId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    //Used for inserts, is not included when responding in json, because json already included entire user obj
    //transient stops from being stored in db, bc User obj already does that
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Include when deserializing, not serializing
    private UUID userId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

//    Used for inserts, is not included when responding in json, because json already included entire hotel obj
//    transient stops from being stored in db, bc User obj already does that
   @Transient
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Include when deserializing, not serializing
   private UUID hotelId;


    @Column(nullable = false)
    private Date bookedDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private Date createdAt;
    @PrePersist
    private void onCreate(){
        createdAt = new Date();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Stay() {}


    public Stay(UUID stayId, User user, Hotel hotel, Date bookedDate, Date endDate) {
        this.stayId = stayId;
        this.user = user;
        this.hotel = hotel;
        this.bookedDate = bookedDate;
        this.endDate = endDate;
    }




    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public UUID getHotelId() {
        return hotelId;
    }

    public void setHotelId(UUID hotelId) {
        this.hotelId = hotelId;
    }

    public UUID getStayId() {
        return stayId;
    }

    public void setStayId(UUID stayId) {
        this.stayId = stayId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Stay{" +
                "stayId=" + stayId +
                ", user=" + user +
                ", userId=" + userId +
                ", hotel=" + hotel +
                ", hotelId=" + hotelId +
                ", bookedDate=" + bookedDate +
                ", endDate=" + endDate +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stay stay = (Stay) o;
        return stayId == stay.stayId && userId == stay.userId && hotelId == stay.hotelId && Objects.equals(user, stay.user) && Objects.equals(hotel, stay.hotel) && Objects.equals(bookedDate, stay.bookedDate) && Objects.equals(endDate, stay.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stayId, user, userId, hotel, hotelId, bookedDate, endDate);
    }
}

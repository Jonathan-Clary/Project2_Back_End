package com.revature.models;



import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "stays")
public class Stay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stayId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    //Used for inserts, is not included when responding in json, because json already included entire user obj
    //transient stops from being stored in db, bc User obj already does that
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Include when deserializing, not serializing
    private int userId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotelId")
    private LocalHotel hotel;

//    Used for inserts, is not included when responding in json, because json already included entire user obj
//    transient stops from being stored in db, bc User obj already does that
   @Transient
   @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//Include when deserializing, not serializing
   private int hotelId;


    @Column(nullable = false)
    private Date bookedDate;

    @Column(nullable = false)
    private Date endDate;

    public Stay() {}


    public Stay(int stayId, User user, LocalHotel hotel, Date bookedDate, Date endDate) {
        this.stayId = stayId;
        this.user = user;
        this.hotel = hotel;
        this.bookedDate = bookedDate;
        this.endDate = endDate;
    }




    public LocalHotel getHotel() {
        return hotel;
    }

    public void setHotel(LocalHotel hotel) {
        this.hotel = hotel;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getStayId() {
        return stayId;
    }

    public void setStayId(int stayId) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
                ", bookedDate='" + bookedDate + '\'' +
                ", endDate='" + endDate + '\'' +
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

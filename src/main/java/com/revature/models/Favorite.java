package com.revature.models;

import com.revature.DTOs.IncomingFavoriteDTO;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int favoriteId;

    @Column(nullable = false)
    private Date dateAdded;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // TODO: this could be one to one will decide later
    private User user;

    @JoinColumn(name = "hotelId")
    @ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private LocalHotel hotel;

    @PrePersist
    void onCreate(){
        //Set date of creation
        dateAdded = new Date();
    }

    public Favorite() {
    }

    public Favorite(int favoriteId, User user, LocalHotel hotel) {
        this.favoriteId = favoriteId;
        //this.dateAdded = dateAdded;
        this.user = user;
        this.hotel = hotel;
    }

    public Favorite(User u, LocalHotel h){
        //this.dateAdded = favorite.getDateAdded();
        this.user = u;
        this.hotel = h;
    }


    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    //public void setDateAdded(String dateAdded) {this.dateAdded = dateAdded;}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalHotel getHotel() {
        return hotel;
    }

    public void setHotel(LocalHotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "favoriteId=" + favoriteId +
                ", dateAdded='" + dateAdded + '\'' +
                ", user=" + user +
                ", hotel=" + hotel +
                '}';
    }
}

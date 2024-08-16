package com.revature.models;

import com.revature.DTOs.IncomingFavoriteDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int favoriteId;

    @Column(nullable = false)
    private String dateAdded;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // TODO: this could be one to one will decide later
    private User user;

    @JoinColumn(name = "hotelId")
    @ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Hotel hotel;

    public Favorite() {
    }

    public Favorite(int favoriteId, String dateAdded, User user, Hotel hotel) {
        this.favoriteId = favoriteId;
        this.dateAdded = dateAdded;
        this.user = user;
        this.hotel = hotel;
    }

    public Favorite(IncomingFavoriteDTO favorite, User u, Hotel h){
        this.dateAdded = favorite.getDateAdded();
        this.user = u;
        this.hotel = h;
    }


    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
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

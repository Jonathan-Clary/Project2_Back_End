package com.revature.models;

import jakarta.persistence.*;

@Entity
@Table(name = "favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int favorite_id;

    @Column(nullable = false)
    private String date_added;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // TODO: this could be one to one will decide later
    private User user;

    @JoinColumn(name = "hotelId")
    @ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Hotel hotel;

    public Favorite() {
    }

    public Favorite(int favorite_id, String date_added, User user, Hotel hotel) {
        this.favorite_id = favorite_id;
        this.date_added = date_added;
        this.user = user;
        this.hotel = hotel;
    }

    public int getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
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
                "favorite_id=" + favorite_id +
                ", date_added='" + date_added + '\'' +
                ", user=" + user +
                ", hotel=" + hotel +
                '}';
    }
}

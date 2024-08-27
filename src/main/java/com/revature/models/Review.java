package com.revature.models;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @Column(nullable = false)
    private String dateAdded;

    @Column(nullable = false)
    private int stars;

    @Column(nullable = false)
    private String reviewText;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @JoinColumn(name = "hotelId")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private LocalHotel hotel;

    public Review() {
    }

    public Review(int reviewId, String dateAdded, int stars, String reviewText, User user, LocalHotel hotel) {
        this.reviewId = reviewId;
        this.dateAdded = dateAdded;
        this.stars = stars;
        this.reviewText = reviewText;
        this.user = user;
        this.hotel = hotel;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
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
        return "Reviews{" +
                "reviewId=" + reviewId +
                ", dateAdded='" + dateAdded + '\'' +
                ", stars=" + stars +
                ", reviewText='" + reviewText + '\'' +
                ", user=" + user +
                ", hotel=" + hotel +
                '}';
    }
}

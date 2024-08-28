package com.revature.models;

import com.revature.DTOs.IncomingReviewDTO;
import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reviewId;

    @Column(nullable = false)
    private Date createdAt;

    @PrePersist
    private void onCreate(){
        createdAt = new Date();
    }

    @Column(nullable = false)
    private int stars;

    @Column(nullable = false)
    private String reviewText;

    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @JoinColumn(name = "hotelId")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Hotel hotel;

    public Review() {
    }
    public Review(IncomingReviewDTO reviewDTO, User u, Hotel h) {
        this.stars = reviewDTO.getStars();
        this.reviewText = reviewDTO.getReviewText();
        this.user = u;
        this.hotel = h;
    }


    public Review(int stars, String reviewText, User user, Hotel hotel) {
        this.stars = stars;
        this.reviewText = reviewText;
        this.user = user;
        this.hotel = hotel;
    }

    public UUID getReviewId() {
        return reviewId;
    }

    public void setReviewId(UUID reviewId) {
        this.reviewId = reviewId;
    }

    public Date getCreatedAt() {
        return createdAt;
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", createdAt=" + createdAt +
                ", stars=" + stars +
                ", reviewText='" + reviewText + '\'' +
                ", user=" + user +
                ", hotel=" + hotel +
                '}';
    }
}

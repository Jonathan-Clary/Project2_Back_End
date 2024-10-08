package com.revature.DAOs;

import com.revature.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewDAO extends JpaRepository<Review, UUID> {

    List<Review> getReviewByHotelHotelId(UUID hotelId);
    List<Review> findByHotelHotelIdAndUserUserId(UUID hotelId, UUID userId);
}

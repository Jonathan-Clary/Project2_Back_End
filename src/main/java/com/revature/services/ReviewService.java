package com.revature.services;

import com.revature.DAOs.ReviewDAO;
import com.revature.DTOs.IncomingReviewDTO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.InvalidStarsException;
import com.revature.models.Hotel;
import com.revature.models.Review;
import com.revature.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService {

    Logger log = LoggerFactory.getLogger(ReviewService.class);

    private ReviewDAO reviewDAO;
    private UserService userService;
    private HotelService hotelService;

    @Autowired
    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    // Creates a Review
    public Review submitReview(IncomingReviewDTO review) throws CustomException {
        log.debug("Method 'submitReview' invoked with review: {}",review.toString());

        User user = userService.getUserById(review.getUserId());
        Hotel hotel = hotelService.saveHotel(review.getHotel());

        if (user == null) {
            log.warn("Method 'submitReview' returning null");
            return null;
        }
        if (hotel == null) {
            log.warn("Method 'submitReview' returning null");
            return null;
        }

        if (review.getStars() < 1 || review.getStars() > 5) {
            throw new InvalidStarsException();
        }

        Review returningReview = reviewDAO.save(new Review(review, user, hotel));
        log.debug("Method 'submitReview' returning: {}",returningReview.toString());
        return returningReview;
    }

    // Update a Review, returns null if review doesn't exist
    public Review updateReview(Map<String, String> updateFields, UUID reviewId) throws CustomException {

        log.debug("Method 'updateReview' invoked with updateFields: {}, reviewId: {}",updateFields.toString(),reviewId);

        int stars = 0;
        String reviewText = "";

        if (updateFields.containsKey("stars")) {
            stars = Integer.parseInt(updateFields.get("stars"));
        }
        if (updateFields.containsKey("reviewText")) {
            reviewText = updateFields.get("reviewText");
        }
        if (stars < 1 || stars > 5) {
            throw new InvalidStarsException();
        }

        Optional<Review> existingReview = reviewDAO.findById(reviewId);

        if (existingReview.isPresent()) {
            Review review = existingReview.get();
            review.setStars(stars);
            review.setReviewText(reviewText);
            Review returningReview = reviewDAO.save(review);
            log.debug("Method 'updateReview' returning: {}",returningReview.toString());
            return returningReview;
        }
        else {
            log.warn("Method 'updateReview' returning null");
            return null;
        }
    }

    // Delete a Review, return 1 if it exists and was successfully deleted
    public int deleteReview(UUID reviewId) {
        log.debug("Method 'deleteReview' invoked with reviewId: {}",reviewId);

        if (reviewDAO.existsById(reviewId)) {
            reviewDAO.deleteById(reviewId);
            log.debug("Method 'deleteReview' completed");
            return 1;
        }
        log.warn("Method 'deleteReview' could not delete review with Id: {}", reviewId);
        return 0;
    }

}

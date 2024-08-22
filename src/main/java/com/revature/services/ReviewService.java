package com.revature.services;

import com.revature.DAOs.ReviewDAO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.InvalidStarsException;
import com.revature.models.Review;
import com.revature.services.UserService;
import com.revature.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.time.*;

@Service
public class ReviewService {

    private ReviewDAO reviewDAO;
    private UserService userService;
    private HotelService hotelService;

    @Autowired
    public ReviewService(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    // Creates a Review
    public Review submitReview(Review review) throws CustomException {
        int userId = review.getUser().getUserId();
        int hotelId = review.getHotel().getHotelId();

        if (userService.getUserById(userId) == null) {
            return null;
        }
        if (hotelService.getHotelById(hotelId) == null) {
            return null;
        }

        if (review.getStars() < 1 || review.getStars() > 5) {
            throw new InvalidStarsException();
        }

        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.format(DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        review.setDateAdded(dateString);

        return reviewDAO.save(review);
    }

    // Update a Review, returns null if review doesn't exist
    public Review updateReview(Map<String, String> updateFields, int reviewId) throws CustomException {

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
            return reviewDAO.save(review);
        }
        else {
            return null;
        }
    }

    // Delete a Review, return 1 if it exists and was successfully deleted
    public int deleteReview(int reviewId) {

        if (reviewDAO.existsById(reviewId)) {
            reviewDAO.deleteById(reviewId);
            return 1;
        }

        return 0;
    }

}

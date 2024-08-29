package com.revature.services;

import com.revature.DAOs.ReviewDAO;
import com.revature.DTOs.HotelDTO;
import com.revature.DTOs.IncomingReviewDTO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.HotelNotFoundException;
import com.revature.exceptions.InvalidStarsException;
import com.revature.exceptions.ReviewNotFoundException;
import com.revature.models.Hotel;
import com.revature.models.Review;
import com.revature.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    Logger log = LoggerFactory.getLogger(ReviewService.class);

    private ReviewDAO reviewDAO;
    private UserService userService;
    private HotelService hotelService;

    @Autowired
    public ReviewService(ReviewDAO reviewDAO, UserService userService, HotelService hotelService) {
        this.reviewDAO = reviewDAO;
        this.userService = userService;
        this.hotelService = hotelService;
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


    public List<Review> getMostRecentReviews() throws CustomException{
        log.debug("Method 'getMostRecentReviews' invoked. ");
        List<Review> review = reviewDAO.findAll();
        if(review != null) {
            review = review.stream()
                    .sorted(Comparator.comparing(Review::getCreatedAt).reversed())
                    .limit(5)
                    .collect(Collectors.toList());
            log.debug("Method 'getMostRecentReviews' returning: {}", review);
            return review;
        }else {
            throw new ReviewNotFoundException("Reviews do not exist.");
        }
    }

    public HotelDTO getHighestReviewed() throws CustomException{
        List<Hotel> hotels = hotelService.findAllHotels();

        // Find the hotel with the highest average rating
        Hotel highestRatedHotel = hotels.stream()
                .max(Comparator.comparingDouble(hotel ->
                {
                    try {
                        return this.getReviewByHotelId(hotel.getHotelId()).stream()
                                .mapToDouble(Review::getStars)
                                .average()
                                .orElse(0.0);
                    } catch (ReviewNotFoundException e) {
                        return -1;
                    }
                }))
                .orElseThrow(() -> new CustomException("No hotels found"));
        return new HotelDTO(highestRatedHotel);
    }


    public List<Review> getReviewByHotelId(UUID hotelId) throws ReviewNotFoundException {
        log.debug("Method 'getReviewByHotelId' invoked with hotelId: {}", hotelId);
        List<Review> review = reviewDAO.getReviewByHotelHotelId(hotelId);
        if(review != null) {
            log.debug("Method 'getReviewByHotelId' returning: {}", review);
            return review;
        }else{
            throw new ReviewNotFoundException("Review for hotelId:"+hotelId+" does not exist.");
        }
    }

    // Delete a Review, return 1 if it exists and was successfully deleted
    public Review getReviewById(UUID reviewId) throws CustomException{
        log.debug("Method 'getReviewById' invoked with hotelId: {}", reviewId);
        Optional<Review> review = reviewDAO.findById(reviewId);
        if(review.isPresent()) {
            Review r = review.get();
            log.debug("Method 'getReviewById' returning: {}", r);
            return r;
        }
        else {
            throw new ReviewNotFoundException(reviewId);
        }
    }

}

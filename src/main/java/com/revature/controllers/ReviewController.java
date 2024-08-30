package com.revature.controllers;

import com.revature.DTOs.HotelDTO;
import com.revature.DTOs.IncomingReviewDTO;
import com.revature.exceptions.CustomException;
import com.revature.models.Favorite;
import com.revature.models.Review;
import com.revature.services.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
@CrossOrigin
public class ReviewController {
    Logger log = LoggerFactory.getLogger(ReviewController.class);
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> submitReview(@RequestBody IncomingReviewDTO requestReview) {
        log.debug("Endpoint POST ./reviews reached");
        try {
            Review review = reviewService.submitReview(requestReview);
            return ResponseEntity.status(200).body(review);
        } catch (CustomException e) {
            log.warn("Exception was thrown: {}", e.getMsg());
            return ResponseEntity.status(400).body(null);
        }
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<Object> updateReview(@RequestBody Map<String, String> updateFields, @PathVariable UUID reviewId) throws CustomException {
        log.debug("Endpoint PATCH ./reviews/{} reached",reviewId);
        Review review = reviewService.updateReview(updateFields, reviewId);
        if (review == null) {
            return ResponseEntity.status(400).body("Review not found with ID: " + reviewId);
        }
        else {
            return ResponseEntity.ok(review);
        }
    }

    @GetMapping("/hotel/best")
    public ResponseEntity<HotelDTO> getHighestRated(){
        log.debug("Endpoint GET ./reviews/hotel/best reached");
        try{
            return ResponseEntity.ok(reviewService.getHighestReviewed());
        }catch(Exception e){
            log.warn("Exception was thrown", e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<List<Review>> getLatestReviewed(){
        log.debug("Endpoint GET ./reviews/latest reached");
        try{
            return ResponseEntity.ok(reviewService.getMostRecentReviews());
        }catch(Exception e){
            log.warn("Exception was thrown", e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Review>> getAllReviewByHotel(@PathVariable UUID hotelId){
        log.debug("Endpoint GET ./favorite/hotel={}",hotelId);
        try{
            return ResponseEntity.ok(reviewService.getReviewByHotelId(hotelId));
        }catch(Exception e){
            log.warn("Exception was thrown", e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Integer> deleteReview(@PathVariable UUID reviewId) {
        log.debug("Endpoint DELETE ./reviews/{} reached",reviewId);
        int result = reviewService.deleteReview(reviewId);
        if (result == 0) {
            return ResponseEntity.status(200).body(null);
        }
        return ResponseEntity.ok(1);
    }

    @GetMapping("/hotel/{hotelId}/user/{userId}")
    public ResponseEntity<?> getAllReviewByHotelAndUser(@PathVariable UUID hotelId, @PathVariable UUID userId) {

        try {
            List<Review> reviews= reviewService.findReviewsByHotelAndUser(hotelId, userId);
            if (reviews.isEmpty()) {
                return ResponseEntity.ok(false);
            }
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            log.warn("Exception was thrown", e);
            return ResponseEntity.status(404).body(null);
        }

    }
    @GetMapping("/user/{userId}/hotel/{hotelId}")
    public ResponseEntity<List<Review>> getAllReviewByHotelAndUserList(@PathVariable UUID userId, @PathVariable UUID hotelId) {

        try {
            List<Review> reviews= reviewService.findReviewsByHotelAndUser(hotelId, userId);

            return ResponseEntity.ok(reviews);

        } catch (Exception e) {
            log.warn("Exception was thrown", e);
            return ResponseEntity.status(404).body(null);
        }

    }
}

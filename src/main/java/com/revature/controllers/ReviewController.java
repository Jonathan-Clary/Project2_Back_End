package com.revature.controllers;

import com.revature.exceptions.CustomException;
import com.revature.models.Review;
import com.revature.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reviews")
@CrossOrigin
public class ReviewController {

    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> submitReview(@RequestBody Review requestReview) {
        try {
            Review review = reviewService.submitReview(requestReview);
            return ResponseEntity.status(200).body(review);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<Object> updateReview(@RequestBody Map<String, String> updateFields, @PathVariable int reviewId) throws CustomException {
        Review review = reviewService.updateReview(updateFields, reviewId);
        if (review == null) {
            return ResponseEntity.status(400).body("Review not found with ID: " + reviewId);
        }
        else {
            return ResponseEntity.ok(review);
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Integer> deleteReview(@PathVariable int reviewId) {
        int result = reviewService.deleteReview(reviewId);
        if (result == 0) {
            return ResponseEntity.status(200).body(null);
        }
        return ResponseEntity.ok(1);
    }
}

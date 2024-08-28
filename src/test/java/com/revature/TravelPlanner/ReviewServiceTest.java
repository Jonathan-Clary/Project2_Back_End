package com.revature.TravelPlanner;

import com.revature.DAOs.ReviewDAO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.InvalidStarsException;
import com.revature.models.Review;
import com.revature.models.User;
import com.revature.models.Hotel;
import com.revature.services.ReviewService;
import com.revature.services.UserService;
import com.revature.services.HotelService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewDAO reviewDAO;

    @Mock
    private UserService userService;

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static UUID UUID_TEST_1;
    private static UUID UUID_TEST_2;

    @BeforeAll
    static void setupIds() {
        UUID_TEST_1 = UUID.randomUUID();
        UUID_TEST_2 = UUID.randomUUID();
    }

    @Test
    void testSubmitReview_Successful() throws CustomException {

        User user = new User();
        user.setUserId(UUID_TEST_1);
        Hotel hotel = new Hotel();
        hotel.setHotelId(UUID_TEST_1);

        Review review = new Review();
        review.setUser(user);
        review.setHotel(hotel);
        review.setStars(5);

        // Mocks the behavior of the 'getById' methods to return the valid 'user' and 'hotel' objects
        when(userService.getUserById(UUID_TEST_1)).thenReturn(user);
        when(hotelService.getHotelById(UUID_TEST_1)).thenReturn(hotel);
        when(reviewDAO.save(any(Review.class))).thenReturn(review);

        // Invoke 'submitReview' to compare to expected
        Review result = reviewService.submitReview(review);

        // Check if the results are as expected
        assertNotNull(result);
        assertEquals(5, result.getStars());
        verify(userService, times(1)).getUserById(UUID_TEST_1);
        verify(hotelService, times(1)).getHotelById(UUID_TEST_1);
        verify(reviewDAO, times(1)).save(any(Review.class));
    }

    @Test
    void testSubmitReview_InvalidStars() throws CustomException {

        User user = new User();
        user.setUserId(UUID_TEST_1);
        Hotel hotel = new Hotel();
        hotel.setHotelId(UUID_TEST_1);

        Review review = new Review();
        review.setUser(user);
        review.setHotel(hotel);
        review.setStars(69);

        // Mocks the behavior of the 'getById' methods to return the valid 'user' and 'hotel' objects
        when(userService.getUserById(UUID_TEST_1)).thenReturn(user);
        when(hotelService.getHotelById(UUID_TEST_1)).thenReturn(hotel);

        // Check if the results are as expected (invalid stars)
        assertThrows(InvalidStarsException.class, () -> {
            reviewService.submitReview(review);
        });
        verify(userService, times(1)).getUserById(UUID_TEST_1);
        verify(hotelService, times(1)).getHotelById(UUID_TEST_1);
        verify(reviewDAO, never()).save(any(Review.class));
    }

    @Test
    void testUpdateReview_Successful() throws CustomException {

        Review existingReview = new Review();
        existingReview.setReviewId(UUID_TEST_1);
        existingReview.setStars(3);
        existingReview.setReviewText("Old");

        Map<String, String> updateFields = new HashMap<>();
        updateFields.put("stars", "5");
        updateFields.put("reviewText", "New");

        // Mocks the behavior of "findById" to return the existing review and "save" to save the updated review
        when(reviewDAO.findById(UUID_TEST_1)).thenReturn(Optional.of(existingReview));
        when(reviewDAO.save(any(Review.class))).thenReturn(existingReview);

        // Invoke the "updateReview" method to compare to expected
        Review updatedReview = reviewService.updateReview(updateFields, UUID_TEST_1);

        // Check if the updated review is as expected
        assertNotNull(updatedReview);
        assertEquals(5, updatedReview.getStars());
        assertEquals("New", updatedReview.getReviewText());
        verify(reviewDAO, times(1)).findById(UUID_TEST_1);
        verify(reviewDAO, times(1)).save(existingReview);
    }

    @Test
    void testUpdateReview_ReviewNotFound() throws CustomException {

        Map<String, String> updateFields = new HashMap<>();
        updateFields.put("stars", "5");
        updateFields.put("reviewText", "New");

        when(reviewDAO.findById(UUID_TEST_1)).thenReturn(Optional.empty());

        // Invoke the "updateReview" method to compare to expected
        Review updatedReview = reviewService.updateReview(updateFields, UUID_TEST_1);

        // Check if "updateReview" returns as expected if the review is not in the table
        assertNull(updatedReview);
        verify(reviewDAO, times(1)).findById(UUID_TEST_1);
        verify(reviewDAO, never()).save(any(Review.class));
    }

    @Test
    void testDeleteReview_Successful() {

        // Mocks the behavior of "existsById" to return true
        when(reviewDAO.existsById(UUID_TEST_1)).thenReturn(true);

        int result = reviewService.deleteReview(UUID_TEST_1);

        // Check if "deleteReview" deletes the review as expected
        assertEquals(1, result);
        verify(reviewDAO, times(1)).existsById(UUID_TEST_1);
        verify(reviewDAO, times(1)).deleteById(UUID_TEST_1);
    }

    @Test
    void testDeleteReview_ReviewDoesNotExist() {

        // Mocks the behavior of "existsById" to return false
        when(reviewDAO.existsById(UUID_TEST_1)).thenReturn(false);

        int result = reviewService.deleteReview(UUID_TEST_1);

        // Check if "deleteReview" catches when a review doesn't exist
        assertEquals(0, result);
        verify(reviewDAO, times(1)).existsById(UUID_TEST_1);
        verify(reviewDAO, never()).deleteById(any(UUID.class));
    }
}

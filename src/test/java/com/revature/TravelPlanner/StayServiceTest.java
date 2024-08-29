package com.revature.TravelPlanner;

import com.revature.DAOs.StayDAO;
import com.revature.DTOs.HotelDTO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.InvalidIDException;
import com.revature.exceptions.StayNotFoundException;
import com.revature.models.User;
import com.revature.models.Hotel;
import com.revature.models.Stay;
import com.revature.services.UserService;
import com.revature.services.StayService;
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
public class StayServiceTest {

    @Mock
    private StayDAO stayDAO;

    @Mock
    private UserService userService;

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private StayService stayService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static UUID UUID_TEST_1;
    private static final Date fakeBookedDate = new Date(2024, Calendar.AUGUST, 25);
    private static final Date fakeEndDate = new Date(2024, Calendar.AUGUST, 26);

    @BeforeAll
    static void setupIds() {
        UUID_TEST_1 = UUID.randomUUID();
    }

    @Test
    void testCreateStay_Successful() throws CustomException {

        Stay stay = new Stay();
        stay.setBookedDate(fakeBookedDate);
        stay.setEndDate(fakeEndDate);

        when(stayDAO.save(any(Stay.class))).thenReturn(stay);

        Stay result = stayService.createStay(stay);

        assertNotNull(result);
        assertEquals(fakeBookedDate, result.getBookedDate());
        assertEquals(fakeEndDate, result.getEndDate());
        verify(stayDAO, times(1)).save(any(Stay.class));
    }

    @Test
    void testCreateStay_BookingDateError() throws CustomException {

        Stay stay = new Stay();
        stay.setBookedDate(fakeEndDate);
        stay.setEndDate(fakeBookedDate);

        // Check if the results are as expected (booking date after/on ending date)
        assertThrows(CustomException.class, () -> {
            stayService.createStay(stay);
        });
        verify(stayDAO, never()).save(any(Stay.class));
    }
}

package com.revature.TravelPlanner;

import com.revature.DAOs.HotelDAO;
import com.revature.models.Hotel;
import com.revature.services.HotelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private HotelDAO hotelDAO;

    @InjectMocks
    private HotelService hotelService;

    private @Mock Hotel mockedHotel1;
    private @Mock Hotel mockedHotel2;
    private @Mock Hotel mockedHotel3;

    @Test
    public void testFindAllHotels() {
        //given
        List<Hotel> hotels = Arrays.asList(mockedHotel1, mockedHotel2 ,mockedHotel3);

        when(hotelDAO.findAll()).thenReturn(hotels);

        //when
        List<Hotel> serviceHotels = hotelService.findAllHotels();

        //then
        assertEquals(hotels, serviceHotels);
        verify(hotelDAO, times(1)).findAll();

    }
}

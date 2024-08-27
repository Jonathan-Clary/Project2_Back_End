package com.revature.TravelPlanner;

import com.revature.DAOs.FavoriteDAO;
import com.revature.DTOs.IncomingFavoriteDTO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.FavoriteNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Favorite;
import com.revature.models.Hotel;
import com.revature.models.User;
import com.revature.services.FavoriteService;
import com.revature.services.HotelService;
import com.revature.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTest {

    @Mock
    private FavoriteDAO favoriteDAO;

    @Mock
    private UserService userService;

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private FavoriteService favoriteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static UUID UUID_TEST_1;
    private static UUID UUID_TEST_2;

    @BeforeAll
    static void setupIds(){
        UUID_TEST_1 = UUID.randomUUID();
        UUID_TEST_2 = UUID.randomUUID();
    }

    @Test
    void testFindAllFavorite() {
        // Arrange
        Favorite favorite1 = new Favorite();
        Favorite favorite2 = new Favorite();
        List<Favorite> favorites = Arrays.asList(favorite1, favorite2);
        when(favoriteDAO.findAll()).thenReturn(favorites);

        // Act
        List<Favorite> result = favoriteService.findAllFavorite();

        // Assert
        assertEquals(2, result.size());
        verify(favoriteDAO, times(1)).findAll();
    }

    @Test
    void testFindAllFavoriteByUser() throws CustomException {
        // Arrange
        User user = new User();
        user.setUserId(UUID_TEST_1);
        Favorite favorite1 = new Favorite();
        Favorite favorite2 = new Favorite();
        Favorite favorite3 = new Favorite();
        List<Favorite> favorites = Arrays.asList(favorite1, favorite2, favorite3);
        when(userService.getUserById(UUID_TEST_1)).thenReturn(user);
        when(favoriteDAO.findByUserUserId(UUID_TEST_1)).thenReturn(favorites);

        // Act
        List<Favorite> result = favoriteService.findAllFavoriteByUser(UUID_TEST_1);

        // Assert
        assertEquals(3, result.size());
        verify(userService, times(1)).getUserById(UUID_TEST_1);
        verify(favoriteDAO, times(1)).findByUserUserId(UUID_TEST_1);
    }

    @Test
    void testFindAllFavoriteByHotel() throws CustomException {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setHotelId(UUID_TEST_1);
        Favorite favorite1 = new Favorite();
        Favorite favorite2 = new Favorite();
        List<Favorite> favorites = Arrays.asList(favorite1, favorite2);
        when(hotelService.getHotelById(UUID_TEST_1)).thenReturn(hotel);
        when(favoriteDAO.findByUserUserId(UUID_TEST_1)).thenReturn(favorites);

        // Act
        List<Favorite> result = favoriteService.findAllFavoriteByHotel(UUID_TEST_1);

        // Assert
        assertEquals(2, result.size());
        verify(hotelService, times(1)).getHotelById(UUID_TEST_1);
        verify(favoriteDAO, times(1)).findByUserUserId(UUID_TEST_1);
    }

    @Test
    void testAddFavoriteSave() throws CustomException {
        // Arrange
        IncomingFavoriteDTO favoriteDTO = new IncomingFavoriteDTO();
        favoriteDTO.setHotelId(UUID_TEST_1);
        favoriteDTO.setUserId(UUID_TEST_1);
        Hotel hotel = new Hotel();
        User user = new User();
        Favorite favorite = new Favorite(user, hotel);
        when(hotelService.getHotelById(UUID_TEST_1)).thenReturn(hotel);
        when(userService.getUserById(UUID_TEST_1)).thenReturn(user);
        when(favoriteDAO.save(any(Favorite.class))).thenReturn(favorite);

        // Act
        Favorite result = favoriteService.addFavorite(favoriteDTO);

        // Assert
        assertNotNull(result);
        verify(hotelService, times(1)).getHotelById(UUID_TEST_1);
        verify(userService, times(1)).getUserById(UUID_TEST_1);
        verify(favoriteDAO, times(1)).save(any(Favorite.class));
    }

    @Test
    void testAddFavoriteNull() throws CustomException {
        // Arrange
        IncomingFavoriteDTO favoriteDTO = new IncomingFavoriteDTO();
        favoriteDTO.setHotelId(UUID_TEST_1);
        favoriteDTO.setUserId(UUID_TEST_1);
        when(hotelService.getHotelById(UUID_TEST_1)).thenReturn(new Hotel());
        when(userService.getUserById(UUID_TEST_1)).thenThrow(UserNotFoundException.class);


        // Assert + Act
        assertThrows(UserNotFoundException.class, () -> favoriteService.addFavorite(favoriteDTO));
        verify(hotelService, times(1)).getHotelById(UUID_TEST_1);
        verify(userService, times(1)).getUserById(UUID_TEST_1);
        verify(favoriteDAO, never()).save(any(Favorite.class));
    }

    @Test
    void testDeleteFavoriteExists() throws CustomException {
        // Arrange
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(UUID_TEST_1);
        when(favoriteDAO.findById(UUID_TEST_1)).thenReturn(Optional.of(favorite));

        // Act
        favoriteService.deleteFavorite(UUID_TEST_1);

        // Assert
        verify(favoriteDAO, times(1)).deleteById(UUID_TEST_1);
    }

    @Test
    void testDeleteFavoriteNull() {
        // Arrange
        when(favoriteDAO.findById(UUID_TEST_1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FavoriteNotFoundException.class, () -> favoriteService.deleteFavorite(UUID_TEST_1));
        verify(favoriteDAO, never()).deleteById(any());
    }

    @Test
    void testGetFavoriteByIdExists() throws CustomException {
        // Arrange
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(UUID_TEST_1);
        when(favoriteDAO.findById(UUID_TEST_1)).thenReturn(Optional.of(favorite));

        // Act
        Favorite result = favoriteService.getFavoriteById(UUID_TEST_1);

        // Assert
        assertNotNull(result);
        assertEquals(UUID_TEST_1, result.getFavoriteId());
        verify(favoriteDAO, times(1)).findById(UUID_TEST_1);
    }

    @Test
    void testGetFavoriteByIdNull() {
        // Arrange
        when(favoriteDAO.findById(UUID_TEST_1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FavoriteNotFoundException.class, () -> favoriteService.getFavoriteById(UUID_TEST_1));
        verify(favoriteDAO, times(1)).findById(UUID_TEST_1);
    }


}

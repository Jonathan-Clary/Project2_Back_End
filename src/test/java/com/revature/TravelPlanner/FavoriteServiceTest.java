package com.revature.TravelPlanner;

import com.revature.DAOs.FavoriteDAO;
import com.revature.DTOs.IncomingFavoriteDTO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.FavoriteNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Favorite;
import com.revature.models.LocalHotel;
import com.revature.models.User;
import com.revature.services.FavoriteService;
import com.revature.services.HotelService;
import com.revature.services.UserService;
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
        user.setUserId(1);
        Favorite favorite1 = new Favorite();
        Favorite favorite2 = new Favorite();
        Favorite favorite3 = new Favorite();
        List<Favorite> favorites = Arrays.asList(favorite1, favorite2, favorite3);
        when(userService.getUserById(1)).thenReturn(user);
        when(favoriteDAO.findByUserUserId(1)).thenReturn(favorites);

        // Act
        List<Favorite> result = favoriteService.findAllFavoriteByUser(1);

        // Assert
        assertEquals(3, result.size());
        verify(userService, times(1)).getUserById(1);
        verify(favoriteDAO, times(1)).findByUserUserId(1);
    }

    @Test
    void testFindAllFavoriteByHotel() throws CustomException {
        // Arrange
        LocalHotel hotel = new LocalHotel();
        hotel.setHotelId(1);
        Favorite favorite1 = new Favorite();
        Favorite favorite2 = new Favorite();
        List<Favorite> favorites = Arrays.asList(favorite1, favorite2);
        when(hotelService.getHotelById(1)).thenReturn(hotel);
        when(favoriteDAO.findByUserUserId(1)).thenReturn(favorites);

        // Act
        List<Favorite> result = favoriteService.findAllFavoriteByHotel(1);

        // Assert
        assertEquals(2, result.size());
        verify(hotelService, times(1)).getHotelById(1);
        verify(favoriteDAO, times(1)).findByUserUserId(1);
    }

    @Test
    void testAddFavoriteSave() throws CustomException {
        // Arrange
        IncomingFavoriteDTO favoriteDTO = new IncomingFavoriteDTO();
        favoriteDTO.setHotelId(1);
        favoriteDTO.setUserId(1);
        LocalHotel hotel = new LocalHotel();
        User user = new User();
        Favorite favorite = new Favorite(user, hotel);
        when(hotelService.getHotelById(1)).thenReturn(hotel);
        when(userService.getUserById(1)).thenReturn(user);
        when(favoriteDAO.save(any(Favorite.class))).thenReturn(favorite);

        // Act
        Favorite result = favoriteService.addFavorite(favoriteDTO);

        // Assert
        assertNotNull(result);
        verify(hotelService, times(1)).getHotelById(1);
        verify(userService, times(1)).getUserById(1);
        verify(favoriteDAO, times(1)).save(any(Favorite.class));
    }

    @Test
    void testAddFavoriteNull() throws CustomException {
        // Arrange
        IncomingFavoriteDTO favoriteDTO = new IncomingFavoriteDTO();
        favoriteDTO.setHotelId(1);
        favoriteDTO.setUserId(1);
        when(hotelService.getHotelById(1)).thenReturn(new LocalHotel());
        when(userService.getUserById(1)).thenThrow(UserNotFoundException.class);


        // Assert + Act
        assertThrows(UserNotFoundException.class, () -> favoriteService.addFavorite(favoriteDTO));
        verify(hotelService, times(1)).getHotelById(1);
        verify(userService, times(1)).getUserById(1);
        verify(favoriteDAO, never()).save(any(Favorite.class));
    }

    @Test
    void testDeleteFavoriteExists() throws CustomException {
        // Arrange
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(1);
        when(favoriteDAO.findById(1)).thenReturn(Optional.of(favorite));

        // Act
        favoriteService.deleteFavorite(1);

        // Assert
        verify(favoriteDAO, times(1)).deleteById(1);
    }

    @Test
    void testDeleteFavoriteNull() {
        // Arrange
        when(favoriteDAO.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FavoriteNotFoundException.class, () -> favoriteService.deleteFavorite(1));
        verify(favoriteDAO, never()).deleteById(anyInt());
    }

    @Test
    void testGetFavoriteByIdExists() throws CustomException {
        // Arrange
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(1);
        when(favoriteDAO.findById(1)).thenReturn(Optional.of(favorite));

        // Act
        Favorite result = favoriteService.getFavoriteById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getFavoriteId());
        verify(favoriteDAO, times(1)).findById(1);
    }

    @Test
    void testGetFavoriteByIdNull() {
        // Arrange
        when(favoriteDAO.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(FavoriteNotFoundException.class, () -> favoriteService.getFavoriteById(1));
        verify(favoriteDAO, times(1)).findById(1);
    }


}

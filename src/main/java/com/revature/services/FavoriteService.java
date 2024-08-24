package com.revature.services;

import com.revature.DAOs.FavoriteDAO;
import com.revature.DTOs.IncomingFavoriteDTO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.FavoriteNotFoundException;
import com.revature.models.Favorite;
import com.revature.models.Hotel;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private FavoriteDAO favoriteDAO;
    private HotelService hotelService;
    private UserService userService;

    @Autowired
    public FavoriteService(FavoriteDAO favoriteDAO, HotelService hotelService, UserService userService){
        this.favoriteDAO = favoriteDAO;
        this.userService = userService;
        this.hotelService = hotelService;
    }

    public List<Favorite> findAllFavorite(){
        return favoriteDAO.findAll();
    }

    public List<Favorite> findAllFavoriteByUser(int userId) throws CustomException {
        return favoriteDAO.findByUserUserId(userService.getUserById(userId).getUserId());
    }

    public List<Favorite> findAllFavoriteByHotel(int hotelId) throws CustomException{
        return favoriteDAO.findByUserUserId(hotelService.getHotelById(hotelId).getHotelId());
    }

    public Favorite addFavorite(IncomingFavoriteDTO favorite) throws CustomException{
        Hotel hotel = hotelService.getHotelById(favorite.getHotelId());
        User user = userService.getUserById(favorite.getUserId());
        if(hotel != null && user != null){
            Favorite newFavorite = new Favorite( user, hotel);
            return favoriteDAO.save(newFavorite);
        }else{
            return null;
        }
    }

    public void deleteFavorite(int favoriteId) throws CustomException{
        favoriteDAO.deleteById(getFavoriteById(favoriteId).getFavoriteId());
    }

    public Favorite getFavoriteById(int favoriteId) throws CustomException{
        Optional<Favorite> favorite = favoriteDAO.findById(favoriteId);
        if(favorite.isPresent()){
            return favorite.get();
        }else{
            throw new FavoriteNotFoundException("Favorite with id: "+favoriteId+" was not found");
        }
    }

}

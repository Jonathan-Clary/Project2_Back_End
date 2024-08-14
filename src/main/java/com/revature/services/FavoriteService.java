package com.revature.services;

import com.revature.DAOs.FavoriteDAO;
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

    public List<Favorite> findAllFavoriteByUser(int user_id) throws CustomException {
        return favoriteDAO.findByUserUser_id(userService.getUserById(user_id).getUserId());
    }

    public List<Favorite> findAllFavoriteByHotel(int hotel_id) throws CustomException{
        return favoriteDAO.findByUserUser_id(hotelService.getHotelById(hotel_id).getHotelId());
    }

    public Favorite addFavorite(Favorite favorite) throws CustomException{
        Hotel hotel = hotelService.getHotelById(favorite.getHotel().getHotelId());
        User user = userService.getUserById(favorite.getUser().getUserId());
        if(hotel != null && user != null){
            return favoriteDAO.save(favorite);
        }else{
            return null;
        }
    }

    public void deleteFavorite(int favorite_id) throws CustomException{
        favoriteDAO.deleteById(getFavoriteById(favorite_id).getFavorite_id());
    }

    public Favorite getFavoriteById(int favorite_id) throws CustomException{
        Optional<Favorite> favorite = favoriteDAO.findById(favorite_id);
        if(favorite.isPresent()){
            return favorite.get();
        }else{
            throw new FavoriteNotFoundException("Favorite with id: "+favorite_id+" was not found");
        }
    }

}

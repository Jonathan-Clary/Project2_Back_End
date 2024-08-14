package com.revature.services;

import com.revature.DAOs.FavoriteDAO;
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

    public List<Favorite> findAllFavoriteByUser(int user_id){
        Optional<User> user = userService.getUserById(user_id);
        if(user.isPresent()){
            return favoriteDAO.findByUserUser_id(user.get().getUserId());
        }else{
            return null;
        }
    }

    public List<Favorite> findAllFavoriteByHotel(int hotel_id){
        Optional<Hotel> hotel = hotelService.getHotelById(hotel_id);
        if(hotel.isPresent()){
            return favoriteDAO.findByUserUser_id(hotel.get().getHotelId());
        }else{
            return null;
        }
    }

    public Favorite addFavorite(Favorite favorite){
        Optional<Hotel> hotel = hotelService.getHotelById(hotel_id);
        Optional<User> user = userService.getUserById(user_id);
        if(hotel.isPresent() && user.isPresent()){
            return favoriteDAO.save(favorite);
        }else{
            return null;
        }
    }

    public void deleteFavorite(int favorite_id){

        favoriteDAO.deleteById(getFavoriteById(favorite_id).getFavorite_id());

    }

    public Favorite getFavoriteById(int favorite_id){
        Optional<Favorite> favorite = favoriteDAO.findById(favorite_id);
        if(favorite.isPresent()){
            return favorite.get();
        }else{
            //TODO:throw exception
            return null;
        }
    }

}

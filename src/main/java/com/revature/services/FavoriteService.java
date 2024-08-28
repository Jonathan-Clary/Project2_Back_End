package com.revature.services;

import com.revature.DAOs.FavoriteDAO;
import com.revature.DTOs.IncomingFavoriteDTO;
import com.revature.exceptions.CustomException;
import com.revature.exceptions.FavoriteNotFoundException;
import com.revature.models.Favorite;
import com.revature.models.Hotel;
import com.revature.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FavoriteService {

    Logger log = LoggerFactory.getLogger(FavoriteService.class);

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
        log.debug("Method 'findAllFavorite' invoked");
        List<Favorite> favoriteList = favoriteDAO.findAll();

        //Append id's to string for logging, because printing every object is excessive
        StringBuilder sb = new StringBuilder();
        for(Favorite f: favoriteList){
            sb.append(f.getFavoriteId()).append(", ");
        }

        log.debug("Method 'findAllFavorite' returning favorite list with favorite_ids: {}", sb.toString());
        return favoriteList;
    }

    public List<Favorite> findAllFavoriteByUser(UUID userId) throws CustomException {
        log.debug("Method 'findAllFavoriteByUser' invoked with userId: {}",userId);
        List<Favorite> userFavorites = favoriteDAO.findByUserUserId(userService.getUserById(userId).getUserId());

        //Append id's to string for logging, because printing every object is excessive
        StringBuilder sb = new StringBuilder();
        for(Favorite f: userFavorites){
            sb.append(f.getFavoriteId()).append(", ");
        }

        log.debug("Method 'findAllFavoriteByUser' returning favorite list with favorite_ids: {}", sb.toString());
        return userFavorites;
    }

    public List<Favorite> findAllFavoriteByHotel(UUID hotelId) throws CustomException{
        log.debug("Method 'findAllFavoriteByHotel' invoked with hotelId: {}",hotelId);
        List<Favorite> hotelFavorites =  favoriteDAO.findByHotelHotelId(hotelId);

        //Append id's to string for logging, because printing every object is excessive
        StringBuilder sb = new StringBuilder();
        for(Favorite f: hotelFavorites){
            sb.append(f.getFavoriteId()).append(", ");
        }
        log.debug("Method 'findAllFavoriteByHotel' returning favorite list with favorite_ids: {}", sb.toString());
        return hotelFavorites;
    }

    public Favorite addFavorite(IncomingFavoriteDTO favorite) throws CustomException{
        log.debug("Method 'addFavorite' invoked with favorite: {}", favorite.toString());
        Hotel hotel = hotelService.saveHotel(favorite.getHotel());
        User user = userService.getUserById(favorite.getUserId());
        if(hotel != null && user != null){
            Favorite newFavorite = new Favorite( user, hotel);
            Favorite returningFavorite = favoriteDAO.save(newFavorite);
            log.debug("Method 'addFavorite' returning: {}", returningFavorite.toString());
            return returningFavorite;
        }else{
            log.warn("Method 'addFavorite' returning: null");
            return null;
        }
    }

    public void deleteFavorite(UUID favoriteId) throws CustomException{
        log.debug("Method 'deleteFavorite' invoked with favoriteId: {}", favoriteId);
        favoriteDAO.deleteById(getFavoriteById(favoriteId).getFavoriteId());
        log.debug("Method 'deleteFavorite' completed");

    }

    public Favorite getFavoriteById(UUID favoriteId) throws CustomException{
        log.debug("Method 'getFavoriteById' invoked with favoriteID: {}", favoriteId);
        Optional<Favorite> favorite = favoriteDAO.findById(favoriteId);
        if(favorite.isPresent()){
            Favorite returningFavorite = favorite.get();
            log.debug("Method 'getFavoriteById' returning: {}", returningFavorite.toString());
            return returningFavorite;
        }else{
            throw new FavoriteNotFoundException("Favorite with id: "+favoriteId+" was not found");
        }
    }

    public List<Favorite> findFavoritesByHotelAndUser(UUID hotelId, UUID userId) throws CustomException {
        log.debug("Method 'getFavoriteByHotelAndUser' invoked with hotelId: {} and userId: {}", hotelId, userId);
        List<Favorite> hotelFavorites = favoriteDAO.findByHotelHotelIdAndUserUserId(hotelId,userService.getUserById(userId).getUserId());
        StringBuilder sb = new StringBuilder();
        for(Favorite f: hotelFavorites){
            sb.append(f.getFavoriteId()).append(", ");
        }
        log.debug("Method 'findFavoritesByHotelAndUser' returning favorite list with favorite_ids: {}", sb.toString());
        return hotelFavorites;
    }
}

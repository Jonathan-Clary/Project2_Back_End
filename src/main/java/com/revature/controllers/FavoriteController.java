package com.revature.controllers;

import com.revature.DTOs.HotelDTO;
import com.revature.DTOs.IncomingFavoriteDTO;
import com.revature.exceptions.CustomException;
import com.revature.models.Favorite;
import com.revature.services.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/favorites")
@CrossOrigin
public class FavoriteController {
    Logger log = LoggerFactory.getLogger(FavoriteController.class);

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService){this.favoriteService = favoriteService;}

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@RequestBody IncomingFavoriteDTO newFavorite) {
        log.debug("Endpoint POST ./favorite reached");
        try{
            return ResponseEntity.status(201).body(favoriteService.addFavorite(newFavorite));
        }catch(Exception e){
            log.warn("Exception was thrown", e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Favorite>> getAllFavorite(){
        log.debug("Endpoint GET ./favorite reached");
        try{
            return ResponseEntity.ok(favoriteService.findAllFavorite());
        }catch(Exception e){
            log.warn("Exception was thrown", e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Favorite>> getAllFavoriteByUser(@PathVariable UUID userId){
        log.debug("Endpoint GET ./favorite/user={}",userId);
        try{
            return ResponseEntity.ok(favoriteService.findAllFavoriteByUser(userId));
        }catch(Exception e){
            log.warn("Exception was thrown", e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/hotel/user/{userId}")
    public ResponseEntity<List<HotelDTO>> getAllFavoriteHotelsByUser(@PathVariable UUID userId){
        log.debug("Endpoint GET ./favorite/hotel/user/={}",userId);
        try{
            return ResponseEntity.ok(favoriteService.findAllFavoriteHotelByUser(userId));
        }catch(Exception e){
            log.warn("Exception was thrown", e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Favorite>> getAllFavoriteByHotel(@PathVariable UUID hotelId){
        log.debug("Endpoint GET ./favorite/hotel={}",hotelId);
        try{
            return ResponseEntity.ok(favoriteService.findAllFavoriteByHotel(hotelId));
        }catch(Exception e){
            log.warn("Exception was thrown", e);
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/hotel/{hotelId}/user/{userId}")
    public ResponseEntity<?> getAllFavoriteByHotelAndUser(@PathVariable UUID hotelId, @PathVariable UUID userId) {

        try {
            List<Favorite> favorites= favoriteService.findFavoritesByHotelAndUser(hotelId, userId);
            if (favorites.isEmpty()) {
                return ResponseEntity.status(204).body(false);
            }
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            log.warn("Exception was thrown", e);
            return ResponseEntity.status(404).body(null);
        }


    }
    @DeleteMapping("/hotel/{hotelId}/user/{userId}")
    public ResponseEntity<Object> deleteFavorite(@PathVariable UUID hotelId, @PathVariable UUID userId){
        log.debug("Endpoint DELETE ./favorite/hotel/{}/user/{}",hotelId,userId);
        try{
            List<Favorite> favorites= favoriteService.findFavoritesByHotelAndUser(hotelId, userId);
            favoriteService.deleteFavorite(favorites.get(0).getFavoriteId());
            return ResponseEntity.status(200).body("deleted");
        }catch(Exception e) {
            log.warn("Exception was thrown", e);
            return ResponseEntity.status(404).body(null);
        }
    }

    // handles all the custom exceptions
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException( CustomException e){
        log.warn("Exception was thrown: {}", e.getMsg());
        return ResponseEntity.status(e.getStatus()).body(e.getMsg());
    }

}

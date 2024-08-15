package com.revature.controllers;

import com.revature.models.Favorite;
import com.revature.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorite")
@CrossOrigin
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService){this.favoriteService = favoriteService;}

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@RequestBody Favorite newFavorite){
        try{
            return ResponseEntity.status(201).body(favoriteService.addFavorite(newFavorite));
        }catch(Exception e){
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Favorite>> getAllFavorite(){
        try{
            return ResponseEntity.ok(favoriteService.findAllFavorite());
        }catch(Exception e){
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/user={userId}")
    public ResponseEntity<List<Favorite>> getAllFavoriteByUser(@PathVariable int userId){
        try{
            return ResponseEntity.ok(favoriteService.findAllFavoriteByUser(userId));
        }catch(Exception e){
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/hotel={hotelId}")
    public ResponseEntity<List<Favorite>> getAllFavoriteByHotel(@PathVariable int hotelId){
        try{
            return ResponseEntity.ok(favoriteService.findAllFavoriteByHotel(hotelId));
        }catch(Exception e){
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/favorite={favoriteId}")
    public ResponseEntity<Object> deleteReview(@PathVariable int favoriteId){
        try{
            favoriteService.deleteFavorite(favoriteId);
            return ResponseEntity.status(200).body("deleted");
        }catch(Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

}

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
    public ResponseEntity<Favorite> addFavorite(@RequestBody Favorite new_favorite){
        try{
            return ResponseEntity.status(201).body(favoriteService.addFavorite(new_favorite));
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

    @GetMapping("/user={user_id}")
    public ResponseEntity<List<Favorite>> getAllFavoriteByUser(@PathVariable int user_id){
        try{
            return ResponseEntity.ok(favoriteService.findAllFavoriteByUser(user_id));
        }catch(Exception e){
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/hotel={hotel_id}")
    public ResponseEntity<List<Favorite>> getAllFavoriteByHotel(@PathVariable int hotel_id){
        try{
            return ResponseEntity.ok(favoriteService.findAllFavoriteByHotel(hotel_id));
        }catch(Exception e){
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/favorite={favorite_id}")
    public ResponseEntity<Object> deleteReview(@PathVariable int favorite_id){
        try{
            favoriteService.deleteFavorite(favorite_id);
            return ResponseEntity.status(200).body("deleted");
        }catch(Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

}

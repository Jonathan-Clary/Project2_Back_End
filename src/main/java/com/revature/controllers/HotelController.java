package com.revature.controllers;

import com.revature.models.LocalHotel;
import com.revature.services.AmadeusHotelService;
import com.revature.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin
public class HotelController {


    private final AmadeusHotelService amadeusHotelService;

    @Autowired
    public HotelController( AmadeusHotelService amadeusHotelService) {
        this.amadeusHotelService = amadeusHotelService;
    }

    @GetMapping("/fetch-by-city")
    public ResponseEntity<List<LocalHotel>> fetchHotelsByCity(@RequestParam String cityCode) {
        List<LocalHotel> hotels = amadeusHotelService.fetchAndSaveHotelsByCity(cityCode);
        return ResponseEntity.ok(hotels);
    }

}


